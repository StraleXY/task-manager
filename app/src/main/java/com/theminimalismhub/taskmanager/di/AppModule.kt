package com.theminimalismhub.taskmanager.di

import android.app.Application
import android.content.Context
import com.theminimalismhub.taskmanager.core.local_storage.LocalPref
import com.theminimalismhub.taskmanager.core.local_storage.LocalStorage
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.feature_calendar.domain.use_cases.CalendarPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun providesContext(app: Application) : Context = app.applicationContext
    @Provides @Singleton fun providesNavigationController() : NavigationController = NavigationController.getInstance()
    @Provides @Singleton fun providesCalendarPrefs(context: Context) : CalendarPref = LocalPref(context)

}