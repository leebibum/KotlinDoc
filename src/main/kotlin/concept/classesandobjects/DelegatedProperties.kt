package concept.classesandobjects

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 위임 속성 전체 문법: var/val <property name>: <Type> by <expression>
// 코드 블록과 함수 내에서도 위임 속성 선언 가능

// lazy는 람다를 인자로 수용
// 첫 접근 시 블록 안 코드가 실행되고 마지막 줄의 반환값을 할당
// 그 후는 계속 할당된 값을 반환

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
// ㄴ thread-safety guarantee 관련 오버헤드 미발생

// Delegates.observable()은 초기값, 수정을 위한 핸들러 두개의 인자 수용
// 핸들러는 람다
// 핸들러는 속성에 값을 할당할때마다 호출(할당 후 호출)
// 핸들러는 속성, 이전 값, 새 값 파라미터가 존재

var observable by Delegates.observable("초기값") { property, oldValue, newValue ->
    println("$property, $oldValue, $newValue")
}

fun observableTest() {
    observable = "kotlin"
}

// 특정 조건에 따라 할당하고 싶다면 vetoable() 사용
// vetoable()은 할당 전 호출
var vetoable by Delegates.vetoable("초기값") { property, oldValue, newValue ->
    observable.length == newValue.length // Boolean 반환
}

fun vetoableTest() {
    vetoable = "세글자"
    println(vetoable)
    vetoable = "안녕하세요" // 미할당
    println(vetoable) // 세글자
}

// 속성은 자신의 접근자를 다른 속성에게 위임 가능
// 최상위 레벨 속성과 다른 클래스 속성 혹은 확장 멤버, 같은 클래스 속성 혹은 확장 멤버 모두 다른 속성에게 위임 가능하며
// 위임 받기도 가능
// 속성의 접근자를 다른 속성에게 위임하기 위해서는 :: 사용
val toplevelProp = 0

class ClassForPropDelegate {
    val anotherClassProp: Int = 10
}

class DelegatedPropClass(val anotherClass: ClassForPropDelegate, val forDelegationProp: Int) {
    val fromTopLv by ::toplevelProp
    val fromAnotherClass by ClassForPropDelegate()::anotherClassProp // 다른 클래스 속성 위임 받기 1
    val fromAnotherClass2 by anotherClass::anotherClassProp // 다른 클래스 속성 위임 받기 2

    val fromAnotherMember by this::forDelegationProp
}

// 다른 속성을 deprecated 시킬때 유용
class ForDeprecated {
    val newProp = 10

    @Deprecated("deprecated")
    val oldProp by this::newProp
}

// map에서 값 위임받기 가능
// Map<T, U>에서 T는 속성의 이름, U는 속성의 타입을 의미
class DelegatedFromMap(map: Map<String, Int>) {
    val prop1 by map // 타입 추론 가능 (Int), Any 사용 시 속성마다 다른 타입 가능
    val prop2 by map
}

fun delegatedFromMap() {
    val result1 = DelegatedFromMap(mapOf("prop1" to 10, "prop2" to 20, "prop3" to 30)) // prop3은 사라짐
    val result2 = DelegatedFromMap(mapOf("prop2" to 20, "prop1" to 10)) // 문자열의 키값과 동일한 속성에 값이 할당
    val result3 = DelegatedFromMap(mapOf("prop" to 10))

    println("${result1.prop1}, ${result1.prop2}") // 10, 20
    println("${result2.prop1}, ${result2.prop2}") // 10, 20
    println("${result3.prop1}, ${result3.prop2}") // 값이 할당되지 않은 속성을 호출하면 NoSuchElementException 발생
}

// 속성이 var인 경우 MutableMap 활용 가능
class DelegatedFromMutableMap(map: MutableMap<String, Int>) {
    var prop1 by map
    var prop2 by map
}

fun delegatedFromMutableMap() {
    val mutableMap = mutableMapOf("prop1" to 10, "prop2" to 20)
    val result = DelegatedFromMutableMap(mutableMap)
    println("${result.prop1}, ${result.prop2}") // 10, 20
    mutableMap["prop1"] = 100
    println("${result.prop1}, ${result.prop2}") // 100, 20
}

// 지역 변수도 위임 받기 가능
fun localDelegated(func: () -> String) {
    val stringProp by lazy(func)
}

// val 속성에 위임하는 객체는 getValue() 구현 필수
// getValue() 함수의 파라미터
// thisRef = 속성이 포함된 클래스, 혹은 포함된 클래스의 수퍼 타입
// ㄴ 속성이 최상위 레벨이거나 지역 변수일 경우 Nothing?
// property = KProperty<*> 혹은 KProperty<*>의 슈퍼 타입
// getValue()는 속성과 같은 타입 반환 필수

// var 속성에 위임하는 객체는 getValue()와 setValue() 둘 다 필수
// setValue() 함수의 파라미터
// 앞 두 파라미터는 동일
// value = 할당되는 값, 속성과 동일 타입 혹은 수퍼 타입
// getValue()와 setValue()는 operator fun으로 선언
open class DelegateForProp(var prop: String = "kotlin") {
    operator fun getValue(thisRef: PropOwner, property: KProperty<*>): String = prop

    operator fun setValue(thisRef: PropOwner, property: KProperty<*>, value: String) {
        prop = value
    }
}

class PropOwner {
    val valProp by DelegateForProp()
    var varProp by DelegateForProp()
}

// getValue()와 setValue()는 확장 함수로도 제공 가능
// operator fun과 파라미터 동일
operator fun DelegateForProp.getValue(thisRef: PropOwner, property: KProperty<*>) = this.prop

// 코틸린이 제공하는 ReadOnlyProperty, ReadWriteProperty 인터페이스를 구현하여 위임 가능
class PropOwnerForIntf {
    var prop by RWImpl
}

val RWImpl = object : ReadWriteProperty<PropOwnerForIntf, String> { // 타입 파라미터의 첫번째 인자는 thisRef, 두번째는 속성의 타입
    var curValue = "kotlin"
    override fun getValue(thisRef: PropOwnerForIntf, property: KProperty<*>): String = curValue
    override fun setValue(thisRef: PropOwnerForIntf, property: KProperty<*>, value: String) {
        curValue = value
    }
}