package com.diksed.kriptak.core.data.di

import com.diksed.kriptak.core.util.ConnectivityManagerNetworkMonitor
import com.diksed.kriptak.core.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
