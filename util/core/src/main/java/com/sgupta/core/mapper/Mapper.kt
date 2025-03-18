package com.sgupta.core.mapper

interface Mapper<F, T> {
    fun convert(from: F): T
}