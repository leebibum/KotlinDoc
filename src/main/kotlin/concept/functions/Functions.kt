package concept.functions

// 함수는 fun 키워드로 선언
fun add(arg1: Int, arg2: Int): Int {
    return arg1 + arg2
}

// 함수 호출, 멤버 함수 호츨은 자바와 동일
val add = add(1, 2)

// 트레일링 콤마 사용 가능
fun funcWIthTrailingComma(
    arg1: Int,
    arg2: Int,
    arg3: Int,
) {
}

// 기본값 사용 가능
fun funcWithDefVal(
    arg1: Int = 0,
    arg2: String = "kotlin",
) {
}

// 재정의된 함수의 기본값은 베이스 함수와 같으며 새로운 기본값 지정 불가능
open class ForOverride {
    open fun funcWithDefVal(arg1: Int = 10) {}
}

class OverrideDefValFunc : ForOverride() {
//    override fun funcWithDefVal(arg1: Int = 20) { 컴파일 에러
//    }

    override fun funcWithDefVal(arg1: Int) {}
}

// 기본값을 가진 파라미터가 기본값이 없는 파라미터보다 앞에 위치한다면
// 네임드 인자로 호출할때만 기본값 사용
fun funcWithDefVal(
    arg1: Int = 0,
    arg2: Int,
) {
}

val callFuncWithDefVal = funcWithDefVal(arg2 = 20) // ar1 = 10, arg2 = 10

// 함수의 마지막 인자가 함수일 경우 기본값 사용 가능
fun funcWithFunc(
    arg1: Int = 0, arg2: Int = 0, func: () -> Unit
) {
}

val callFuncWithFunc = funcWithFunc { println() } // arg1 = 0, arg2 = 0

// 함수 호출 시 인자의 이름을 언급하여 특정 파라미터에 값 전달 가능
// 함수에 인자가 많거나 Boolean, null 같이 파라미터와의 연결성 확인이 필요한 경우 유용
// 인자 순서 마음대로 변경 가능
// 기본값을 사용하려면 그냥 생략

fun funcWithNamedArg(
    arg1: String,
    arg2: Int = 0,
    arg3: Boolean = true,
    arg4: Char = ' ',
) {
}

// 언급하고 싶은 인자만 언급가능
val callFuncWithNamedFunc = funcWithNamedArg("kotlin", 10, arg3 = false, arg4 = '가')

// 기본값을 사용하고 싶다면 기본값을 가진 파라미터 전부 생략해도 무관
val callFuncWithNamedFunc2 = funcWithNamedArg("kotlin")

// 기본값을 가진 특정 파라미터만 생략할 수 있지만 생략된 인자 뒤 값을 전달하는 인자는 이름 언급 필수
// 인자 순서를 알수 없기 때문
// arg2 생략 시 arg3, arg4 언급 필수
val callFuncWithNamedFunc3 = funcWithNamedArg("kotlin", arg3 = false, arg4 = '가')

// 분산 연산자를 사용하여 vararg 파라미터에 배열 전달 가능
val strings = arrayOf("A", "B", "C", "D")
fun funcForSpreadOperator(vararg args: String) {}

// val callFuncForSpreadOperator = funcForSpreadOperator(arr) 직접 전달 시 컴파일 에러
val callFuncForSpreadOperator = funcForSpreadOperator(*strings)
// 배열만 가능

// 함수가 어떤 값도 리턴하지 않으면 Unit 반환
// Unit은 Unit만을 값으로 가질 수 있는 타입
fun funcWithUnit(): Unit {
    println("kotlin")
    return Unit // 혹은 return 자체 생략 가능, 생략 권장
}

// 함수의 반환 타입이 Unit일 경우 반환 타입 생략 가능
fun funcWithUnitOmit() { // 위 함수와 동일 함수
    println("kotlin")
}

// 함수가 단일 식의 결과를 반환할 경우 한줄로 표현 가능
fun addAsSingleExpression(arg1: Int, arg2: Int): Int = arg1 + arg2

// 컴파일러가 반환 타입을 추론할 수 있다면 반환 타입 생략 가능
fun addAsSingleExpressionWithOmit(arg1: Int, arg2: Int) = arg1 + arg2

// vararg를 사용하여 여러 인자 받기 가능
fun funcWithVararg(arg: Int, vararg ints: Int) {
    for (num in ints) println(num)
}

val callFuncWithVararg = funcWithVararg(10, 1, 2, 3, 4, 5)

// T 타입의 vararg는 함수 내에서 Array<Int>와 동일
// 가변 인자 뒤에 오는 파라미터는 언급을 이용하여 값 전달 가능
// 또는 가변 인자 뒤에 오는 파라미터가 함수라면 괄호 밖 람다를 통해 전달 가능
// 이미 배열이 존재한다면 *을 사용하여 호출 가능
// 이때 원시 자료형 배열을 전달해야 한다면 .toTypedArray() 함수 호출

val ints = arrayOf(1, 2, 3, 4, 5)
val callFuncWithVarargByArr = funcWithVararg(10, 1, 2, *ints.toIntArray(), 3, 4, 5)
val callFuncWithVarargByArr2 = funcWithVararg(10, *ints.toIntArray())

// 중위 함수는 infix fun으로 선언
// 중위 함수 조건:
// 1. 멤버 함수이거나 확장 함수만 가능
// 2. 한개의 파라미터
// 3. infix 키워드

infix fun Int.nameAndAge(name: String): String = "Hi, I'm $name and $this years old"

fun infixFunTest() {
    println(30 nameAndAge "kotlin")
    println(30.nameAndAge("kotlin"))
}

// 중위 함수는 산술 연산자, 형변환, rangeTo 보다 우선 순위가 낮음
// 1 shl 2 + 3 = 1 shl (2 + 3)
// 0 until n * 2 = 0 until (n * 2)
// xs union ys as Set<*> = xs union (ys as Set<*>)
// &&, ||, is, in 등 다른 연산자보다는 우선순위가 높음
// a && b xor c = a && (b xor c)
// a xor b in c = (a xor b) in c
// 중위 함수는 항상 호출자와 파라미터 명시 필수

class ForInfixFunc {
    infix fun infixFunc(arg: Int) {}

    fun callInfixFunc() {
        this infixFunc 0 // 가능
        infixFunc(0) // 가능
        // infixFunc 0 불가
    }
}

// 클래스 없이 최상위 레벨 함수 선언 가능(자바는 불가능)
// 로컬, 멤버, 확장 함수 모두 선언 가능

// 지역 함수는 외부 함수의 지역 변수 사용 가능
fun localFuncTest() {
    val varForLocalFunc = 0
    fun localFunc() = varForLocalFunc.plus(0) // 참조 가능
}

// 멤버 함수는 클래스 혹은 오브젝트 내부에 선언된 함수

class ForMmeberFunc {
    fun memberFunc() {}
}

// 멤버 함수의 호출은 . 사용
fun callMemberFunc() {
    ForMmeberFunc().memberFunc()
}

// 제네릭 함수는 타입 파라미터를 가지는 함수

fun <T> genericFunc(t: T) {}

// 꼬리 재귀 함수는 tailrec 키워드가 붙은 재귀 함수를 의미
// 꼬리 재귀 함수는 스택오버플로우 위험이 없게 코드를 최적화 해줌(loop 코드로 컴파일)
// 다만 조건에 맞는 형식 작성 필수
tailrec fun tailRecursiveFunc(arg: UInt, total: UInt = arg): UInt { // 인자까지의 모든 수를 더하는 함수
    return if (arg == 0u) total
    else tailRecursiveFunc(arg - 1u, total + (arg - 1u))
}
// 자신 호출 이후에 다른 구문이 있다면 tailrec 불가
// try/catch/finally 블록에서는 불가