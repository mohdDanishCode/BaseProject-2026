package com.omniful.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omniful.database.dao.MovieDao
import com.omniful.database.dao.MovieRemoteKeysDao
import com.omniful.database.model.MovieEntity
import com.omniful.database.model.MovieRemoteKeys


@Database(entities = [ MovieEntity::class, MovieRemoteKeys::class], version = 1, exportSchema = false)
abstract class OmnifulDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieRemoteKeysDao(): MovieRemoteKeysDao


}