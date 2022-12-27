package com.bhaveshp750.composetodo.model.service.module

import com.bhaveshp750.composetodo.model.service.AccountService
import com.bhaveshp750.composetodo.model.service.ConfigurationService
import com.bhaveshp750.composetodo.model.service.LogService
import com.bhaveshp750.composetodo.model.service.StorageService
import com.bhaveshp750.composetodo.model.service.impl.AccountServiceImpl
import com.bhaveshp750.composetodo.model.service.impl.ConfigurationServiceImpl
import com.bhaveshp750.composetodo.model.service.impl.LogServiceImpl
import com.bhaveshp750.composetodo.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

  @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

  @Binds
  abstract fun provideConfigurationService(imple: ConfigurationServiceImpl): ConfigurationService
}
