package concept.functions

import kotlin.math.max

// 코틀린의 함수는 1급 객체
// 변수와 자료 구조에 저장 가능
// 고차 함수의 인자로 전달 가능
// 비함수로 할 수 있는 모든 기능 가능

// 고차 함수는 함수를 인자로 받거나 반환하는 함수

fun highOrderFunc(func: (String) -> Unit) {
    func("kotlin")
}

fun highOrderFuncTest() {
    highOrderFunc { println("call 1 $it") }
    highOrderFunc { println("call 2 $it") }
}

// 함수의 타입은 (Int) -> Stirng과 같이 표현
// (A, B) -> C는 A, B 두 인자를 받고 C를 반환하는 함수의 타입을 표현
// 파라미터가 없다면 () -> C 와 같이 파라미터 자리에 공백 가능
// Unit을 포함한 반환 타입 생략 불가
// A.(B) -> C와 같은 확장 함수로 두개의 리시버 가능
// suspend () -> Unit, suspend A.() -> Unit과 같이 suspend 키워드 사용 가능
// (x: Int, y: Int) -> Unit과 같이 매개변수 이름 지정 가능
// nullable을 반환하는 경우 ((A, B) -> C)?와 같이 표현
// (Int) -> ((Int) -> Unit) 과 같이 중첩 가능
// (Int) -> (Int) -> Unit은 위와 동일
// ((Int) -> (Int)) -> Unit은 비동일
// 타입 별명을 사용하여 함수 타입에 이름 붙히기 가능
typealias TypeAlias = (Int, Int) -> Unit

fun funcWithTypeAlias(typeAlias: TypeAlias) = typeAlias(10, 10)
fun callFuncWithTypeAlias() = funcWithTypeAlias { x, y -> println("$x, $y") }

// 함수의 인스턴스 화
// { a, b -> a + b }와 같은 람다식 사용
// fun(a: Int, b: Int): Int{ a + b }와 같은 익명 함수 사용
// 리시버가 있는 함수 리터럴은 리시버가 있는 함수 타입의 값으로 사용 가능
val valueOfFuncLiteralWithReceiver: Int.(Int) -> Int = { this + it }
val valueOfFuncLiteralWithReceiver2: Int.(Int) -> Int = fun Int.(arg: Int) = this + arg

// 이미 존재하는 함수에 콜러블 레퍼런스 사용
// 최상위 레벨, 지역, 멤버, 확장 함수: ::isOdd, String::toInt
// 최상위 레벨 멤버, 확장 속성: List<Int>::size
// 생성자: ::Regex
// 인스턴스::toString과 같은 특정 인스턴스를 가르키는 bound callable reference도 가능
// ::를 사용한 모든 참조 객체는 고차 함수의 인자로 사용 가능
fun callableRefFuncTest() {
    val topLvRef = ::topLvFunc

    fun localFunc() {}

    // 지역 함수는 같은 스코프 이내만 가능
    val localRef = ::localFunc

    val memberRef = ForCallableRef::memberFunc

    val extnRed = ForCallableRef::extnFunc

    val constructorRef = ::ForCallableRef

    val boundCallableRef = constructorRef::toString

    val str = "kotlin"
}

fun topLvFunc() {}

class ForCallableRef {
    fun memberFunc() {}
}

fun ForCallableRef.extnFunc() {}

fun callableRefPropTest() {
    val topLvRef = ::topLvProp

    val extnProp = String::extnProp
}

val topLvProp = 0
val String.extnProp: String
    get() = this + "kotlin"

// 함수를 구현하는 커스텀 클래스의 인스턴스를 인터페이스처럼 활용 가능

class intfForInstanceOfFunc : (Int) -> Int {
    override fun invoke(p1: Int): Int = 0
}

val classAsIntf: (Int) -> Int = intfForInstanceOfFunc()

// 컴파일러는 충분한 정보가 있다면 함수 타입을 가지는 변수의 타입을 추론 가능

// A.(B) -> C와 (A, B) -> C는 서로 호환 가능
val compatibleTest: Int.(Int) -> Unit = { println(it) } // Int.(Int) -> Unit
val compatibleTest2: (Int, Int) -> Unit = compatibleTest // (Int, Int) -> Unit

// 함수 타입의 값은 invoke() 연산자를 통해 인보크
// 인보크란 함수의 이름 없이 함수를 호출하는 것
// 함수 타입에 리시버가 있다면 첫번째 인자로 리시버 전달 필수
// 또는 1.foo(1)과 같은 형태로 호출
val funcForInvoke: (Int) -> Unit = { println(it) }
val funcForInvoke2: Int.(Int) -> Unit = { println(it) }

fun invokeTest() {
    funcForInvoke(0)
    funcForInvoke.invoke(0) // 위와 동일 코드

    funcForInvoke2(1, 0) // 1은 리시버
    funcForInvoke2.invoke(1, 0) // 위와 동일
    1.funcForInvoke2(0) // 위와 동일
}

// 람다식과 익명 함수는 함수 리터럴
// 함수 리터럴이란 선언되지 않았지만 표현식으로 즉시 전달되는 함수
fun funcForFuncLiteral() {
    listOf(1, 2, 3, 4, 5).forEach { println(it) } // 여기서 람다식이 함수 리터럴이며 함수 자체인 표현식
}

// 람다식 전체 문법
val funcForLambda: (Int, Int) -> Unit = { arg1: Int, arg2: Int -> println("$arg1, $arg2") }

// 람다식은 언제나 {}로 둘러싸여 있어야 함
// 파라미터 선언은 {} 안에 존재, 자료형은 선택적
// 내부는 -> 오른쪽
// 추론된 반환 타입이 Unit이 아니라면 마지막 구문이 반환값
// 모든 선택적 주석을 생략하면 아래와 동일
val funcForLambdaOmit = { arg1: Int, arg2: Int -> println("$arg1, $arg2") }

// 함수의 마지막 인자가 함수인 경우 괄호 밖 람다식으로 전달 가능
