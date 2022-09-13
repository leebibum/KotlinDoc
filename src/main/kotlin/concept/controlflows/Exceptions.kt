package concept.controlflows
// 코틀린은 예외를 강제하지 않음

// 예외를 던지기 위해서는 throw 사용

fun throwExc() {
    throw Exception("예외 메시지")
}

// try는 식
// try식이 반환하는 값은 try의 마지막 줄이거나 catch의 마지막 줄
// finally는 반환값에 영향을 미치지 않음
val result = try {
    println("try")
    0 // 반환
} catch (e: ClassCastException) {
    println("catch")
    0 // 반환
} finally {
    println("무조건 실행")
    0 // 미반환
}

// try-catch에서 catch, finally 둘 다 생략 가능, 하지만 둘 중 하나는 필수
fun tryCatch() {
//    try {
//        println("try") 컴파일 에러
//    }

    try {
        println("try")
    } finally {
        println("finally")
    }
}

// throw는 표현식이며 Nothing을 반환
val person = Person(null)

val name = person.name ?: throw IllegalArgumentException("Name required")

// Nothing 타입은 실행이 멈춤을 의미
fun fail(msg: String): Nothing { // 컴파일러는 이 함수가 호출되면 실행이 멈춘다는 걸 인지
    throw IllegalArgumentException(msg) // 예외가 발생하면 실행이 멈추기 때문에 리턴값 무의미, 따라서 사실상 뭘 리턴하든 무관
}

fun failTest(person: Person) {
    val name = person.name ?: fail("Name require")
    println(name) // 이 코드에 도달하면 name은 무조건 초기화된 상태
}

// null 타입 추론 시 Nothing?으로 추론, Nothing?에는 null만을 할당 가능
val nothing = null // Nothing
val nothingList = listOf(null) //List<Nothing>

class Person(var name: String?)
