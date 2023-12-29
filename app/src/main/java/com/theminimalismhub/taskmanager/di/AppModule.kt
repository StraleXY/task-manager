package com.theminimalismhub.taskmanager.di

import android.app.Application
import android.content.Context
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun providesContext(@ApplicationContext app: Application) : Context = app.applicationContext
    @Provides @Singleton fun providesNavigationController() : NavigationController = NavigationController.getInstance()

}