package com.example.loweschallenge.di

import com.example.loweschallenge.data.repo.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    // DI call for repository
    fun providesDataRepository() = DataRepositoryImpl()

}