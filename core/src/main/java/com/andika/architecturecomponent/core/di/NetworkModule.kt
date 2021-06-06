package com.andika.architecturecomponent.core.di


import com.andika.architecturecomponent.core.BuildConfig
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSourceImpl
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.baseDomain
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.deepLinkDomain
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.posterDomain
import com.andika.architecturecomponent.core.business.network.NetworkManager
import com.andika.architecturecomponent.core.business.network.NetworkManagerService
import com.andika.architecturecomponent.core.business.network.NetworkManagerServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(baseDomain, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(deepLinkDomain, "sha256/P4c/fova2loNyah+FHGVbPpPRirbcJrvPFwrIcauQWk=")
            .add(posterDomain, "sha256/dkkdrQXG/soxr05PnqVNAas2Cl7nDyOp2iRkEHQ+wk0=")
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttp3LoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        interceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .certificatePinner(certificatePinner)
            .build()

    }

    @Singleton
    @Provides
    fun getRetrofit(client: OkHttpClient, gson: Gson): NetworkManager {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(NetworkManager::class.java)
    }

    @Singleton
    @Provides
    fun getNetworkManagerService(networkManager: NetworkManager): NetworkManagerService {
        return NetworkManagerServiceImpl(networkManager)
    }

    @Singleton
    @Provides
    fun getRemoteDataSource(networkManagerService: NetworkManagerService): RemoteDataSource {
        return RemoteDataSourceImpl(networkManagerService)
    }


}