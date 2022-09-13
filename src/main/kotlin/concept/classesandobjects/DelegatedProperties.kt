package concept.classesandobjects

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 일반적인 속성은 한 번 구현 후 라이브러리에서 호출하는게 효율적
// 제공 위임 속성 종류
// lazy: 첫 접근시 블록 실행 후 할당
// observable: 리스너가 이 속성의 변경에 대해 알림을 수신
// 맵에 각 속성을 분리한 필드 대신 속성 저장
// 문법: var/val <property name>: <Type> by <expression>
// 속성의 get(), set()은 각각 getValue(), setValue()에서 위임받음
// getValue()와 setValue()는 operator fun
// 따라서 위임하는 객체는 getValue(), setValue() 구현 필수
// 밑에서 다시 다룸

// layz는 람다를 인자로 받으며, Lazy<T>를 반환하는 함수
// 여기서 T는 마지막 구문에 반환하는 타입
// get()이 처음 호출될때 lazy의 전달된 람다를 실행하고 결과를 변수에 할당
// 그 후 get()은 할당값을 반환

val lazy by lazy {
    println("lazy 실행")
    10 // 할당
}

fun lazyTest() {
    println(lazy)
    println(lazy)
}
// lazy는 동기화적으로 실행
// 값은 하나의 스레드에서 계산되지만 모든 스레드는 동일한 결과값을 받게됨
// 동기화가 필요없는 경우 LazyThreadSafetyMode.PUBLICATION을 lazy()의 인자에 전달
// 속성을 사용하고 있는 스레드에서만 초기화된다고 확신할 수 있는 경우 LazyThreadSafetyMode.NONE 전달
// ㄴ thread-safety guarantee 관련 오버헤드가 미발생된다고 함

// Delegates.observable()은 초기값, 수정을 위한 핸들러 두개의 인자를 받음
// 핸들러는 속성에 할당될 때마다 호출(할당 후 호출)
// 핸들러는 속성, 이전 값, 새 값 세 파라미터가 있음

var observable by Delegates.observable("초기값") { property, oldValue, newValue ->
    println("$property, $oldValue, $newValue")
}

fun observableTest() {
    observable = "kotlin"
}

// 특정 조건에 따라 할당하고 싶다면 vetoable() 호출
// vetoable()은 할당 전 호출
var vetoable by Delegates.vetoable("초기값") { property, oldValue, newValue ->
    observable.length == newValue.length // Boolean으로 할당 여부 확인
}

fun vetoableTest() {
    vetoable = "세글자"
    println(vetoable)
    vetoable = "안녕하세요" // 미할당
    println(vetoable) // 세글자
}

// 속성은 자신의 접근자를 다른 속성에게 위임 가능
// 최상위 레벨 속성과 다른 클래스 속성 혹은 확장 멤버, 같은 클래스 속성 혹은 확장 멤버 모두 다른 속성에게 위임 가능
// 속성의 접근자를 다른 속성에게 위임하기 위해서는 :: 사용
val toplevelProp = 0

class ClassForPropDelegate {
    val anotherClassProp: Int = 10
}

class DelegatedPropClass(val anotherClass: ClassForPropDelegate, val forDelegationProp: Int) {
    val fromTopLv by ::toplevelProp
    val fromAnotherClass by ClassForPropDelegate()::anotherClassProp // 다른 클래스 속성 위임 받기 1
    val fromAnotherClass2 by anotherClass::anotherClassProp // 다른 클래스 속성 위임 받기 1

    val fromAnotherMemeber by this::forDelegationProp
}

// 다른 속성을 deprecated 시킬때 유용
class ForDeprecated {
    val newProp = 10

    @Deprecated("deprecated")
    val oldProp by this::newProp
}

// map에서 값 위임받기 가능
class DelegatedFromMap(map: Map<String, Int>) {
    val prop1 by map // 타입 추론 가능 (Int)
    val prop2 by map
}

fun delegatedFromMap() {
    val result1 = DelegatedFromMap(mapOf("prop1" to 10, "prop2" to 20, "prop3" to 30)) // prop3은 사라짐
    val result2 = DelegatedFromMap(mapOf("prop2" to 10, "prop1" to 20))
    val result3 = DelegatedFromMap(mapOf("prop" to 10))
    // 문자열의 키값과 동일한 속성에 값이 할당
    println("${result1.prop1}, ${result1.prop2}")
    println("${result2.prop1}, ${result2.prop2}")
    println("${result3.prop1}, ${result3.prop2}") // 모든 속성에 값이 할당되지 않으면 NoSuchElementException 발생
}

// 속성이 var인 경우 MutableMap 활용
class DelegatedFromMutableMap(map: MutableMap<String, Int>) {
    var prop1 by map
    var prop2 by map
}

fun delegatedFromMutableMap() {
    val mutableMap = mutableMapOf("prop1" to 10, "prop2" to 20)
    val result = DelegatedFromMutableMap(mutableMap)
    println("${result.prop1}, ${result.prop2}")
    mutableMap["prop1"] = 100
    println("${result.prop1}, ${result.prop2}") // 100, 20
}

// 지역 변수도 위임 받기 가능
fun localDelegated(func: () -> String) {
    val stringProp by lazy(func)
}

// val 속성 위임은 getValue() 필수
// getValue() 함수의 파라미터는 thisRef: Any?, property: KProperty<*> 필수
// thisRef = 같은 타입 혹은 속성이 포함된 클래스
// property = KProperty<*> 혹은 KProperty<*>의 슈퍼 타입
// getValue()는 속성과 같은 타입 반환 필수

// var 속성은 getValue()와 setValue() 둘 다 필수
// 앞 두 파라미터는 동일
// value의 타입은 속성과 같은 타입만 가능
open class DelegateForProp(var prop: String = "kotlin") {
    operator fun getValue(thisRef: PropOwner, property: KProperty<*>): String { // 최상위 레벨 속성은 thisRef에 null이 들어옴
        return prop
    }

    operator fun setValue(propOwner: PropOwner, property: KProperty<*>, s: String) {
        prop = s
    }
}

class PropOwner {
    val valProp by DelegateForProp()
    var varProp by DelegateForProp()
}

// getValue()와 setValue()는 위임하는 객체의 멤버 함수 혹은 확장 함수로도 제공 가능
// operator와 파라마터 동일
operator fun DelegateForProp.getValue(thisRef: PropOwner, property: KProperty<*>) = this.prop

// 코틸린이 제공하는 ReadOnlyProperty, ReadWriteProperty 인터페이스를 구현하여 위임 가능

class PropOwnerForIntf {
    var prop by RWImpl
}

val RWImpl = object : ReadWriteProperty<Any?, Any?> { // 타입 파라미터의 첫번째 인자는 thisRef, 두번째는 속성의 타입
    var curValue: Any? = 10
    override fun getValue(thisRef: Any?, property: KProperty<*>): Any? = curValue

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Any?) {
        curValue = value
    }
}

// 위임은 컴파일 시 아래와 같이 변환
//class CompileTimeDelegate {
//    private val propDelegate = DelegateForProp()
//    val prop: String
//        get() = propDelegate.getValue(this, this::prop)
//        set(value) = propDelegate.setValue(this, this::prop, value)
//}

// 속성에서 다른 속성에게 위임할때는 메모리 최적화를 위해 propDelegate 객체를 생성하지 않음
class CompileTimeDelegate2 {
    private var impl: String = "kotlin"
    var prop
        get() = impl
        set(value) {
            impl = value
        }
}