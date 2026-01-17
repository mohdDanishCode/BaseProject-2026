package com.omniful.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omniful.database.dao.MovieDao
import com.omniful.database.dao.MovieRemoteKeysDao
import com.omniful.database.dao.NoteDao
import com.omniful.database.dao.UserDao
import com.omniful.database.model.MovieEntity
import com.omniful.database.model.MovieRemoteKeys
import com.omniful.database.model.NoteEntity
import com.omniful.database.model.UserEntity


@Database(entities = [UserEntity::class, NoteEntity::class, MovieEntity::class, MovieRemoteKeys::class], version = 1, exportSchema = false)
abstract class OmnifulDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun getNoteDao(): NoteDao

    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieRemoteKeysDao(): MovieRemoteKeysDao


}