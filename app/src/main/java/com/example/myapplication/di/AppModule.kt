package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.repository.MainRepositoryImpl
import com.example.myapplication.data.source.local.AppDatabase
import com.example.myapplication.data.source.remote.Constants
import com.example.myapplication.data.source.remote.SimpleApi
import com.example.myapplication.domain.repository.MainRepository
import com.example.myapplication.domain.usecase.AddFavoritePictureInfo
import com.example.myapplication.domain.usecase.GetFavoritePictureInfo
import com.example.myapplication.domain.usecase.GetPictureInfo
import com.example.myapplication.domain.usecase.MainUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideSimpleApi(retrofit: Retrofit): SimpleApi {
        return retrofit.create(SimpleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepositoryDatabase(db: AppDatabase, simpleApi: SimpleApi): MainRepository {
        return MainRepositoryImpl(db.menuDao(), simpleApi)
    }

    @Provides
    @Singleton
    fun provideMenuUseCases(mainRepository: MainRepository): MainUseCases {
        return MainUseCases(
            getPictureInfo = GetPictureInfo(mainRepository),
            addFavoritePictureInfo = AddFavoritePictureInfo(mainRepository),
            getFavoritePictureInfo = GetFavoritePictureInfo(mainRepository)
        )
    }
}