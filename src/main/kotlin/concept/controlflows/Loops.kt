package concept.controlflows

//while은 자바와 동일

// 컬렉션 순환
fun collectionLoop(arr: Array<*>) {
    for (item in arr) println(item)
}

// 특정 범위 반복에는 범위식 사용
fun rangeLoop() {
    for (num in 0..10) println(num) // 처음 값 마지막 값 포함

    for (num in 10 downTo 0 step 2) println(num) // downTo로 큰 수에서 작은 수로 감소 가능, step으로 증가값 감소값 지정 가능
}

// 인덱스가 있는 자료구조 순환
fun collectionWithIndexLoop(arr: Array<*>) {
    // indices는 인덱스의 범위를 반환
    for (index in arr.indices) println(arr[index])

    // withIndex()를 사용하여 인덱스와 요소 둘 다 참조 가능
    for ((index, item) in arr.withIndex()) println("{$index}번째 아이템: $item")
}