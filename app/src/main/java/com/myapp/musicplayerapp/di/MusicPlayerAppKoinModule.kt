package com.myapp.musicplayerapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.myapp.musicplayerapp.data.mapper.MusicResponseMapper
import com.myapp.musicplayerapp.data.mapper.MusicResponseMapperImpl
import com.myapp.musicplayerapp.data.repository.MusicRepositoryImpl
import com.myapp.musicplayerapp.data.service.SongService
import com.myapp.musicplayerapp.domain.repository.MusicRepository
import com.myapp.musicplayerapp.ui.mapper.MusicMapper
import com.myapp.musicplayerapp.ui.mapper.MusicMapperImpl
import com.myapp.musicplayerapp.ui.viewmodel.MusicViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun injectMusicAppKoinModule() {
    loadKoinModules(
        listOf(
            musicAppViewModelModule(),
            musicPlayerMapperModule(),
            musicAppRepositoryModule(),
            musicAppNetworkModule()
        )
    )
}

fun musicAppNetworkModule() = module {
        single {
            val context: Context = get()

            val chucker = ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250_000L)
                .alwaysReadResponseBody(true)
                .build()

            OkHttpClient.Builder()
                .addInterceptor(chucker)
                .build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single<SongService> {
            get<Retrofit>().create(SongService::class.java)
        }
    }

fun musicAppViewModelModule() = module {
    viewModel { MusicViewModel(get(), get()) }
}

fun musicPlayerMapperModule() = module {
    single<MusicMapper> { MusicMapperImpl() }
    single<MusicResponseMapper> { MusicResponseMapperImpl() }
}

fun musicAppRepositoryModule() = module {
    single<MusicRepository> {
        MusicRepositoryImpl(
            api = get(),
            mapper = get()
        )
    }
}
