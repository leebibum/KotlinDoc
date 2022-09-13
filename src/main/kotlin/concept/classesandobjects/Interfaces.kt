package concept.classesandobjects

// 비추상 메소드, 추상 메소드 모두 소유 가능
// 속성도 가질 수 있지만 추상적이거나 getter(val), setter(var) 구현 필수
// 인터페이스의 속성은 backing field 사용 불가
// interface 키워드를 사용하여 정의
interface Inft {
    fun absFunc() // 추상 메소드

    fun nonAbsFunc() { // 비추상 메소드
    }
}

interface Inft2 {
    fun fooBar()
}

// 클래스와 오브젝트는 하나 이상의 인터페이스 구현 가능
class MultipleImpl : Inft, Inft2 {
    override fun absFunc() {}
    override fun fooBar() {}
}

// 인터페이스의 속성은 추상적이거나 접근자 구현 필수
interface IntfWithProp {
    val absValProp: Int // 추상 속성

    val nonAbsValProp: String //  비추상 속성
        get() = "kotlin"

    var nonAbsVarProp: String // setter를 가진 비추상 속성
        get() = "kotlin"
        set(value) = println(value) // Unit만 가능
}

// 주 생성자에서 구현 가능
class IntfWithPropImpl(override val absValProp: Int) : IntfWithProp

// 내부에서도 구현 가능
class IntfWithPropImpl2 : IntfWithProp {
    override val absValProp: Int = 10 // 혹은 getter 지정
}

// 다른 인터페이스 구현 및 확장 가능
interface IntfExtends : IntfWithProp {
    val propForPropImpl: Int

    override val absValProp: Int
        get() = propForPropImpl + 1
}