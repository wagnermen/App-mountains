package com.example.data.remote.di

import android.content.Context
import com.example.data.BuildConfig
import com.example.data.remote.api.ApiTouristService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import local.AppDatabase
import local.MountainsDao
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val apiKey = "68133b3087msh049b031ad460b62p197a89jsn7c3fd13e03c8"

@Module
@InstallIn(SingletonComponent::class)
object ApiManager {

    @Singleton
    @Provides
    fun provideMountainsApi(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(apiKey))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): ApiTouristService {
        return retrofit.create(ApiTouristService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideMountainsDao(database: AppDatabase): MountainsDao {
        return database.mountainsDao()
    }

}