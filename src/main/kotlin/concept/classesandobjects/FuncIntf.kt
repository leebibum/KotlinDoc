package concept.classesandobjects

// 하나의 추상 메소드만 포함하는 인터페이스를 함수형 인터페이스라고 함
// 혹은 SAM(single abstract method interface)
// 여러 비추상 메소드를 가질 수 있지만 추상 메소드는 하나만 가능
// fun interface로 정의
fun interface FuncIntf {
    fun absFunc(prop: String) // 추상 메소드
    // fun absFunc2() 컴파일 에러

    fun nonAbsFunc() {

    }

    fun nonAbsFunc2() {

    }
}

// 람다식을 사용하여 간결한 문법 제공
// original
val funcIntfImpl = object : FuncIntf { // 익명 객체 생성
    override fun absFunc(prop: String) {
        println(prop)
    }
}

// SAM conversion
val funcIntfImplWithSamConversion = FuncIntf { println(it) }

// 타입 별명
typealias myFun = () -> Unit

fun interface FuncIntfVSTypeAlias {
    fun foo()
}

val typeAlias: myFun = { println("kotlin") }
val functionalInterface = FuncIntfVSTypeAlias { println("kotlin") }

// 둘은 비슷한 기능을 제공
// 함수형 인터페이스는 객체를 생성해야 하므로 더 많은 비용이 발생

// API가 특정한 매개 변수와 반환값을 가진 함수를 수용해야하는 경우 타입 별명 사용
// 함수보다 더 복잡한 엔티티를 수용해야하는 경우 함수형 인터페이스 사용
