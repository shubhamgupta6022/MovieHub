package com.sgupta.core.usecase

import kotlinx.coroutines.flow.Flow

abstract class QueryUseCase<T, R> {
    fun execute(param: T): Flow<R> = start(param)

    protected abstract fun start(param: T): Flow<R>
}