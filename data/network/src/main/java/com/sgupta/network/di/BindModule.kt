package com.sgupta.network.di

import com.sgupta.network.header.HeaderHelper
import com.sgupta.network.header.HeaderHelperImpl
import com.sgupta.network.mapper.HostConfigurationMapper
import com.sgupta.network.mapper.InterceptorMapper
import com.sgupta.network.mapper.impl.HostConfigurationMapperImpl
import com.sgupta.network.mapper.impl.InterceptorMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindHostConfigurationMapper(impl: HostConfigurationMapperImpl): HostConfigurationMapper

    @Binds
    abstract fun bindInterceptorMapper(impl: InterceptorMapperImpl): InterceptorMapper

    @Binds
    abstract fun bindHHeaderHelper(impl: HeaderHelperImpl): HeaderHelper

}