package com.omniful.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.omniful.common.network.Dispatcher
import com.omniful.common.network.OmnifulDispatchers
import com.omniful.common.network.di.ApplicationScope
import com.omniful.datastore.OmnifulDataStoreSerializer
import com.omniful.datastore.model.UserProfile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideUserProfileDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(OmnifulDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
    ): DataStore<UserProfile> {
        // Define the default value for UserProfile
        val userProfileDefault = UserProfile(name = "")

        return DataStoreFactory.create(
            serializer = OmnifulDataStoreSerializer.createSerializer(userProfileDefault),
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = listOf(
//                IntToStringIdsMigration, // Add any migrations as required
            ),
        ) {
            context.dataStoreFile("user_profile.pb")
        }
    }
}