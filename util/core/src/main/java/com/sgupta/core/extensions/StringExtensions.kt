package com.sgupta.core.extensions

fun String?.toThrowable() = Throwable(this)