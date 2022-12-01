package io.fingerlabs.ex.app2app.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.fingerlabs.lib.app2app.App2AppComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideApp2AppComponent(
        @ApplicationContext context: Context
    ) = App2AppComponent(context)


}