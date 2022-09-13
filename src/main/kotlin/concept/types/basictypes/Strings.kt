package concept.types.basictypes

// 기본 사항 자바와 동일

// 문자열의 단일 문자는 s[i]로 접근 가능, Char로 반환
val str = "kotlin"
val char = str[0] // Char k

fun strIterate() {
    // for문으로 문자열 순환 가능
    for (char in str) println(char)
}

// 문자열은 불변
// 문자열을 변환하는 모든 연산자와 함수는 새로운 문자열 객체를 생성하여 반환
val upperCase = str.uppercase() // 원본 유지

// 문자열 연결
// 문자열이 첫번째인 경우 다른 자료형과 연결 가능
val strLink = str + 0
// 대부분의 경우 문자열 템플릿 사용

// 원시 문자열
// 원시 문자열은 """로 묶인 문자열
// 원시 문자열은 \n 없이 개행 가능
// 또한 이스케이핑이 필요한 모든 문자를 \ 없이 표현 가능
val rawStr = """안녕하세요.
        반갑습니다."""

// 원시 문자열에서 개행 시 들여쓰기 발생
// 각 행에 접미사 |를 붙힌 후 trimMargin() 호출로 제거 가능
// 인텔리제이가 알아서 해줌
val rawStr2 = """안녕하세요.
            |반갑습니다.""".trimMargin()

// 접미사로 다른 특수 문자 사용 가능
// 이때 사용된 특수 문자는 trimMargin()의 인자로 넘겨주어야 함
val rawStr3 = """안녕하세요.
            >반갑습니다.""".trimMargin(">")

// 문자열 템플릿
// $를 사용하여 변수의 값을 문자열에 표현 가능
val strTemplate = "사용하는 언어: $str"

// 변수명 뒤에 변수명에 사용할 수 있는 문자가 오는 경우 중괄호 사용
val strTemplate2 = "이 언어의 이름은 ${str}입니다."

// 중괄호를 사용하여 식, 함수 표현 가능
val strTemplate3 = "이 언어의 이름은 ${str.uppercase()}입니다."

val x = 10
val y = 20
val strTemplate4 = "$x + $y = ${x + y}"

// 원시 문자열에서는 이스케이핑이 동작하지 않기 때문에 종종 표현할 수 없는 문자가 있음
// 이때 문자열 템플릿을 통해 해결 가능
// 또한 일반 문자열에서도 이와 같은 방법 사용 가능
val rawStrWithStrTemplate = """${'$'}99"""
val strTemplate5 = "${'$'}99"