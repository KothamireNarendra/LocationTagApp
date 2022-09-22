package com.example.tagpropertyapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.tagpropertyapp.data.local.db.PropertyDao
import com.example.tagpropertyapp.data.local.db.PropertyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesPropertyDatabase(
        @ApplicationContext context: Context
    ): PropertyDatabase {
        return Room.databaseBuilder(
            context,
            PropertyDatabase::class.java,
            "property-database"
        ).build()
    }

    @Provides
    fun providesPropertyDao(propertyDatabase: PropertyDatabase): PropertyDao{
        return propertyDatabase.propertyDao()
    }
}