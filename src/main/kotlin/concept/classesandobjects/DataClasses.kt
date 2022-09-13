package concept.classesandobjects

// data 키워드로 정의
data class Data(val prop1: String, val prop2: Int)

// equals(), hashCode(), toString(), componentX(), copy() 자동 생성
// toString()의 형식은 "DataClass(name=kotlin, age=10)"과 같은 형식
// componentX()는 선언 순서에 따른 속성 반환 component1()은 prop1 반환

// 주 생성자는 최소 한 개 이상의 파라미터가 있어야 함
// 주 생성자의 파라미터는 모두 val, var로 표시 필수
// abstract, open, sealed, inner 불가

// equals(), hashCode(), toString()의 명시적인 구현이 있거나 슈퍼 클래스에 final로 정의된 경우
// 위 함수들은 생성되지 않고 존재하는 함수 사용
// 슈퍼 클래스에 componentX() 함수가 명시적으로 구현되어 있고 호환되는 타입을 반환하며 open일 경우 재정의
// 데이터 클래스에서는 componentX(), copy() 명시적 구현 불가능

// 자동 구현하고 싶지 않은 속성은 데이터 클래스 내부에 선언
data class NoImplProp(val prop1: String) {
    var prop2 = 10
}

fun equalsTest() {
    val noImplProp1 = NoImplProp("kotlin")
    val noImplProp2 = NoImplProp("kotlin")

    noImplProp1.prop2 = 10
    noImplProp2.prop2 = 20

    println(noImplProp1 == noImplProp2) // prop2는 equals() 자동 구현에서 제외되므로 prop2가 달라도 true 반환
}

// copy()를 사용하여 똑같이 복사하거나 일부 속성을 변경하여 복사 가능
fun doCopy() {
    val data = Data("kotlin", 10)
    val copyOfData = data.copy() // kotlin, 10

    val copyOfData2 = data.copy(prop2 = 10) // kotlin, 20
}

// 데이터 클래스 componentX()를 사용하여 여러 변수에 할당 가능
fun doDestructuring() {
    // Pair와 Triple 객체로 받을 수도 있지만 잘 네이밍된 변수에 받는게 더 나은 설계라고 함
    val data = Data("kotlin", 10)

    val (prop1, prop2) = data

    print("$prop1, $prop2") // kotlin, 10
}
