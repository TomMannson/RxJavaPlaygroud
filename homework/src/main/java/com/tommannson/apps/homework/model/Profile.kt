package com.tommannson.apps.homework.model

import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class Bank {

    val listOfProfiles = mutableListOf<Profile>()
    val listOfAccounts = mutableListOf<Account>()

    fun setup() {
        for (index in 0..100) {
            listOfProfiles.add(Profile(UUID.randomUUID()))
            listOfAccounts.add(Account(UUID.randomUUID(), (index.rem(2) == 0), 100))
        }
    }

    fun assignAccounts(indexOfProfile: Int, indexes: Array<Int>) {
        val profile = listOfProfiles[indexOfProfile]
        for (index in indexes) {
            val account = listOfAccounts[index]
            profile.accountUuids.add(account.uuid)
        }
    }


    /**
     * 1. Create a function that accepts profile id UUID and returns an Observable<UUID> for allthe account UUIDS
     * associated with that profile
     */
    fun getObservableForProfileUUID(profileUuid: UUID): Observable<UUID> {
        return Observable.fromIterable(listOfProfiles)
            .filter { it.uuid == profileUuid }
            .flatMap { Observable.fromIterable(it.accountUuids) }
    }

    /**
     * 2. Now create function that accepts profile id UUID and then returns an Observable<Account> for all accounts
     * associated with that profile
     */
    fun getObservableAccountForProfileUUID(profileUuid: UUID): Observable<Account> {
        return getObservableForProfileUUID(profileUuid)
            .flatMap {
                val accountUUID = it
                Observable.fromIterable(listOfAccounts)
                    .filter { it.uuid == accountUUID }
            }
    }

    /**
     * 3. Now create function that accepts profile id UUID and then returns an Observable<Account> that only emits
     *  checking account information (use operators to remove non checking accounts)
     */
    fun getObservableAccountCheckingForProfileUUID(profileUuid: UUID): Observable<Account> {
        return getObservableAccountForProfileUUID(profileUuid)
            .filter { it.checkingAccount }
    }

    /**
     * A. Using the function from step 2 emit all account details except for checking account
     */
    fun getObservableAccountUncheckingForProfileUUID(profileUuid: UUID): Observable<Account> {
        return getObservableAccountForProfileUUID(profileUuid)
            .filter { !it.checkingAccount }
    }

    /**
     * 4. Create a function that accepts a profile UUIDs and then returns an Single<Long> that only emits
     *  the total amount for all checking accounts associated with that UUID
     */
    fun getFullBillingForAccount(profileUuid: UUID): Single<Long> {
        return getObservableAccountCheckingForProfileUUID(profileUuid)
            .map { it.bilance }
            .reduce(0L, { first, next -> first + next })
    }

    //point three
    //Using function from point step 2 all account
//    fun getObservableAccountCheckingForProfileUUID(profileUuid: UUID): Observable<Account> {
//        return getObservableAccountForProfileUUID(profileUuid)
//            .filter { it.checkingAccount }
//    }


}

data class Profile(val uuid: UUID, val accountUuids: MutableList<UUID> = mutableListOf())

data class Account(val uuid: UUID, val checkingAccount: Boolean, val bilance: Long)
