package com.sgupta.composite.di

import com.sgupta.composite.repoimpl.MovieHubRepoImpl
import com.sgupta.domain.repo.MovieHubRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindMovieHubRepo(impl: MovieHubRepoImpl): MovieHubRepo

}