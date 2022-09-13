package concept.packagesandimports


// 파일의 모든 내용은 패키지에 포함

fun Func() { // 이 함수의 풀 패키지 네임은 concept.packagesandimports.Func
}

class Class // 이 클래스의 풀 패키지 네임은 concept.packagesandimports.Class

// 다른 패키지와 이름이 겹칠 경우 as를 사용하여 대체명 가능
// concept.packagesandimports.Func as Class 임포트된 패키지의 Class 클래스가 Class로 작동

// import는 최상위 레벨의 함수, 속성, 클래스와 object 클래스의 함수, 속성, enum 상수 등 가져오기 가능

// *를 이용하여 패키지 하위 모든 내용 임포트 가능
// import concept.*