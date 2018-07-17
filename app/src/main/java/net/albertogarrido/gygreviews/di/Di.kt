package net.albertogarrido.gygreviews.di

import android.arch.persistence.room.Room
import android.content.Context
import com.google.gson.GsonBuilder
import net.albertogarrido.gygreviews.repository.network.NetworkStatusLiveData
import net.albertogarrido.gygreviews.repository.persistence.ReviewsDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val GYG_URL = "https://www.getyourguide.com/"

fun provideRetrofitClient(): Retrofit =
        Retrofit.Builder()
                .baseUrl(GYG_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(provideGsonConverter())
                .build()

fun provideGsonConverter(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().create())

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

fun getNetworkStatusLiveData(context: Context): NetworkStatusLiveData = NetworkStatusLiveData(context)


object Database {
    private var reviewsDatabase: ReviewsDatabase? = null

    fun provideDatabase(context: Context): ReviewsDatabase? {
        if (reviewsDatabase == null) {
            reviewsDatabase = Room.databaseBuilder(context, ReviewsDatabase::class.java, "reviews-database").build()
        }
        return reviewsDatabase
    }
}
