package com.example.translator

import com.example.translator.util.LanguageMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLanguageMapper() : LanguageMapper{
        return LanguageMapper()
    }
}