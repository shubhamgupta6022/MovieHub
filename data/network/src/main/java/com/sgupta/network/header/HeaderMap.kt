package com.sgupta.network.header

import com.sgupta.network.constants.NetworkHeaders
import okhttp3.Headers

/**
 * Util class for generating header maps for header configs
 */
class HeaderMap {
    private val defaultHeaderMap: HashMap<String, String> = HashMap()

    /**
     * Getter for getting headers from header map.
     * Usually a mapper which converts [HeaderMap] to [Headers]
     */
    fun getHeaders(): Headers = Headers.of(defaultHeaderMap)

    /**
     * Remove headers from [HeaderMap]
     */
    fun removeHeader(key: String) = defaultHeaderMap.remove(key)

    /**
     * Add Header to default map
     * @param key as [NetworkHeaders] expects a [NetworkHeaders] enum
     * @param value as [String]
     */
    fun addHeader(
        key: NetworkHeaders,
        value: String,
    ) {
        defaultHeaderMap[key.value] = value
    }

    /**
     * Add Header to default map
     * @param key as [String]
     * @param value as [String]
     */
    fun addHeader(
        key: String,
        value: String,
    ) {
        if (key.isNotEmpty()) defaultHeaderMap[key] = value
    }

    /**
     * Mapper for adding [HashMap]<String,String> and adds into the [HeaderMap]
     */
    fun addHeaders(hashMap: HashMap<String, String>) {
        for ((key, value) in hashMap) {
            addHeader(key, value)
        }
    }

    /**
     * Mapper for adding [Headers] and adds into the [HeaderMap]
     */
    fun addHeaders(headers: Headers) {
        headers.names().forEach { key ->
            headers.get(key)?.let {
                defaultHeaderMap[key] = it
            }
        }
    }
}