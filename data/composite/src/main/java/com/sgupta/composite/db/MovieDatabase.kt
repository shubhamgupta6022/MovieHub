package com.sgupta.composite.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sgupta.composite.db.dao.MovieDao
import com.sgupta.composite.db.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie_db"
    }
}