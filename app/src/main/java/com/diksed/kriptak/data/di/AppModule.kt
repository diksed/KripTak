package com.diksed.kriptak.data.di

import android.content.Context
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.remote.api.CoinService
import com.diksed.kriptak.data.remote.api.NewsApiService
import com.diksed.kriptak.data.remote.api.TrendingCoinService
import com.diksed.kriptak.domain.repository.CoinRepository
import com.diksed.kriptak.domain.repository.CoinRepositoryImpl
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.repository.NewsRepository
import com.diksed.kriptak.domain.repository.NewsRepositoryImpl
import com.diksed.kriptak.domain.repository.TrendingCoinRepository
import com.diksed.kriptak.domain.repository.TrendingCoinRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTrendingCoinService(): TrendingCoinService {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrendingCoinService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinService(): CoinService {
        return Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(apiService: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(apiService: CoinService): CoinRepository {
        return CoinRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideTrendingCoinRepository(apiService: TrendingCoinService): TrendingCoinRepository {
        return TrendingCoinRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(firestore: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): KripTakApp {
        return app as KripTakApp
    }
}
