package io.fingerlabs.ex.app2app.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.fingerlabs.ex.app2app.data.repository.App2AppRepository
import io.fingerlabs.ex.app2app.data.source.api.App2AppApi


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideApp2AppRepository(
           app2appApi: App2AppApi
    ): App2AppRepository = App2AppRepository(app2appApi)
}