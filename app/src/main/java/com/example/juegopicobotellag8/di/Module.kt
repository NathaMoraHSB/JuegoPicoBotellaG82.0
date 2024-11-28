package com.example.juegopicobotellag8.di

import com.example.juegopicobotellag8.api.ApiService
import com.example.juegopicobotellag8.repository.RetosRepository
import com.example.juegopicobotellag8.repository.LoginRepository
import com.example.juegopicobotellag8.utils.Constants.BASE_URL
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideRetosRepository(firestore: FirebaseFirestore): RetosRepository {
        return RetosRepository(firestore)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository {
        return LoginRepository(firebaseAuth)
    }

}