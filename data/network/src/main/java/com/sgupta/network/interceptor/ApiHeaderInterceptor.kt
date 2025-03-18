package com.sgupta.network.interceptor

import com.sgupta.network.header.HeaderMap
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton

/**
 *  Create interceptor with dynamic headers.
 *  @param [HeaderMap] create interceptor dynamically.
 */
@Singleton
class ApiHeaderInterceptor(
    private val header: HeaderMap = HeaderMap(),
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkZTYxOWYzMTQzYWM3ZGYzZjNlYjY3MWQxZjBmZDVhZCIsIm5iZiI6MTc0MjMxNDY4Mi44OTc5OTk4LCJzdWIiOiI2N2Q5OWNiYTFiYjRiNWM1OGJjNmJiMTYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.1Xo73ayU5n7VqO1oAIXe-MTVG45clgCdc9FRp773qgM"
        val builder =
            original.newBuilder().apply {
                header.addHeaders(original.headers())
                header.addHeader("Authorization", authToken)
                header.addHeader("accept", "application/json")
                headers(header.getHeaders())
            }
        return chain.proceed(builder.build())
    }
}