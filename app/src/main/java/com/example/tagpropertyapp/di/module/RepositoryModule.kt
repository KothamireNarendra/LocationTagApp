package com.example.tagpropertyapp.di.module

import com.example.tagpropertyapp.data.local.repository.PropertyRepository
import com.example.tagpropertyapp.domain.repository.IPropertyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesPropertyRepository(
        propertyRepository: PropertyRepository
    ): IPropertyRepository

}