package concept.classesandobjects

// wrapper class는 기본 타입을 객체로 사용하기 위한 클래스
// 다만 기본 타입이 아니어도 하나의 데이터 타입을 사용하면 랩퍼 클래스라고 함
// 코틀린의 기본 자료형들은 컴파일러가 원시 자료형으로 변환해주기 때문에 성능이 좋지만
// 커스텀 랩퍼 클래스는 그렇지 않기 때문에 성능 저하가 발생
// 이때 inline class로 해결 가능
// 인라인 클래스는 값을 담기 위해서만 존재
// value class와 @Jvminline으로 정의
// 주 생성자에서 초기화되는 속성 하나만 소유 가능
// 런타임 시 인라인 클래스는 단일 속성으로 변환
// 함수, init 가능
@JvmInline
value class Inline(val prop: String) {
    init {
        println("초기화 블록 실행")
    }

    val length get() = this.prop.length // getter를 가진 속성은 소유 가능, lateinit, 위임 속성 불가

    fun func() {}
}

// 인터페이스 구현 가능
interface IntfForInline {
    fun func()
}

@JvmInline
value class InlineForIntf(val prop: String) : IntfForInline {
    override fun func() {}
}
// 인라인 클래스는 다른 클래스를 상속할 수 없으며 final 상태(open 불가)

// 인라인 클래스는 다른 타입으로 사용될때 boxing됨
fun asInline(arg: InlineForIntf) {}
fun <T> asGeneric(arg: T) {}
fun asInterface(arg: IntfForInline) {}
fun asNullable(arg: InlineForIntf?) {}
fun <T> getY(arg: T): T = arg

fun inlineBoxingTest() {
    val x = InlineForIntf("kotlin")

    asInline(x) // unboxed, InlineForIntf로 사용
    asGeneric(x) // boxed, Generic T로 사용
    asInterface(x) // boxed, IntfForInline로 사용
    asNullable(x) // boxed, IntfForInline?로 사용

    val y = getY(x) // 처음엔 T로 boxed, 반환될때 unboxed, y는 "kotlin"으로 사용됨
}

// JVM에서 시그니쳐 충돌 발생 가능
fun func(arg: Inline) {} // public final void func(String arg)
fun func(arg: String) {} // public final void func(String arg)
// 이를 해결하기 위해 JVM에서는 함수명에 해시코드 자동으로 추가
// public final void func-<hashcode>(String arg)

// 자바에서도 인라인 클래스를 수용하는 함수 호출 가능
// @JvmName으로 직접 자바에서 사용될 함수명 지정 가능
@JvmName("funcInlineForIntf")
fun func(arg: InlineForIntf) {
}

// 위임으로 인터페이스 랩퍼 생성 가능
@JvmInline
value class IntfWrapper(val intf: IntfForInline) : IntfForInline by intf

fun intfWrapperTest() {
    val intfWrapper = IntfWrapper(object : IntfForInline {
        override fun func() {}
    })

    intfWrapper.func()
}