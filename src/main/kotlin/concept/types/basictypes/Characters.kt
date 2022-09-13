package concept.types.basictypes

// 기본 사항 자바와 동일

// 유니코드 인코딩
val encodedUnicode = '\u0041' // 'A'

fun castToInt() {
    // Char의 값이 숫자인 경우 digitToInt()로 해당 숫자를 Int로 변환 가능
    val charOne = '1'
    println(charOne.digitToInt()) // 1

    // println(charOne.toInt()) Char 자료형을 숫자로 변환하는 함수는 모두 deprecated
    println(charOne.code) // toInt() 대체

    // Char의 값이 숫자인 경우 - '0'으로 Int로 변환 가능
    println(charOne - '0') // 1
    // ‘0’의 코드값은 48, ‘1’의 코드값은 49, ‘2’의 코드값은 50 순으로 증가
    // 따라서 49 - 48과 같으므로 Int로 변환 가능

    // 숫자 자료형의 경우 toChar() 호출 시 해당 숫자에 해당하는 문자 반환
    // 숫자 자체를 Char로 변환하기 위해서는 digitToChar() 호출
    val int = 9

    println(int.toChar()) // ?
    println(int.digitToChar()) // 9
}