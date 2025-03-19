package com.sgupta.composite.di

import android.content.Context
import androidx.room.Room
import com.sgupta.composite.db.MovieDatabase
import com.sgupta.composite.db.MovieDatabase.Companion.DATABASE_NAME
import com.sgupta.composite.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(
        database: MovieDatabase
    ): MovieDao = database.movieDao()
}