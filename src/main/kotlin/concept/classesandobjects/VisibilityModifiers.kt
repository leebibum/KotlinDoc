package concept.classesandobjects

// 접근 제한자 생략 시 기본값은 public
// private, protected, internal, public

// 패키지
// 최상위 레벨에 선언된 함수, 속성, 클래스, 오브젝트, 인터페이스 등에 적용
// public: 생략 시 기본값, 임포트된 모든 곳에서 접근 가능
// internal: 같은 모듈 내에서 접근 가능
// private: 선언된 파일 내에서만 접근 가능
// protected: 사용 불가

val publicProp = 10 // 임포트 된 모든 곳에서 접근 가능
internal val internalProp = 10 // 같은 모듈 내에서 접근 가능
private val privateProp = 10 // 현재 파일 내에서만 접근 가능
// 클래스 등 나머지 최상위 레벨 선언들 동일

// 클래스
// private: 클래스 내에서만 접근 가능
// protected: 서브 클래스에서만 접근 가능
// internal: 같은 모듈 내에서만 접근 가능
// public: 모든 곳에서 접근 가능

// 아우터 클래스는 이너 클래스의 private 멤버 접근 불가
// protected, internal 멤버를 재정의하고 접근 제한자를 명시하지 않으면 동일 접근 제한자 유지
// 또한 internal -> public, protected -> public 으로만 재정의 가능 나머지는 불가능
open class Visibility {
    val publicProp = 10 // 어디서나 접근 가능
    open internal val internalProp = 10 // 같은 모듈내에서만 접근 가능
    open protected val protectedProp = 10 // 서브 클래스에서만 접근 가능
    private val privateProp = 10 // 이 클래스에서만 접근 가능

    class VisibilityInner {
        val innerPublicProp = 10
        internal val innerInternalProp = 10
        protected val innerProtectedProp = 10
        private val innerPrivateProp = 10

        fun outerVisibilityTest() {
            publicProp // 접근 가능
            internalProp // 접근 가능
            // protectedProp 접근 불가
            privateProp // 접근 가능
        }
    }

    fun innerVisibilityTest() {
        val visibilityInner = VisibilityInner()
        visibilityInner.innerPublicProp // 접근 가능
        visibilityInner.innerInternalProp // 접근 가능
        // visibilityInner.innerProtectedProp 접근 불가
        // visibilityInner.innerPrivateProp 접근 불가
    }
}

class VisibilityDerived() : Visibility() {
    override val internalProp = 20 // internal
    public override val protectedProp = 20 // public
}