package com.omniful.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omniful.database.dao.NoteDao
import com.omniful.database.dao.UserDao
import com.omniful.database.model.NoteEntity
import com.omniful.database.model.UserEntity


@Database(entities = [UserEntity::class, NoteEntity::class], version = 2, exportSchema = false)
abstract class OmnifulDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun getNoteDao(): NoteDao

}