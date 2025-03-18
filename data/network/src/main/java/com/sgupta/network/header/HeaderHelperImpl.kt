package com.sgupta.network.header

import javax.inject.Inject

class HeaderHelperImpl @Inject constructor() : HeaderHelper {
    override fun getCommonHeaders(): HeaderMap {
        return HeaderMap()
    }
}