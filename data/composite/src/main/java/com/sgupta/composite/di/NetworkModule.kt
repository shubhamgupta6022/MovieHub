package com.sgupta.composite.di

import com.sgupta.composite.api.MovieHubAPIService
import com.sgupta.network.client.NetworkClient
import com.sgupta.network.client.NetworkHost
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieHubAPIService(networkClient: NetworkClient): MovieHubAPIService {
        return networkClient.getService(NetworkHost.SERVER_BASE, MovieHubAPIService::class.java)
    }
}