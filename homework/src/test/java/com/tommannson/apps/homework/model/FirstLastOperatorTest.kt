package com.tommannson.apps.homework.model

import com.tommannson.apps.homework.utils.firstAndLast
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class FirstLastOperatorTest {

    @Test
    fun testOperator() {

        val testObserver = TestObserver<Int>()

        Observable.just(1, 2, 3)
            .firstAndLast()
            .subscribe(testObserver)


        testObserver.assertResult(1, 3);
    }
}
