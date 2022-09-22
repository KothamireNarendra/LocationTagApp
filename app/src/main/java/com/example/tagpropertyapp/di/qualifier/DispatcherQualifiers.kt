package com.example.tagpropertyapp.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher