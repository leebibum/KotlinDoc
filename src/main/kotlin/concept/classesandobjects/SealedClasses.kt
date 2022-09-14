package concept.classesandobjects

// 상속을 더 잘 제어하기 위해 존재(abstract와 동일 개념)
// 컴파일러가 sealed 클래스의 모든 서브 클래스를 인지 가능
// sealed 클래스가 정의된 모듈 외부에서는 상속 불가(1.5부터 변경)
// sealed 인터페이스도 동일
// enum과 비슷하지만 enum은 싱글톤처럼 단일 객체로 존재하는 반면 sealed 클래스는 각각의 인스턴스로 존재 가능
// 또한 상속을 통해 다양한 확장 가능
// sealed 수정자로 선언
// 접근 제한자는 protected(기본값)와 private만 가능
sealed class Sealed

sealed interface SealedIntf

// sealed 클래스의 서브 클래스는 public을 기본 접근 제한자로 가지며 변경 가능
// 상태값이 바뀌지 않는 sealed 클래스의 서브 클래스는 object 권장
object DerivedSealed : Sealed()
object DerivedSealed2 : Sealed()

// sealed 클래스의 서브 클래스는 익명 혹은 지역 객체로는 생성 불가능
// enum 클래스는 sealed 인터페이스 구현 가능

// when식에 사용할때 유용(안정성)
fun sealedWhenTest(s: Sealed) {
    when (s) {
        is DerivedSealed -> println("s는 DerivedSealed입니다.")
        is DerivedSealed2 -> println("s는 DerivedSealed2입니다.")
    }
}