package concept.classesandobjects

// 클래스 상속 없이 새 함수, 속성 생성 가능
// 혹은 수정할 수 없는 클래스에 새 함수, 속성 생성 가능
// 본래 멤버 함수와 속성처럼 호출 가능

// 확장 함수 정의
fun <T> MutableList<T>.swap(index1: Int, index2: Int) { // 확장 대상 클래스.함수명(파라미터)
    // this 키워드는 호출 객체
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun doSWap() {
    val list = mutableListOf(1, 2, 3, 4, 5) // [1, 2, 3, 4, 5]
    println(list)

    list.swap(1, 2)
    println(list) // [2, 1, 3, 4, 5]
}

// 확장은 클래스를 실제로 수정하지 않기 때문에 정적으로 동작
open class ExtnBase
class ExtnDerived : ExtnBase()

fun ExtnBase.getName() = "Base"
fun ExtnDerived.getName() = "Derived"

fun printName(arg: ExtnBase) {
    println(arg.getName())
}

fun extnFuncTest() {
    printName(ExtnBase()) // Base 출력
    printName(ExtnDerived()) // Base 출력
}

// 본래 멤버와 동일한 시그니쳐의 확장 함수는 무효화
class SameSigExtn {
    fun sameSigExtnFunc() = println("Class method") // 실행
}

fun SameSigExtn.sameSigExtnFunc() = println("Extension function") // 미실행

// nullable 확장 가능
fun String?.printLength() {
    if (this == null) return

    println(this.length) // 스마트 캐스팅
}

// 확장 속성 정의
// val 속성은 getter, var 속성은 getter, setter 모두 정의 필수
val <T> List<T>.lastIndex: Int
    get() = this.size + 1
// 확장 속성은 field 사용 불가
// set(value) { field = value } 컴파일 에러

// 직접적인 값 추가도 불가
// val String.name = "kotlin" 컴파일 에러
// 속성은 객체를 참조하여 얻은 값만 가능하다고 보면 됨

// 컴패니언 오브젝트 확장 가능
// 컴패니언 오브젝트 확장 멤버는 정적으로 호출
class CompanionObjExtn {
    companion object
}

fun CompanionObjExtn.Companion.extnFunc() = println("companion obj extn func")

fun callCompanionObjExtn() {
    CompanionObjExtn.extnFunc() // 정적 호출
}
// 최상위 레벨에 정의된 확장 멤버는 임포트를 통해 다른 패키지에서 호출 가능

// 클래스 멤버 선언된 다른 클래스의 확장 멤버는 클래스 내부에서만 호출 가능
class ToExtn {
    fun sameSigFunc() {
    }
}

class MemberExtnFunc {
    fun sameSigFunc() {}

    fun ToExtn.extnFunc() { // 현재 클래스 내부에서만 호출 가능
        // 이 클래스와 확장되는 클래스 멤버의 시그니쳐가 겹칠 경우 this@Class로 명시
        sameSigFunc() // 호출 객체 우선
        this@MemberExtnFunc.sameSigFunc() // 현재 클래스 멤버
    }

}

fun memberExtnFuncTest() {
    // ToExtn().extnFunc() 컴파일 에러
    // MemberExtnFunc().extnFunc() 컴파일 에러
}

// 클래스 멤버로 선언된 확장 함수는 open으로 오버라이드 가능
open class ToExtnBase

class ToExtnDerived : ToExtnBase()

open class BaseCaller {
    open fun ToExtnBase.call() = println("BaseCaller에서 호출한 ToExtnBase.call()")

    open fun ToExtnDerived.call() = println("BaseCaller에서 콜한 ToExtnDerived.call()")

    fun callExtnFunc(base: ToExtnBase) = base.call()
}

class BaseCallerDerived : BaseCaller() {
    override fun ToExtnBase.call() = println("BaseCallerDerived에서 호출한 ToExtnBase.call()")

    override fun ToExtnDerived.call() = println("BaseCallerDerived에서 콜한 ToExtnDerived.call()")
}

fun extnFuncOverrideTest() {
    BaseCaller().callExtnFunc(ToExtnBase()) // BaseCaller에서 호출한 ToExtnBase.call()
    BaseCaller().callExtnFunc(ToExtnDerived()) // BaseCaller에서 콜한 ToExtnBase.call()

    BaseCallerDerived().callExtnFunc(ToExtnBase()) // BaseCallerDerived에서 호출한 ToExtnBase.call()
    BaseCallerDerived().callExtnFunc(ToExtnDerived()) // BaseCallerDerived에서 콜한 ToExtnBase.call()
}

