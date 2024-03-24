package com.ktor.exp.di

import com.ktor.exp.data.repo.MainRepo
import com.ktor.exp.data.repo.MainRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    abstract fun bindMainRepo(impl: MainRepoImpl): MainRepo
}