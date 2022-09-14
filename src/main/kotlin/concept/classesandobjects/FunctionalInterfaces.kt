package concept.classesandobjects

// 하나의 추상 메소드만 포함하는 인터페이스를 함수형 인터페이스라고 함
// 혹은 SAM(single abstract method interface)
// 여러 비추상 메소드 소유 가능
// fun interface 수정자로 정의
fun interface FuncIntf {
    fun absFunc(prop: String)
    // fun absFunc2() 컴파일 에러

    fun nonAbsFunc() {}
    fun nonAbsFunc2() {}
}

// 람다식을 사용하여 간결한 문법 제공

// original
val funcIntfImpl = object : FuncIntf { // 익명 객체 생성
    override fun absFunc(prop: String) {
        // 구현
    }
}

// SAM conversion
val funcIntfImplWithSamConversion = FuncIntf {
// 구현 }
}