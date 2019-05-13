package com.tommannson.apps.homework.utils

/**
 * Naive implementation of email vaidation
 */
fun CharSequence.isEmail() = this.contains(regex = Regex("[\\w-]+@([\\w-]+\\.)+[\\w-]+"))
