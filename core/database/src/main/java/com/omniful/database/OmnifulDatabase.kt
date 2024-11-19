package com.omniful.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omniful.database.dao.UserDao
import com.omniful.database.model.UserEntity


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class OmnifulDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}