package com.fabiocavallari.linker.plataform.di

import com.fabiocavallari.linker.data.client.UrlShortenerClient
import com.fabiocavallari.linker.data.remoteprovider.UrlShortenerRemoteProvider
import com.fabiocavallari.linker.data.remoteprovider.UrlShortenerRemoteProviderImpl
import com.fabiocavallari.linker.data.repository.AliasRepositoryImpl
import com.fabiocavallari.linker.data.util.UrlValidatorImpl
import com.fabiocavallari.linker.domain.repository.AliasRepository
import com.fabiocavallari.linker.domain.usecase.CreateAliasUseCase
import com.fabiocavallari.linker.domain.util.UrlValidator
import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LinkerDependencyInjection {
    val networkModules = module {
        single<OkHttpClient> {
            OkHttpClient.Builder()
                .dispatcher(Dispatcher().apply {
                    maxRequests = 1
                    maxRequestsPerHost = 1
                })
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
        single<UrlShortenerClient> {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://url-shortener-server.onrender.com/api/")
                .client(get())
                .build()
                .create(UrlShortenerClient::class.java)
        }
    }

    val appModules = module {
        singleOf(::UrlShortenerRemoteProviderImpl) { bind<UrlShortenerRemoteProvider>() }
        singleOf(::AliasRepositoryImpl) { bind<AliasRepository>() }
        singleOf(::UrlValidatorImpl) { bind<UrlValidator>() }
        factoryOf(::CreateAliasUseCase)
        viewModelOf(::HomeScreenViewModel)
    }
}