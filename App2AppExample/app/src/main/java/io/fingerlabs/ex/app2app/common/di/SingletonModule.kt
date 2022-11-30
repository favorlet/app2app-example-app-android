package io.fingerlabs.ex.app2app.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fingerlabs.lib.app2app.App2AppComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    private const val TIME_OUT = 10000L


    @Provides
    @Singleton
    fun provideApp2AppComponent() = App2AppComponent()


}