package com.hopeinyang.preg_health.data.service.module

import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.data.service.impl.AccountServiceImpl
import com.hopeinyang.preg_health.data.service.impl.LogServiceImpl
import com.hopeinyang.preg_health.data.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService



    //@Binds abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService

}