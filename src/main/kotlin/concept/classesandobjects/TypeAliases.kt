package concept.classesandobjects

import java.io.File

// 다른 타입에 대한 대체 이름 지정 가능
// 타입이 너무 긴 경우 혹은 타입에 이름을 붙일때 사용

typealias FileTable = Map<String, MutableList<File>>

// 함수 타입도 가능
typealias MyHandler = (Int, String, Any) -> Unit

// 중첩, 이너 클래스도 가능

class OuterForTypeAlias {
    inner class InnerForTypeAlias
}

typealias MyInner = OuterForTypeAlias.InnerForTypeAlias
