package concept.classesandobjects

// 속성 선언 전체 문법
/*
 * var <propertyName>[: <PropertyType>] [= <property_initializer>]
 *  [<getter>]
 *  [<setter>]
 */
// 초기화, getter, setter
// 초기화로 타입을 추론할 수 있다면 자료형도 선택 사항
// val은 setter 미존재

// 내부에서 속성 선언 시 초기화 필수
// 사용자 지정 getter가 있는 경우, 위임 속성일 경우는 예외


// 사용자 지정 getter
val valPropWithGetter get() = "kotlin"
// getter는 모든 호출 시 호출
// getter가 있는 경우 자료형 생략 가능
// 호출 시 매번 같은 값이 반환되기 때문에 var은 의미 없음

// 사용자 지정 setter
// 초기화를 제외한 모든 할당 시 호출
var propWithSetter: String = "kotlin"
    set(value) { // 컨벤션에 따라 파라미터 이름은 value로 권장되지만 다른 이름 가능
        println(value) // 이 블록은 Unit만 리턴 가능(실제로 속성에는 미할당)
        // 실제로 할당하고 싶을 경우 backing field 사용
        field = value
    }
// 접근자 내부에서 자기 자신을 참조할 경우 staackOverFlowException 발생
// 이를 해결하기 위해 field 사용
// field는 속성 자기 자신을 의미, 이를 backing field라고 함

// field로 구현할 수 없는 무언가를 구현해야 할때 새로운 속성을 정의하여 해결 가능
// 이를 backing property라고 함
private var _arr: ArrayList<String>? = null

val arr: ArrayList<String>
    get() {
        if (_arr == null) // field로 null 체크 불가
            _arr = arrayListOf()
        return _arr ?: throw AssertionError("다른 스레드에서 null 부여됨")
    }

// 접근 제한자와 어노테이션이 필요한 경우 본문에 정의하지 않고 정의 가능
// setter는 속성과 다른 접근 제한자 가능
// getter는 동일 접근 제한자만 가능
var propWithPrivateSetter: String = ""
    // private get 컴파일 에러
    private set // 함수 바디가 비어있을 경우 생략 가능

// const val 키워드로 상수 정의(자바의 static final과 동일)
// 최상위 레벨 속성, object, companion object의 멤버만 가능
// 기본 자료형과 String만 가능
// getter 설정 불가
const val constVal = 10

// 컴파일러는 속성 대신 리터럴을 사용
// ::를 사용하여 상수와 상호작용 가능
val constValName = ::constVal.name // constVal

// lateinit var로 초기화 미루기 가능
// 최상위 레벨 속성, 클래스 속성, 지역 변수 가능
// nullable과 원시 자료형 불가능
// 주 생성자에 선언되었거나 사용자 지정 접근자를 가지고 있는 경우 불가능
class LateInit {
    lateinit var lateInitVar: String

    // lateinit 속성이 초기화 되기전 접근하면 예외 발생
    // ::와 .initialized를 사용하여 초기화 여부 확인 가능
    fun access() {
        if (::lateInitVar.isInitialized) println(lateInitVar)
    }
}

