package com.hfad.allweather.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hfad.allweather.common.Constants
import com.hfad.allweather.data.location.DefaultLocationTracker
import com.hfad.allweather.data.remote.WeatherAPI
import com.hfad.allweather.data.repository.WeatherRepositoryImpl
import com.hfad.allweather.domain.location.LocationTracker
import com.hfad.allweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiWeather(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesSongRepository(api: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker {
        return defaultLocationTracker
    }

    @Provides
    @Singleton
    fun provideFusedlocationClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }


}
