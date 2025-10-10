package com.fabiocavallari.linker.plataform.di

import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object LinkerDependencyInjection {

//    val networkModules = module {
//        single<OkHttpClient> {
//            OkHttpClient.Builder()
//                .dispatcher(Dispatcher().apply {
//                    maxRequests = 1
//                    maxRequestsPerHost = 1
//                })
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS)
//                .addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                })
//                .build()
//        }
//        single<TrackApiClient> {
//            Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://10.0.2.2:5000/")
//                .client(get())
//                .build()
//                .create(TrackApiClient::class.java)
//        }
//    }

    val appModules = module {
//        singleOf(::TrackApiRemoteProviderImpl) { bind<TrackApiRemoteProvider>() }
//
//        singleOf(::DataTrackRepositoryImpl) { bind<DataTrackRepository>() }
//
//        factoryOf(::SaveHitUseCase) { bind<SaveHitUseCase>() }
//        factory<SendBatchHitsUseCase> { SendBatchHitsUseCase(BuildConfig.HIT_BATCH_THRESHOLD, get()) }
//        factory<TrackHitsUseCase> { TrackHitsUseCase(BuildConfig.WORK_MANAGER_ENABLED, get(), get()) }
//
        viewModelOf(::HomeScreenViewModel)
    }
}