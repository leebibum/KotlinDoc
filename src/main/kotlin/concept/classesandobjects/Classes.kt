package concept.classesandobjects

// 클래스는 class 키워드를 사용하여 선언
// 클래스명, 헤더(주 생성자, 파라미터 등), 중괄호로 묶인 내부로 구성
// 포함 가능: 주 생성자, 보조 생성자, 초기화 블록, 함수, 속성, 중첩 혹은 이너 클래스, 오브젝트 선언, 컴패니언 오브젝트

// 헤더와 내부가 비어있다면 생략가능
class CtorOmit

// 주 생성자
class PrimCtor constructor(prop: String)

// 접근 제한자와 어노테이션이 없다면 constructor 생략 가능
class CtorOmitWithParam(prop: String)

// 초기화 코드는 init 키워드로 지정된 블록에 작성, 다수 가능
// 다수의 init이 의미있는 이유는 속성 초기화를 포함하여 내부에 작성된 순서대로 실행되기 때문
class InitAndInitProp {
    val prop1 = "kotlin"

    init {
        println("초기화 블록 시행") // prop2 접근 불가
    }

    val prop2 = 20

    init {
        println("두번째 초기화 코드 실행")
    }
}

// 주 생성자의 파라미터로 속성 초기화 가능(val, val로 지정 시 속성)
class InitPropByPrimCtorParam(val prop1: String, val prop2: Int)

// 인자 미전달 시 기본값 지정 가능
class DefaultValue(val prop1: String, val prop2: Int = 10)
class DefaultValue2(val prop1: String = "kotlin", val prop2: Int)

fun classDefaultValueTest() {
    val x = DefaultValue("kotlin") // 기본값이 지정되지 않은 prop1은 생략 불가
    // prop1 = "kotlin", prop2 = 10
    val y = DefaultValue("kotlin", 20)
    // prop1 = "kotlin", prop2 = 20

    // 기본값이 지정된 파라미터가 마지막이 아니라면 아래와 같이 가능
    val z = DefaultValue2(prop2 = 10) // 이름 지정하지 않을 시 prop1로 10이 들어감
    // prop1 = "kotlin", prop2 = 10
}

// 파라미터가 너무 많거나 길다면 trailing comma 사용
class TrailingComma(
    val prop1: String, // trailing comma
    val prop2: Int,
    val prop3: String,
)

// 접근 제한자 혹은 어노테이션 있다면 주 생성자 생략 불가
class PrimCtorWithVisibilityOrAnnotation @kotlin.jvm.Throws private constructor(val prop: String)

// constructor 키워드로 보조 생성자 생성
// 블록은 init과 동일
// 파라미터로 속성 초기화 불가
// 내부가 비어있다면 생략 가능
class SecondaryCtor {
//    constructor(val prop: String) { val 때문에 컴파일 에러
//        println("보조 생성자 실행")
//    }

    constructor(prop: String) {
        println("보조 생성자 실행")
    }
}

// 주 생성자가 있다면 보조 생성자에서 주 생성자 호출 필수, this()로 주 생성자 호출
class SecondaryCtorWithPrimCtor(val prop: String) {
    constructor(prop1: String, prop2: Int) : this(prop1) {
        println("보조 생성자 실행")
    }
}

// 속성 초기화와 초기화 블록이 보조 생성자보다 먼저 실행
class ExecuteOrder() {
    constructor(prop: String) : this() {
        println("보조 생성자 실행") // 실행 3
    }

    var prop = "kotlin".also { println("속성 초기화 실행") } // 실행 1

    init {
        println("초기화 블록 실행") // 실행 2
    }
}

fun creatingInstance() {
    // 인스턴스 생성
    // new 키워드 미사용
    val x = CtorOmit()
    val y = PrimCtor("kotlin")
}

// abstract 키워드로 추상 클래스 선언
// 추상 클래스는 비추상 멤버 소유 가능, 추상 멤버는 abstract 키워드를 사용하여 정의
// 추상 멤버는 open 키워드 불필요
abstract class Abs {
    val nonAbsProp = "kotlin" // 비추상 속성
    abstract val absProb: String // 추상 속성

    fun nonAbsFunc() { // 비추상 메소드

    }

    abstract fun absFunc() // 추상 메소드
}

// 비추상 멤버도 open 키워드를 통해 추상화 가능
abstract class AbsForReAbs {
    open val nonAbsProp: String = "kotlin" // 비추상 속성

    open fun nonAbsFunc() { // 비추상 메소드
    }
}

abstract class RaAbs : AbsForReAbs() {
    abstract override val nonAbsProp: String // 비추상 속성 추상화
    abstract override fun nonAbsFunc() // 비추상 메소드 추상화
}