package concept.types

fun isCheck(any: Any) { // Any는 최상위 자료형
    // 객체가 해당 자료형으로 변환되는지 검사
    // 조건문과 사용 시 해당 조건문 안에서만 자동으로 캐스팅(스마트 캐스팅)
    if (any is String) println(any.length)

    if (any !is String) println("obj의 타입은 String이 아닙니다.")
    else println(any.length)

    // !is-check가 false를 반환할 경우 밖 스코프 내에서 변환된채 유지
    if (any !is String) return // 이 라인 이후로는 계속 String으로 변환된 채 유지
    println(any.length)

    // 또한 ||, &&과 같이 사용 시 is-check 후 자동으로 변환
    if (any !is String || any.length != 10) return

    if (any is String && any.length == 10) println(any.length)

    // while문에서도 자동으로 변환
}

// 스마트 캐스팅 작동 조건
// val 지역 변수 - 지역 위임 속성을 제외하고 항상
// val 속성 - private, internal 이거나 속성이 선언된 같은 모듈에서 검사될 경우
// ㄴ open 속성이거나 사용자 지정 getter가 있는 경우 불가능
// var 지역 변수 - 람다에 캡쳐되지 않으며, 지역 위임 속성이 아닌 경우
// var 속성 - 불가능

fun asCast(any: Any?) {
    // as 연산자는 변수를 직접 변환하며 변환된 값을 반환하기도 함
    // 변환 실패 시 ClassCastException 발생
    val str = any as String
    println(any.length)
    println(str.length)

    // null은 String으로 변환할 수 없기 때문에 any가 null이라면 예외 발생

    // null에 가능하게 하려면 변환 타입에 nullalble 유형 사용
    val str2 = any as String?
    // 이때 변환된 변수의 타입과 반환된 값의 타입은 모두 nullable
    println(any?.length)
    println(str2?.length)

    // as? 연산자는 변환 실패 시 ClassCastException 대신 null 반환
    // 변수를 직접 변환하지 않음
    val str3 = any as? String // 반환된 값의 타입은 nullable
    println(str3?.length)
}
