package com.mills.justin.republicserviceschallenge.di

import com.mills.justin.republicserviceschallenge.data.DriverRepo
import com.mills.justin.republicserviceschallenge.data.DriverRepoImpl
import com.mills.justin.republicserviceschallenge.data.local.DriverLocalDataSource
import com.mills.justin.republicserviceschallenge.data.local.DriverLocalDataSourceImpl
import com.mills.justin.republicserviceschallenge.data.remote.DriverRemoteDataSource
import com.mills.justin.republicserviceschallenge.data.remote.DriverRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindDriverRepo(
        driverRepoImpl: DriverRepoImpl
    ): DriverRepo

    @Binds
    abstract fun bindRemote(
        remoteDataSourceImpl: DriverRemoteDataSourceImpl
    ): DriverRemoteDataSource

    @Binds
    abstract fun bindLocal(
        localDataSourceImpl: DriverLocalDataSourceImpl
    ): DriverLocalDataSource

}

@Module
@InstallIn(ViewModelComponent::class)
object CoroutineModule {

    @Provides
    fun provideExternalCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}
