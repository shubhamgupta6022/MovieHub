package com.sgupta.network.client

import com.google.gson.Gson
import com.sgupta.network.builder.NetworkClientBuilder
import com.sgupta.network.mapper.NetworkConfigMapper
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper class for getting services and other utilities related to network.
 */
@Singleton
class NetworkClient
@Inject
constructor(
    private val networkConfigMapper: NetworkConfigMapper,
) {
    /**
     * Getting the service dynamically
     * @param host accepts [NetworkHost] for url mapping
     * @param service accepts retrofit service
     * @param gson optional - accepts custom gson
     */
    fun <T> getService(
        host: NetworkHost,
        service: Class<T>,
        gson: Gson? = null,
    ): T {
        val networkConfig = networkConfigMapper.convert(host)
        val url = networkConfig.url
        val interceptor = networkConfig.interceptor
        if (url.isEmpty()) {
            throw IllegalStateException("Url can't be empty")
        }
        val networkClientBuilder =
            NetworkClientBuilder().apply {
                addConverterFactory(GsonConverterFactory.create(gson ?: Gson()))
            }
        interceptor?.let {
            networkClientBuilder.addInterceptor(it)
        }
        return networkClientBuilder.build(url).create(service)
    }
}