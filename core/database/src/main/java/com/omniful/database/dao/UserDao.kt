package com.omniful.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omniful.database.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity):Long

    @Query("SELECT username FROM user_table WHERE id = :userId ")
    suspend fun getUserName(userId: Int): String?
}