package concept.classesandobjects

// 모든 클래스는 Any를 상속
// Any 클래스는 equals(), hashCode(), toString() 세 메소드 소유
// 모든 객체는 Any 타입 변수에 할당 가능하며 기본적으로 위 세 메소드 소유

// 모든 클래스는 상속 불가능한 final 상태
// open 키워드로 상속 가능한 상태로 정의 가능
open class Base

// 상속 문법
class Derived : Base()

// 수퍼 클래스 생성자에 인자가 필요한 경우
open class BaseWithParam(val prop: String) {
    constructor(prop: String, prop2: Int) : this(prop)
}

// 1. 서브 클래스의 파라미터에서 수퍼 클래스의 생성자로 전달
// 수퍼 클래스의 보조 생성자 호출도 가능
class DerivedWithPrimCtor(name: String) : BaseWithParam(name)

// 2. 다수의 수퍼 클래스 생성자를 호출하고 싶다면 서브 클래스의 보조 생성자에서 super 키워드로 호출
class DerivedWithSecondaryCtor : BaseWithParam {
    constructor(name: String) : super(name)
    constructor(name: String, age: Int) : super(name, age)
}

// 모든 멤버는 재정의 불가한 final 상태
// open 수정자로 상속 가능한 상태로 선언 가능
open class OverrideBase {
    open val openProp = "kotlin"
    open val openProp2 = "kotlin"
    open fun openFunc() {}
}

// 재정의한 멤버는 override 수정자로 명시 필수
open class OverrideDerived : OverrideBase() {
    override var openProp = "Java" // var로 재정의
    // val을 var로 재정의할 수 있지만 반대는 불가능

    override val openProp2: String // get 메소드로 재정의 가능
        get() = "Java"

    // 재정의 한 멤버는 자동으로 open 상태
    final override fun openFunc() {} // final 수정자로 재정의 방지 가능
}

// 서브 클래스의 주 생성자 파라미터로 속성 재정의 가능
class OverridePropByPrimCtorParam(override var openProp: String = "Ruby") : OverrideBase()

// 서브 클래스의 인스턴스가 생성될때 슈퍼 클래스의 내부가 먼저 동작
open class InitOrderBase(val prop: String) {
    init {
        println("실행 2")
    }

    open val allPropLength = prop.length.also { println("실행 3") }
}

class InitOrderDerived(prop: String, val prop2: String) : InitOrderBase(prop.also { println("실행 1") }) {
    init {
        println("실행 4")
    }

    override val allPropLength get() = (prop.length + prop2.length).also { println("실행 5") }
}
// 이는 슈퍼 클래스의 내부가 동작하는 순간 서브 클래스는 아직 초기화 되지 않았다는 뜻
// 따라서 수퍼 클래스의 속성 초기화나 init 블록에서 open 멤버를 사용하지 않도록 설계

// super 키워드 자바랑 동일
// 이너 클래스에 안에서 super는 자신의 슈퍼 클래스를 의미하기 때문에 super@Outer 어노테이션으로 아우터 클래스의 슈퍼 클래스 참조 가능
class Outer : OverrideBase() {
    inner class Inner {
        fun openFunc() = super@Outer.openFunc() // 아우터 클래스에서 재정의 되었다면 재정의된 멤버 호출
    }
}

// 다중 상속 시 동일 시그니처의 함수, 속성이 충돌한다면 super<Base>를 통해 모호성을 제거하거나 아예 다른 함수로 정의
// super<Base>로 각 멤버 호출 가능
interface SameSigFuncIntf {
    fun sameSigFunc() {}
}

open class SameSigFuncBase {
    open fun sameSigFunc() {}
}

class SameSigFuncDerived : SameSigFuncIntf, SameSigFuncBase() {
    override fun sameSigFunc() {
        super<SameSigFuncIntf>.sameSigFunc()
        super<SameSigFuncBase>.sameSigFunc() // 둘 다 호출 or 하나만 호출 or 재정의
    }
}