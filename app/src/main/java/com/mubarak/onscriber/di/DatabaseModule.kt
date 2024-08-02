package com.mubarak.onscriber.di

import android.content.Context
import androidx.room.Room
import com.mubarak.onscriber.data.sources.OsbRepository
import com.mubarak.onscriber.data.sources.DefaultOsbRepository
import com.mubarak.onscriber.data.sources.local.OsbDatabase
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): OsbDatabase {
        return Room.databaseBuilder(
            context,
            OsbDatabase::class.java,
            "noteDB"
        ).build()
    }

    @Singleton
    @Provides
    fun getRepositoryImpl(noteDatabase: OsbDatabase): OsbRepository {
        return DefaultOsbRepository(noteDatabase)
    }
}