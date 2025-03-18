package com.sgupta.network.mapper

import com.sgupta.core.mapper.Mapper
import com.sgupta.network.client.NetworkConfig
import com.sgupta.network.client.NetworkHost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfigMapper @Inject constructor(
    private val interceptorMapper: InterceptorMapper,
    private val hostConfigurationMapper: HostConfigurationMapper
) : Mapper<NetworkHost, NetworkConfig> {
    override fun convert(from: NetworkHost): NetworkConfig {
        val interceptor = interceptorMapper.convert(from)
        val host = hostConfigurationMapper.convert(from)
        return NetworkConfig(host, interceptor)
    }
}