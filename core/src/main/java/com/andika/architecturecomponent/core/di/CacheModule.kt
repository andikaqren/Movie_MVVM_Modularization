package com.andika.architecturecomponent.core.di

import android.content.Context
import androidx.room.Room
import com.andika.architecturecomponent.business.data.local.LocalDao
import com.andika.architecturecomponent.business.data.local.LocalDataSource
import com.andika.architecturecomponent.business.data.local.LocalDataSourceImpl
import com.andika.architecturecomponent.business.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Singleton
    @Provides
    fun providesDB(@ApplicationContext context: Context): LocalDatabase {
        return Room
            .databaseBuilder(
                context,
                LocalDatabase::class.java,
                LocalDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDBDao(localDatabase: LocalDatabase): LocalDao {
        return localDatabase.localDB()
    }

    @Singleton
    @Provides
    fun providesDBService(sampinganDao: LocalDao): LocalDataSource {
        return LocalDataSourceImpl(sampinganDao)
    }

}