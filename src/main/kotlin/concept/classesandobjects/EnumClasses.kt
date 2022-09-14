package concept.classesandobjects

// enum 키워드로 정의
// 각 상수는 enum 클래스의 객체이며 싱글톤과 같이 동작
enum class Enum {
    RED, BLUE, GREEN
}

// 각 상수 속성 소유 가능
enum class EnumWithProp(var prop: Int) {
    RED(0),
    BLUE(0),
    GREEN(0);
}

fun enumWithPropTest() {
    val blue1 = EnumWithProp.BLUE
    val blue2 = EnumWithProp.BLUE

    blue1.prop = 10
    println(blue2.prop) // 10
}

// 상수마다 개별적인 멤버 소유 가능
// 각 상수와 enum 클래스는 클래스의 대부분의 기능 보유
// enum 클래스의 멤버를 선언할때는 상수와 분리하기 위해 상수 마지막에 세미콜론 사용
// enum 클래스의 멤버는 모든 상수가 공통으로 사용
enum class EnumWithFunc {
    RED {
        val redProp = 0
        fun redFunc() {}
        override fun absFunc() {}
        override val absVal = 0
    },
    BLUE {
        val blueProp = 0
        fun blueFunc() {}
        override fun absFunc() {}
        override val absVal = 1
    },
    GREEN {
        val greenProp = 0
        fun greenFunc() {}
        override fun absFunc() {}
        override val absVal = 2
    };

    abstract fun absFunc()
    abstract val absVal: Int

    val publicVal = 10 // 모든 객체가 소유
    fun publicFunc() {} // 모든 객체가 호출 가능
}

// 클래스 상속은 불가, 인터페이스 구현은 가능
interface IntfForEnum {
    fun func()
}

enum class EnumWithIntf : IntfForEnum {
    RED {
        override fun func() {}
    },
    BLUE {
        override fun func() {}
    },
    GREEN {
        override fun func() {}
    };

//    override fun foo() {} 이렇게도 가능
}

// 기본 제공 메소드
fun enumFunc() {
    val enums = Enum.values() // 상수 배열 반환
    val enum = Enum.valueOf("RED") // 인자에 상수의 이름과 다른 값이 들어오면 예외 발생

    val enums2 = enumValues<Enum>()
    val enum2 = enumValueOf<Enum>("RED")
}

// 또한 모든 상수는 이름과 포지션을 위해 아래와 같은 속성 소유
fun enumProps() {
    val enum = Enum.RED
    enum.name // 이름
    enum.ordinal // 순서 번호
}