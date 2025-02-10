package com.michalm.movies.di

import android.app.Application
import com.michalm.movies.data.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModuleModule {

    @Provides
    fun provideSharedPreferencesHelper(application: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }
}