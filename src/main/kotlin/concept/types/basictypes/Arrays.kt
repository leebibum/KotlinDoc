package concept.types.basictypes

// 기본 사항 자바와 동일

// 팩토리 함수
val arr = arrayOf(1, 2, 3, 4, 5) // [1, 2, 3, 4, 5]

// arrayOfNulls() 함수는 null로 채워진 배열을 생성
// 타입 인자, 인자로 사이즈 필수
val nullArr = arrayOfNulls<Int>(5) // [null, null, null, null, null]

// Array 클래스의 생성자로 인덱스를 초기값으로 사용한 배열 혹은 하나의 값을 모든 요소로 가지는 배열 생성
// 사이즈 필수
val arr2 = Array(5) { 10 } // [10, 10 ,10 ,10, 10]

// 인덱스를 이용한 초기화 배열 생성
// 사이즈 필수
val indexArr = Array(5) { i -> i } // [1 ,2 ,3 ,4 ,5]
val indexArr2 = Array(5) { i -> i * i } // [1 ,4 ,9 ,16 ,25]

// 원시 자료형 배열
// ByteArray, ShortArray, IntArray 등 원시 자료형 배열 클래스 지원
// 배열 클래스와 상속 관계는 없지만 팩토리 함수, 메소드 및 속성은 동일
val intArr = intArrayOf(1, 2, 3, 4, 5)
val longArr = LongArray(5) { i -> i + 1L }