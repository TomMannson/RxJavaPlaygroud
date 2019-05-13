package com.tommannson.apps.homework.model

import io.reactivex.observers.TestObserver
import org.junit.Test

class BankTest {

    @Test
    fun testBankingSystem() {

        val bank = Bank()
        bank.setup()
        val subscriber = TestObserver<Long>()

        bank.assignAccounts(0, arrayOf(0, 4, 6, 10))
        bank.assignAccounts(1, arrayOf(1, 40, 60, 99))
        bank.hashCode()

        bank.getFullBillingForAccount(bank.listOfProfiles[1].uuid)
            .subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValue { it == 200L }
        //beacause for profile 1 we have 0 billing for first and forth account
        //in other hand we have 2 times 100 for second and third account

    }


}