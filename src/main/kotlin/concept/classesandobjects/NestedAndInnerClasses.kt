package concept.classesandobjects

// 클래스 내부에 중첩 클래스 정의 가능
// 중첩 클래스는 아우터 클래스에서 정적으로 접근 인스턴스로는 접근 불가
// 사실상 서로 다른 클래스
// 인터페이스도 중첩 가능
// 클래스에 인터페이스, 인터페이스 안에 클래도 중첩 가능
// 중첩 클래스는 아우터 클래스의 멤버 참조 불가능
// 아우터 클래스 인스턴스 생성하면 가능
class OuterClassWithNested {
    val outerProp = 0

    class NestedClass {
        val nestedProp = 0

        fun outerAccessTest() {
            // outerProp 접근 불가 컴파일 에러
            OuterClassWithNested().outerProp // 접근 가능
        }

    }

    interface NestedIntf { //
        val prop get() = 1

        fun outerAccessTest() {
            // outerProp 접근 불가 컴파일 에러
            OuterClassWithNested().outerProp // 접근 가능
        }
    }
}

fun nestedClassTest() {
    OuterClassWithNested.NestedClass().nestedProp
    // OuterClassWithNested().NestedClass().nestedProp 컴파일 에러
}

interface OuterIntfWithNested {
    // 접근 제한 사항 전부 동일
    interface NestedIntf {
        val prop get() = 1
    }

    class NestedClass {
        val prop = 1
    }
}

// inner로 표시된 클래스는 아우터 클래스의 멤버
// 인터페이스는 불가능
// 이너 클래스는 아우터 클래스 모든 멤머 참조 가능
// 이너 클래스는 아우터 클래스의 인스턴스 생성 후 생성 가능
class OuterClassWithInner {
    private val privateProp = 0

    open inner class Inner {
        val prop = privateProp // 접근 가능
    }
}

fun innerClassTest() {
    OuterClassWithInner().Inner().prop
    // OuterClassWithInner.InnerClass().prop 컴파일 에러
}

// 익명 객체 생성(클래스도 가능)
interface IntfForAnonObj {
    fun absFunc()
    fun nonAbsFunc() {}
}

val anonObj = object : IntfForAnonObj {
    override fun absFunc() {}

    // 비추상 멤버도 오버라이드 가능
    override fun nonAbsFunc() {}
}
