package concept.controlflows

// return: 가장 가까운 함수 종료, 반환
// break:가장 가까운 반복문 종료
// continue:가장 가까운 반복문으로 건너뛰기

// 아래 모든 예시는 continue도 동일

// 코틀린의 모든 표현식은 라벨 표시 가능
// 라벨은 label@과 같이 표시
// 라벨을 표시하려면 표현식 앞에 라벨 표시
fun label() {
    loop@ for (i in 0..10) println(i)
}

fun nonLabelForLoops() {
    // 아래 for문은 밖 for문이 종료되지 않음
    for (i in 1..100) {
        println("i = $i")
        for (j in 1..100) {
            println("j = $j")
            if (j == 5) break // 내부 for문만 break
        }
    }
}

// 라벨을 사용하여 특정 표현식 제어 가능
fun labelForLoops() {
    outer@ for (i in 1..100) {
        println("i = $i")
        for (j in 1..100) {
            println("j = $j")
            if (j == 5) break@outer
        }
    }
}
// continue도 동일

// 내부 함수의 return으로 인해 외부 함수가 종료되는 경우를 비지역 반환이라고 함
fun nonLocalReturn() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // 함수 전체 종료
        println(it)
    }
    println("실행되지 않는 구문")
}

// 라벨을 사용하여 비지역 반환 해결 가능
fun returnLabel() {
    listOf(1, 2, 3, 4, 5).forEach function@{
        if (it == 3) return@function // 라벨이 표시된 함수 종료
        println(it)
    }
    println("실행되는 구문")
}
// 헷갈릴 수 있는 점
// forEach 함수는 람다식을 다섯번 실행함, 때문에 forEach가 종료되는 게 아닌 it이 3일때 람다식 하나를 종료

// 람다를 호출하는 함수의 이름으로 묵시적 라벨 사용 가능
fun implicitLabel() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach
        println(it)
    }
    println("실행되는 구문")
}

// 익명 함수를 생성하여 비지역 반환 해결 가능
// 익명 함수의 return은 익명 함수만을 종료
fun localReturnByAnonymousFunc() {
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) { // 익명 함수
        if (value == 3) return
        println(value)
    })
    println("실행되는 구문")
}
