package com.myapp.musicplayerapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.myapp.musicplayerapp.di.musicAppNetworkModule
import com.myapp.musicplayerapp.di.musicAppRepositoryModule
import com.myapp.musicplayerapp.di.musicAppViewModelModule
import com.myapp.musicplayerapp.di.musicPlayerMapperModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidContext(this@BaseApplication)
            modules(
                musicAppViewModelModule(),
                musicPlayerMapperModule(),
                musicAppRepositoryModule(),
                musicAppNetworkModule()
            )

        }
    }
}