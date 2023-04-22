package com.mills.justin.republicserviceschallenge.di

import android.content.Context
import androidx.room.Room
import com.mills.justin.republicserviceschallenge.data.DriverRepo
import com.mills.justin.republicserviceschallenge.data.DriverRepoImpl
import com.mills.justin.republicserviceschallenge.data.local.DriverDatabase
import com.mills.justin.republicserviceschallenge.data.local.DriverLocalDataSource
import com.mills.justin.republicserviceschallenge.data.local.DriverLocalDataSourceImpl
import com.mills.justin.republicserviceschallenge.data.remote.DriverRemoteDataSource
import com.mills.justin.republicserviceschallenge.data.remote.DriverRemoteDataSourceImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient

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
object MainModule {

    @Provides
    fun provideExternalCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun provideDriverDatabase(
        @ApplicationContext context: Context,
    ): DriverDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = DriverDatabase::class.java,
            name = "driver.db",
        ).build()
    }

    @Provides
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}