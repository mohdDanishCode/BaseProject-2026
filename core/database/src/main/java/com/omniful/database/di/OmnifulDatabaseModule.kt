package com.omniful.database.di

import android.content.Context
import androidx.room.Room
import com.omniful.database.OmnifulDatabase
import com.omniful.database.dao.MovieDao
import com.omniful.database.dao.NoteDao
import com.omniful.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OmnifulDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OmnifulDatabase {
        return Room.databaseBuilder(
            context,
            OmnifulDatabase::class.java,
            "omniful_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: OmnifulDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideNotesDao(database: OmnifulDatabase): NoteDao {
        return database.getNoteDao()
    }

    @Provides
    fun provideMovieDao(database: OmnifulDatabase): MovieDao {
        return database.getMovieDao()
    }
}