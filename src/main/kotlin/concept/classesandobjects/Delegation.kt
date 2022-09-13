package concept.classesandobjects

// 위임은 객체에서 속성 및 함수를 위임 받을 수 있음
// 인터페이스만 위임 가능
interface IntfForDelegation {
    val absProp: Int
    fun absFunc()
}

open class IntfForDelegationImpl : IntfForDelegation {
    override val absProp get() = 0
    override fun absFunc() {}
}

class Delegated(param: IntfForDelegationImpl) : IntfForDelegation by param

fun delegationTest() {
    val delegated = Delegated(IntfForDelegationImpl())
    delegated.absProp // IntfForDelegationImpl()가 구현한 속성과 함수
    delegated.absFunc()
}

// 위임 받은 멤버 재정의 가능
class OverrideDelegatedMember(param: IntfForDelegation) : IntfForDelegation by param {
    override fun absFunc() {} // param의 구현 재정의
    override val absProp get() = 10 // param의 구현 재정의
}

// 재정의된 멤버는 위임 하는 객체만 접근 가능한 멤버에게는 미적용
interface IntfForDelegationOverride {
    val absProp: Int
    fun absFunc()
}

class DelegationOverrideImpl : IntfForDelegationOverride {
    override val absProp = 10
    override fun absFunc() = println(absProp) // absProp이 재정의 되어도 10을 출력함
}

class DelegationOverride(param: IntfForDelegationOverride) : IntfForDelegationOverride by param {
    override val absProp = 100
}

fun delegationOverrideTest() {
    val delegationOverrideImpl = DelegationOverrideImpl()
    val derived = DelegationOverride(delegationOverrideImpl)
    derived.absFunc() // 서브 클래스에서 foo를 100으로 재정의 했으므로 100이 출력되어야 하지만 원래 값인 10이 출력됨
    println(derived.absProp) // 속성 호출은 100 출력됨
}