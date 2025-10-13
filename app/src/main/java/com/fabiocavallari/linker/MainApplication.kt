package com.fabiocavallari.linker

import android.app.Application
import com.fabiocavallari.linker.plataform.di.LinkerDependencyInjection.appModules
import com.fabiocavallari.linker.plataform.di.LinkerDependencyInjection.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(networkModules, appModules))
        }
    }
}