package concept.classesandobjects

// 비추상 메소드, 추상 메소드 모두 소유 가능
// interface 키워드를 사용하여 정의
interface Inft {
    fun absFunc() // 추상 메소드
    fun nonAbsFunc() {}// 비추상 메소드
}

interface Inft2 {
    fun absFunc2()
}

// 속성도 가질 수 있지만 추상적이거나 getter(val), setter(var) 구현 필수
// 직접 초기화 불가능
// backing field 사용 불가
interface IntfWithProp {
    val absValProp: Int // 추상 속성

    val nonAbsValProp: String //  비추상 속성(val)
        get() = "kotlin"

    var nonAbsVarProp: String // 비추상 속성(var)
        get() = "kotlin"
        set(value) = println(value)
}

// 다른 인터페이스 구현 및 확장 가능
interface IntfExt : IntfWithProp {
    val propForPropImpl: Int

    override val absValProp get() = propForPropImpl + 1
}