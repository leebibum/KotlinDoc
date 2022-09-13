package concept.types.basictypes

// 정수형의 맨 앞 비트를 값으로 사용한 자료형
// 0부터 표현 가능
fun printMinMaxValue() {
    println("UByte의 표현 범위: ${UByte.MAX_VALUE}")
    println("UShort의 표현 범위: ${UShort.MAX_VALUE}")
    println("UInt의 표현 범위: ${UInt.MAX_VALUE}")
    println("ULong의 표현 범위: ${ULong.MAX_VALUE}")
}

// 일반 정수형의 기능 대부분 지원
val uByte: UByte = 0u // 리터럴은 접미사 u 혹은 U
val uShort: UShort = 0u
val uInt = 0u
val uLong = 0uL // UInt 표현 범위 초과 시 ULong

// 부호 없는 정수형 배열 지원
// 아직 베타, 언제든 삭제될 수 있음
// 배열과 동일한 API 제공
// 사용 시 경고 표시, ExperimentalUnsignedTypes 어노테이션으로 경고 제거 가능
@OptIn(ExperimentalUnsignedTypes::class)
val uByteArr = ubyteArrayOf(1u, 2u, 3u, 4u, 5u)

// 부호 없는 정수형으로 사용하는 범위 관련 기능들은 안정적
fun unSignedRange() {
    for (i in 0u..10u) println(i)
}

// Color 클래스의 인자는 Long을 사용하여 64비트를 사용해야하지만 uInt를 사용하면 32비트로 표현 가능
data class Color(val representation: UInt)

val yellow = Color(0xFFCC00CCu)