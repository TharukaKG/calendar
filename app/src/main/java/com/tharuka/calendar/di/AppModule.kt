package com.tharuka.calendar.di

import com.tharuka.calendar.data.local.calendar_provider.CalenderProvider
import com.tharuka.calendar.data.repository.CalendarRepositoryImpl
import com.tharuka.calendar.domain.repository.CalendarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCalendarRepository(
        calenderProvider: CalenderProvider
    ): CalendarRepository{
        return CalendarRepositoryImpl(calenderProvider)
    }

    @Provides
    @Singleton
    fun providesCalendarProvider(
    ): CalenderProvider{
        return CalenderProvider()
    }

}