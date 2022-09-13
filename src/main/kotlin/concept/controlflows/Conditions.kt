package concept.controlflows

const val x = 10
const val y = 20

// 코틀린의 if는 값 반환 가능
// 삼항연산자 불필요 미존재
// 식으로 사용 시 else 생략 불가
val resultByIfExpr = if (x > y) x else y

// 중괄호 안이 여러줄일 경우 마지막 줄이 반환값
val resultByIfExpr2 = if (x > y) {
    println("x 반환")
    x // 반환
} else {
    println("y 반환")
    y // 반환
}

// when
// switch의 코틀린 버전
// 최초로 만족하는 하나의 분기만 실행
// 식으로 사용 가능하며 식으로 사용 시 else 생략 불가
fun whenSt() {
    when (x) { // 문
        1 -> println("x는 1입니다.")
        2 -> println("x는 2입니다.")
    }
}

val resultByWhenExpr = when (x) { // 식, else 생략 불가
    1 -> "x는 1입니다."
    2 -> "x는 2입니다."
    else -> "x는 1도 2도 아닙니다."
}

// 각 분기의 중괄호는 여러줄일 수 있으며, 여러줄일 경우 마지막 줄이 반환값
val resultByWhenExpr2 = when (x) {
    1 -> {
        println("x는 1입니다.")
        "x는 1입니다."
    }

    2 -> {
        println("x는 2입니다.")
        "x는 2입니다."
    }

    else -> {
        println("x는 1도 2도 아닙니다.")
        "x는 1도 2도 아닙니다."
    }
}


// 다중 조건 가능
fun whenStWithMultiCase() {
    when (x) {
        0, 1 -> println("x는 0 혹은 1입니다.")
        2, 3, 4 -> println("x는 2 혹은 3 혹은 4입니다.")
    }
}

// 식의 반환값과 직접 비교 가능
fun whenStWithReturnVal() {
    when (x) {
        getSomeInt() -> println("x는 ${getSomeInt()}입니다.")
        10 + 10 -> println("x는 ${10 + 10}입니다.")
    }
}

// in과 !in으로 범위 포함 여부 혹은 컬렉션 포함 여부 확인 가능(if도 가능)
fun whenStWithIn() {
    val intArr = intArrayOf(1, 2, 3, 4, 5)

    when (x) {
        in 0..10 -> println("x는 0~10 사이에 있습니다.")
        !in intArr -> println("x는 intArr에 없습니다.")
    }
}

// is와 !is로 변환 가능 여부 확인 가능(스마트 캐스팅 동작)
fun whenStWithIs(any: Any) {
    when (any) {
        is Int -> println("any는 Int로 변환할 수 있습니다.")
        is String -> println("any는 String으로 변환할 수 있으며, 길이는 ${any.length}입니다.")
    }
}

// if-else 대체 가능

fun whenStWithNoArg(any: Any) {
    // 인자 없이 사용 가능, 이때는 논리식 사용
    // 또한 여러 객체를 사용 가능
    when {
        x.isEven() -> println("x는 짝수 입니다.")
        y.isOdd() -> println("y는 홀수 입니다.")
        any is String -> println("any는 String으로 변환할 수 있습니다.")
    }
}

fun whenStWithBooleanEnumSealed(boolean: Boolean, color: Color, language: Language) {
    // Boolean, enum, sealed 혹은 이들의 nullable 타입을 인자로 받고 모든 조건을 다루지 않을 경우 else 필수

    // Boolean
    when (boolean) {
        true -> println("boolean은 true입니다.")
        false -> println("boolean은 false입니다.") // 없으면 컴파일 에러
    }

    // enum
    when (color) {
        Color.RED -> println("color는 RED입니다.")
        Color.BLUE -> println("color는 BLUE입니다.")
        Color.GREEN -> println("color는 GREEN입니다.") // 하나라도 없으면 컴파일 에러, 혹은 else로 처리
    }

    // sealed
    when (language) {
        Kotlin -> println("language는 kotlin입니다.")
        Java -> println("language는 Java입니다.")
        Ruby -> println("language는 Ruby입니다.")  // 하나라도 없으면 컴파일 에러, 혹은 else로 처리
    }
}

fun whenWithReturnValue() {
    // 인자에 선언문 가능, 해당 변수는 when식 내에서만 사용 가능
    when (val any = getAny()) {
        is String -> {
            println("any는 String으로 변환할 수 있습니다.")
            println(any)
        }

        is Int -> {
            println("any는 Int로 변환할 수 있습니다.")
            println(any)
        }
    }
}

fun getAny(): Any = "hello"

fun getSomeInt(): Int = 10

enum class Color {
    RED, BLUE, GREEN
}

sealed class Language

object Kotlin : Language()
object Java : Language()
object Ruby : Language()

fun Int.isOdd(): Boolean = this % 2 != 0

fun Int.isEven(): Boolean = this % 2 == 0
