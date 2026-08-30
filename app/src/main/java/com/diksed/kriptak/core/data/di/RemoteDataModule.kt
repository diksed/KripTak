package com.diksed.kriptak.core.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.diksed.kriptak.core.data.remote.api.CoinGeckoApiService
import com.diksed.kriptak.core.data.repository.CoinIdRepository
import com.diksed.kriptak.core.data.repository.CoinIdRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoinGeckoApiService(
        @ApplicationContext context: Context
    ): CoinGeckoApiService {
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(context.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CoinGeckoApiService.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(CoinGeckoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideCoinIdRepository(sharedPreferences: SharedPreferences): CoinIdRepository {
        return CoinIdRepositoryImpl(sharedPreferences)
    }
}
