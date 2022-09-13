package concept.types.basictypes

// 코틀린에서 함수를 포함한 모든것은 객체
// 기본 자료형은 컴파일 타임에는 참조 자료형으로 취급되지만 런타임에서는 원시 자료형으로 변환(String 제외)

// 정수형 기본 사항 자바와 동일

val long = 2147483648 // Int 표현 범위 초과 시 Long

// 실수형 기본 사항 자바와 동일

val float = 0.0f // 접미사 생략 불가

// 유효 자릿수
val DecimalDigitsForFloat = 3.1415_926f // 소수점 밑 6-7 자리 나머지 올림 혹은 내림
val DecimalDigitsForDouble = 3.1415_9265_3589_7932_384 // 소수점 밑 15-16 자리 나머지 올림 혹은 내림

// 숫자 리터럴
val hexadecimal = 0x0f // 16진법 접두사 0x
val binary = 0b0101 // 2집법 접두사 0b
// 8 진수는 미지원

val exponentialNotaionForDouble = 0e10 // 지수 표기법
val exponentialNotaionForFloat = 0e10f // 지수 표기법 Float

val underscore = 1000_0000 // 긴 숫자의 경우 _를 사용하여 자릿수 구분 가능

fun equalityCheck() {
    // nuallble과 generic은 런타임 시에도 참조 자료형

    val x = 1000
    val boxedX: Int? = x
    val anotherBoxedX: Int? = x

    val y = 100
    val boxedY: Int? = y
    val anotherBoxedY: Int? = y

    // 코틀린에서 equals()는 ==, 참조값 비교는 ===
    println(x == y) // false

    println(boxedX == anotherBoxedX) // true
    println(boxedX === anotherBoxedX) // false

    // -128~127 사이의 값은 캐시에 넣어서 사용하기 때문에 참조값 비교 항상 true(무의미)
    println(boxedY == anotherBoxedY) // true
    println(boxedY === anotherBoxedY) // true
}

fun casting() {
    val int = 100

    // 묵시적 형변환 불가
    // val long: Long = int 컴파일 에러

    val long = int.toLong()

    val result = long + int // 산술 연산의 경우 성능을 위해 묵시적 형변환 불필요
}

fun division() {
    // 정수 나눗셈은 소수점 이하 부분이 탈락된 정수 반환
    val result = 5 / 2
    // println(result == 2.5) 컴파일 에러
    println(result == 2)

    // 실수 반환을 위해서는 수 하나를 실수로 변환
    val result2 = 5 / 2.0
    println(result2 == 2.5)
}

class BitOperator {
    private val bit = 10 // 1010

    fun bitShift() {
        // shl
        println(bit shl 3) // 1010000 (80)
        // 모든 비트 왼쪽으로 이동, 이동으로 인한 빈자리는 0으로 대체, 인자 * 2^x와 동일
        // a x 2^3 = 10 x 8

        // shr
        println(bit shr 3) // 0001 (1)
        // 모든 비트 오른쪽으로 이동, 이동으로 인한 빈자리는 부호비트(양수 0, 음수 1)로 대체, 인자 / 2^x와 동일
        // a / 2^3 = 10 / 8

        // ushr
        println(bit ushr 3) //0001 (1)
        // 부호를 제외한 모든 비트 오른쪽으로 이동, 이동으로 인한 빈자리는 0으로 대체

        //음수의 경우 2의 보수까지 계산
    }

    fun bitWise() {
        // bit = 1010

        // and
        println(bit and 0b0010) // 0010 (2)
        // 모든 비트가 1인 경우 1 반환
        // check
        println(bit and 0b0100) // 0000 (0)
        // 확인하고 싶은 비트에 1을 넣어 check
        // clear
        println(bit and 0b0101) // 0000 (0)
        // 0으로 만들고 싶은 비트에 0을 넣어 clear

        // or
        println(bit or 0b0101) // 1111 (15)
        // 둘 중 하나라도 1이면 1 반환
        // set 사용
        println(bit or 0b0100) // 1110 (14)
        // 1로 만들고 싶은 비트에 1을 넣어 set

        // xor
        println(bit xor 0b1010) // 0000 (0)
        // 두 비트가 같다면 0 다르다면 1 반환
        // compare 사용
        println(bit xor 0b0101) // 1111 (15)

        // inv()
        println(bit.inv()) // 0101
        // 모든 비트 반전
        // 2의 보수 계산 필요
    }
}

fun range() {
    val x = 7

    val range = 0..10 // 범위 인스턴스화 가능

    // 특정 값의 범위 확인
    println(x in range)
    println(x in 0..10)
    println(x !in range)
}

fun implicitConversionOfFunc() {
    // 함수의 파라미터도 묵시적 변환 불가

    // printDouble(0) 컴파일 에러
    // printDouble(0L) 컴파일 에러
    printDouble(0.0)
}

private fun printDouble(double: Double) = println(double)