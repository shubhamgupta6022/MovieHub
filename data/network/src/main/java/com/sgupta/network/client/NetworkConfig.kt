package com.sgupta.network.client

import okhttp3.Interceptor

data class NetworkConfig(val url: String, val interceptor: Interceptor?)
