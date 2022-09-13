package concept.classesandobjects

// 클래스는 class 키워드를 사용하여 선언

// 헤더와 바디가 비어있다면 생략가능
class CtorOmit

// 주 생성자
class PrimCtor(prop: String)

// 접근 제한자 혹은 어노테이션 있다면 constructor 키워드 생략 불가
class PrimCtorWithVisOrAnnotation @kotlin.jvm.Throws private constructor(val prop: String)

// 초기화 코드는 init 키워드로 지정된 블록에 작성, 다수 가능
// 다수의 init이 의미있는 이유는 속성 초기화를 포함하여 내부에 작성된 순서대로 실행되기 때문
class InitAndInitProp {
    val prop1 = "kotlin"

    init {
        println("초기화 블록 실행") // prop2 접근 불가
    }

    val prop2 = 20

    init {
        println("두번째 초기화 코드 실행")
    }
}

// 주 생성자의 파라미터로 속성 초기화 가능(val, val로 지정 시 속성)
class InitPropByPrimCtorParam(val prop1: String, val prop2: Int)

// 인자 생략 시 기본값 지정 가능
class DefValue(val prop1: String, val prop2: Int = 10)
class DefValue2(val prop1: String = "kotlin", val prop2: Int)

fun defValueTest() {
    val x = DefValue("kotlin") // 기본값이 지정되지 않은 prop1은 생략 불가
    // prop1 = "kotlin", prop2 = 10
    val y = DefValue("kotlin", 20)
    // prop1 = "kotlin", prop2 = 20

    // 기본값이 지정된 파라미터가 마지막이 아니라면 아래와 같이 가능
    val z = DefValue2(prop2 = 10) // 파라미터 이름을 명시하지 않을 시 prop1로 10이 들어감
    // prop1 = "kotlin", prop2 = 10
}

// trailing comma 사용 가능
class TrailingComma(
    val prop1: String,
    val prop2: Int,
    val prop3: String,
)

// constructor 키워드로 보조 생성자 추가
// 시그니쳐가 다르다는 조건하에 다수 가능
// 블록은 init과 동일
// 파라미터로 속성 초기화 불가
// 바디가 비어있다면 생략 가능, 다만 바디 생략시 보조 생성자 의미 없음
// 주 생성자가 없다면 보조 생성자가 의미 없음
// 보조 생성자에서 주 생성자 호출 필수, this()로 주 생성자 호출
class SecondaryCtor(val prop: String) {
//    constructor(val prop1: String, val prop2: Int) : this(prop1) { 파라미터 val로 지정 시 컴파일 에러
//        println("보조 생성자 실행")
//    }

    constructor(prop1: String, prop2: Int) : this(prop1) {
        println("보조 생성자 실행")
    }
}

// 속성 초기화와 초기화 블록이 보조 생성자보다 먼저 실행됨
class ExecuteOrder(val prop: String) {
    constructor(prop1: String, prop2: Int) : this(prop1) {
        println("보조 생성자 실행") // 실행 3
    }

    var prop3 = "kotlin".also { println("속성 초기화 실행") } // 실행 1

    init {
        println("초기화 블록 실행") // 실행 2
    }
}

// 인스턴스 생성
// new 키워드 미사용
val ctorOmit = CtorOmit()

val primCtor = PrimCtor("kotlin")

// abstract 키워드로 추상 클래스와 추상 멤버 정의
// 클래스의 모든 기능 사용 가능
abstract class Abs {
    abstract val absProb: String

    abstract fun absFunc()
}

class ClassForAbs() : Abs() {
    override val absProb: String = "kotlin" // 구현

    override fun absFunc() {
        // 구현
    }
}

// 비추상 멤버도 open 키워드를 통해 추상화 가능
abstract class AbsForReAbs {
    open val nonAbsProp: String = "kotlin" // 비추상 속성

    open fun nonAbsFunc() {} // 비추상 메소드
}

abstract class RaAbs : AbsForReAbs() {
    abstract override val nonAbsProp: String // 비추상 속성 추상화
    abstract override fun nonAbsFunc() // 비추상 메소드 추상화
}