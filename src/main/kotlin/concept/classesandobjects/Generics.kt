package concept.classesandobjects

// 클래스 타입 파라미터 가능
class Generic<T>(prop: T)

// 인스턴스 생성
fun createGeneric() {
    val generic = Generic<String>("kotlin")

    // 파라미터에 T 타입 인자가 들어오는 경우 추론할 수 있으므로 자료형 생략 가능
    val genericOmit = Generic("kotlin")
}

// 무변성 - 일반적인 제네릭 타입 인스턴스의 관계
// 공변성 <out T> - T의 서브 타입 제네릭 인스턴스가 T 타입 인스턴스로 업캐스팅 가능
// ㄴ Foo<Number> 타입 변수에 Foo<Int> 타입 변수 할당 가능
// 반공변성 <in T> - T의 수퍼 타입 제네릭 인스턴스가 T 타입 인스턴스로 다운 캐스팅 가능
// ㄴ Foo<Int> 타입 변수에 Foo<Number> 타입 변수 할당 가능

// 클래스를 정의하면서 변성 지정 가능
// 이를 선언 지점 변성이라 함

// 공변성 <out T>
// 파라미터를 제외한 T 타입 인자 수용 불가
// T 타입 인자 반환만 가능, 따라서 T 타입 속성은 val만 가능
class Covariance<out T>(val prop: T) { // var일 시 컴파일 에러
    fun get() = prop

    // fun set(prop: T) {} 컴파일 에러
}

fun covarianceTest() {
    val covarianceNumber: Covariance<Number> // Number 하위 타입 수용 가능
    var covarianceInt: Covariance<Int> // Int 하위 타입 수용 가능

    covarianceNumber = Covariance(10) // Covariance<Int> -> Covariance<Number> 가능
    // covarianceInt = Covariance<Number>(10) 컴파일 에러 Covariance<Number> -> Covariance<Int> 불가능
}

// 반공변성
// T의 수퍼 타입 파라미터 수용 가능
// T 타입 반환 불가
// T 타입 속성 소유 불가
// T 타입 인자 수용만 가능
class Contravariance<in T> {
    fun printT(t: T) = println(t)
}

fun contraVarianceTest() {
    var contraVarianceNumber: Contravariance<Number> // Number의 수퍼 타입 수용 가능
    val contraVarianceInt: Contravariance<Int> // Int의 수퍼 타입 수용 가능

    // contraVarianceNumber = Contravariance<Int>() 컴파일 에러 ContraVariance<Int> -> ContraVariance<Number> 불가능
    contraVarianceInt = Contravariance<Number>() // ContraVariance<Number> -> ContraVariance<Int> 가능
}

// 클래스 사용 시 변성 지정 가능
// 이를 사용 지점 변성이라고 함
fun combine(from: ArrayList<Any>, to: ArrayList<Any>) {
    assert(from.size == to.size)
    for (item in from) to.add(item)
}

fun doCombine() {
    val intList = arrayListOf(1, 2, 3, 4, 5)
    val anyList = arrayListOf<Any>(6, 7, 8, 9, 10)

//    combine(intList, anyList) 컴파일 에러

    // ArrayList<Any>는 ArrayList<Int>의 요소를 수용할 수 있지만
    // combine()의 파라미터가 ArrayList<Any>로 지정되어있기 때문에 ArrayList<Int> -> ArrayList<Any>로 불가능
    // 이를 무변성이라고 함
}

// ArrayList 클래스는 수정할 수 없기 때문에 사용 지점에서 변성을 지정하여 해결 가능
// ArrayList<out Any>는 모든 타입 수용 가능
fun combineWithCovariance(from: ArrayList<out Any>, to: ArrayList<Any>) {
    assert(from.size == to.size)
    for (item in from) to.add(item)
}

fun doCombineWithCovariance() {
    val intList = arrayListOf(1, 2, 3, 4, 5)
    val anyList = arrayListOf<Any>(6, 7, 8, 9, 10)

    combineWithCovariance(intList, anyList) // ArrayList<Int> -> ArrayList<Any>
}

// 어떤 T가 들어와도 상관 없고 중요하지 않을 때는 <*> 사용
// <*>은 <out Any?>, <in Nothing>와 동일
// 반환되는 타입은 Any, 어떤 타입 인자도 수용 불가
fun starProjection(list: ArrayList<*>) { // 어떤 리스트가 들어올 지 알 수 없으며 중요하지 않음
    print(list.size)
    val any = list[0] // Any로 반환
    // list[0] = 1 컴파일 에러
}

// <*>로 사용할때 <out T>는 <out Any?>, <in T>는 <in Nothing>과 동일
class StarProjectionOut<out T>(val param: T) {
    fun get() = param
}

fun starProjectionOut(arg: StarProjectionOut<*>) {
    val any = arg.get() // Any?
}

class StarProjectionIn<in T> {
    fun set(t: T) {
        println(t)
    }
}

fun starProjectionIn(arg: StarProjectionIn<*>) {
    // arg.set() Nothing만 가능
}

// <T : Base>와 같은 상위 타입 경계 파라미터는 <out Base>와 동일
class StarProjection<T : Number>(var param: T) {
    fun get() = param
    fun set(t: T) {
        param = t
    }
}

fun starProjection(arg: StarProjection<*>) {
    val number = arg.get() // Number
    // arg.set() Nothing만 가능
}

// <out T : Base>와 같은 상위 타입 경계 파라미터는 <out Base>와 동일
class StarProjection2<out T : Number>(val param: T) {
    fun get() = param
}

fun starProjection(arg: StarProjection2<*>) {
    val number = arg.get() // Number
}

// <in T : Base>와 같은 상위 타입 경계 파라미터는 <in Nothing>과 동일
class StarProjection3<in T : Number> {
    fun set(t: T) {
        println(t)
    }
}

fun starProjection(arg: StarProjection3<*>) {
    // arg.set() Nothing만 가능
}
// 기본적으로 스타 프로젝션은 인자 수용 불가라고 알고 있으면 됨

// 타입 파라미터에 다수의 타입이 있다면 각각 스타 프로젝션 적용 가능
// Foo<T, U>
// <*, U>는 <<out Any?, in Nothing>, U>와 동일

// Foo<in T, out U>와 같은 경우
// Foo<*, U>는 <in Nothing, out U>
// Foo<T, *>는 <in T, out Any?>
// Foo<*, *>은 <in Nothing, out Any?>

class StarProjection5<in T, out U>(val prop: U) {
    fun getU(): U = prop

    fun setT(t: T) = println(t)
}

fun starProjectionTest(arg: StarProjection5<*, Int>) {
    // arg.setT() Nothing만 가능
    val int = arg.getU() // Int
}

fun starProjectionTest2(arg: StarProjection5<Int, *>) {
    arg.setT(10)
    val any = arg.getU() // Any?
}

fun starProjectionTest3(arg: StarProjection5<*, *>) {
    // arg.setT() Nothing만 가능 사실 상 불가능
    val any = arg.getU() // Any?
}


// 함수도 타입 파라미터 사용 가능
// 함수명 앞에 위치
fun <T> genericFunc(arg: T) {}
fun <T> genericFunc2(arg: String) {}

// 타입 파라미터 확장 함수 가능
fun <T> T.genericExtensionFunc(arg: T) {}
fun <T> T.genericExtensionFunc2(arg: String) {}

// 제네릭 함수 호출
// 마찬가지로 파라미터로 T 타입 인자가 들어온다면 타입 파라미터 추론 가능
fun callGenericFunc() {
    genericFunc("kotlin")
    "kotlin".genericExtensionFunc("kotlin") // 호출 객체로 T 추론
    10.genericExtensionFunc(10)

    genericFunc2<String>("kotlin")
    "kotlin".genericExtensionFunc2("kotlin") // 호출 객체로 T 추론
    10.genericExtensionFunc2("kotlin")
}

// <T : Base>로 타입 파라미터를 Base의 서브 클래스로 제한 가능
// 이를 상위 타입 경계라고 함
// 클래스 제한은 하나만 가능하며 인터페이스는 다수 가능
// 두개 이상의 제한 필요시 where 절 작성
open class UpperBoundClass
interface UpperBoundIntf

class UpperBound<T> where T : UpperBoundClass, T : UpperBoundIntf

fun <T> multipleUpperBound() where T : UpperBoundClass, T : UpperBoundIntf {}
// T는 where 절의 모든 조건 만족 필수

// 런타임 시에는 모든 타입 파라미터의 정보 소멸
// 따라서 ints is List<int>와 같은 is-check 금지
// ints is List<*>와 같이 스타 프로젝션 is-check, 캐스팅 가능
fun starProjectionIsCheckTest(arg: Any) {
    // if (ints is List<Int>) ints.forEach { println(it) } 컴파일 에러
    if (arg is ArrayList<*>) arg.forEach { println(it) } // 가능

    val list = arg as? ArrayList<*>
    list?.forEach { println(it) }
}

// 정적으로 확인된 제네릭 인스턴스는 is-check 혹은 캐스팅 가능
// 이때는 <> 생략
fun staticCheckedGenericIsCheckTest(arg: List<Int>) {
    if (arg is ArrayList) arg.forEach { println(it) }

    val list = arg as? ArrayList
    list?.forEach { println(it) }
}

// 제네릭 함수의 타입 파라미터로 Foo is T와 같은 is-check 불가능
// 캐스팅은 가능하지만 unchecked 경고
// ㄴ 컴파일러가 안성정을 보증하지 않음
// reified 키워드를 사용한 타입 파라미터는 가능
// reified 키워드는 인라인 함수에만 사용 가능
// 타입 체크와 형변환 안에서 사용되는 제네릭 인스턴스는 여전히 타입이 지워짐
// ㄴ List<Int> is T는 List is T와 동일
inline fun <reified T, reified U> getPair(arg1: Any, arg2: Any): Pair<T, U>? {
    if (arg1 !is T || arg2 !is U) return null
    return arg1 as T to arg2 as U
}

fun reifiedTest() {
    val pair1 = getPair<String, List<Int>>("kotlin", listOf(1, 2, 3, 4, 5))
    // kotlin, List<Int>
    println(pair1)

    val pair2 = getPair<String, List<Int>>("kotlin", listOf("A", "B", "C", "D", "E"))
    // List<String> as List<Int>지만 <String>의 소멸로 List is List<Int>가 됨
    // val int = pair2?.second?.get(0) 하지만 Int로 캐스팅 할 수 없으며 ClassCastException 발생
    println(pair2)

    val pair3 = getPair<String, List<Int>>("kotlin", 1)
    println(pair3) // null
}