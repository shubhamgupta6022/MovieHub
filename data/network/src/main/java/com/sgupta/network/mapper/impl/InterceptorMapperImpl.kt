package com.sgupta.network.mapper.impl

import com.sgupta.network.client.NetworkHost
import com.sgupta.network.header.HeaderMap
import com.sgupta.network.interceptor.ApiHeaderInterceptor
import com.sgupta.network.mapper.InterceptorMapper
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterceptorMapperImpl @Inject constructor() : InterceptorMapper {
    override fun convert(from: NetworkHost): Interceptor {
        return when (from) {
            NetworkHost.SERVER_BASE -> {
                ApiHeaderInterceptor(HeaderMap())
            }
        }
    }
}