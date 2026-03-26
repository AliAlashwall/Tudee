package com.example.tudee.di.modules

import com.example.tudee.data.repository.CategoryRepositoryImpl
import com.example.tudee.data.repository.TaskRepositoryImpl
import com.example.tudee.domain.repository.CategoryRepository
import com.example.tudee.domain.repository.TaskRepository
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
    fun provideTaskRepository(impl: TaskRepositoryImpl): TaskRepository {
        return impl
    }


    @Provides
    @Singleton
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository {
        return impl
    }
}