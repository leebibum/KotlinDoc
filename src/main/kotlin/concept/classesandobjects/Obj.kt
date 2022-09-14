package concept.classesandobjects

// 익명 객체 생성은 객체식과 객체 선언으로 나뉨

// 객체식은 익명 클래스 객체를 생성
// 정의, 상속, 인터페이스 구현을 처음부터 가능
val obj = object {
    val pram = 0

    fun func() {}
}

// 다른 클래스를 상속받는 익명 객체 생성
// 다중 상속은 클래스와 동일
// 다중 상속 시 접근 제한자가 private가 아니라면 변수 타입 명시 필수
abstract class BaseForObj {
    abstract fun absFunc()
}

interface IntfForObj {
    fun intfFunc()
}

val objWithExtend: BaseForObj = object : BaseForObj(), IntfForObj {
    override fun absFunc() {}
    override fun intfFunc() {}
}

// 클래스 내부에서 익명 객체가 private 함수, 속성일때만 모든 멤버 접근 가능
// 인라인 함수일 경우 불가
class PrivateObjMember {
    private fun objFunc() = object {
        val prop = 0
    }

    fun objFuncPublic() = object {
        val prop = 0
    }

    private inline fun objFuncInline() = object {
        val prop = 0
    }

    private val obj = object {
        val prop = 0
    }

    val objPublic = object {
        val prop = 0
    }

    fun accessTest() {
        objFunc().prop // 접근 가능
        // getObjPublic().prop 접근 불가 컴파일 에러
        // getObjInline().prop 접근 불가 컴파일 에러
        obj.prop // 접근 가능
        // objNotPublic.prop 접근 불가 컴파일 에러
    }
}

// 익명 객체 함수, 속성이 public일 경우 실제 타입은 아래와 동일
// 슈퍼 타입이 없는 경우 Any
// 슈퍼 타입이 있는 경우 해당 객체
// 슈퍼 타입이 다수인 경우 명시적으로 선언된 반환 타입(반환 타입 생략 불가)
// ㄴ 속성의 경우 속성의 타입을 명시하지 않으면 private으로만 선언 가능
class PublicObjMember {
    val obj = object {} // Any

    fun objFunc() = object {} // Any

    val objWithBase = object : BaseForObj() { // BaseForObj
        override fun absFunc() {}
    }

    fun objFuncWithBase() = object : BaseForObj() { // BaseForObj
        override fun absFunc() {}
    }

    val objWith2Base: IntfForObj = object : BaseForObj(), IntfForObj { // IntfForObj
        override fun absFunc() {}
        override fun intfFunc() {}
    }

    fun objFuncWith2Base(): IntfForObj = object : BaseForObj(), IntfForObj { // IntfForObj
        override fun absFunc() {}
        override fun intfFunc() {}
    }

    fun typeTest() {
        val any: Any = obj
        val any2: Any = objFunc()

        val baseForObj: BaseForObj = objWithBase
        val baseForObj2: BaseForObj = objFuncWithBase()

        val intfForObj: IntfForObj = objWith2Base
        val intfForObj2: IntfForObj = objFuncWith2Base()
    }
}

// 객체식의 블록 안은 스코프 바깥 변수 접근 가능
class ScopeOfObj {
    val prop = "kotlin"

    val obj = object {
        val length = prop.length
    }
}

// 싱글톤 패턴을 오브젝트로 쉽게 정의 가능
// 오브젝트의 초기화는 thread-safe, 최초 접근 시 자동 생성
object Obj {
    const val prop = 10 // 오브젝트의 기본 자료형 속성은 const 권장
    fun func() {}
}

// 오브젝트 참조
fun objReference() {
    Obj.func()
}

// 오브젝트도 상속, 구현 가능
object ObjWithBase : BaseForObj(), IntfForObj {
    override fun absFunc() {}
    override fun intfFunc() {}
}
// 오브젝트 선언은 지역 불가, 다른 오브젝트 혹은 non-inner 클래스 안에 중첩은 가능

// companion object 키워드로 일반 클래스에 정적 멤버 선언 가능
class CompanionObj {
    companion object CompanionObjName { // 이름 생략 가능, 생략할 경우 Companion이 자동으로 사용
        const val prop = 10 // 컴패니언 오브젝트의 속성도 기본 자료형이라면 const 권장
        fun func() {}
        private const val privateProp = 0
    }

    val prop = privateProp // 클래스 멤버는 컴패니언 오브젝트의 모든 멤버 참조 가능
}

// 컴패니언 오브젝트 참조
fun companionObjReference() {
    CompanionObj.CompanionObjName.prop // 정적으로 참조, 이름 생략 가능
}
// static과 동일하게 보이지만 런타임 시 실제 인스턴스의 멤버로 취급

// 인터페이스 구현 가능
class CompanionObjWithIntf {
    companion object : IntfForObj {
        override fun intfFunc() {}
    }
}
// @JvmStatic 어노테이션으로 JVM에서 static으로 사용 가능

// 초기화 시점
// 오브젝트 식은 사용될때 즉시 초기화 (익명 객체)
// 오브젝트 선언은 최초 접근시 초기화 (object 정의)
// 컴패니언 오브젝트는 클래스가 로드될때 초기화