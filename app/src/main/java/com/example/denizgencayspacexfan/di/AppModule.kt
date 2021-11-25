package com.example.denizgencayspacexfan.di

import com.example.denizgencayspacexfan.network.ApiService
import com.example.denizgencayspacexfan.util.Utils.Companion.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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

    //Using singleton design pattern with help of Hilt
    //Creating retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //Creating Api service instance
    @Singleton
    @Provides
    fun provideRocketService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    //Creating firebase authentication instance
    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    //Creating firebase firestore instance
    @Provides
    @Singleton
    fun provideFirestore()= FirebaseFirestore.getInstance()

}