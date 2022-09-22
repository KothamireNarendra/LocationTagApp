package com.example.tagpropertyapp.common

interface IMapper<F, T> {
    fun transform(model: F): T
    fun transform(models: List<F>): List<T>{
        return models.map { transform(it) }
    }
}