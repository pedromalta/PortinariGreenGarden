package br.com.drivin.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PortinariApis {

    fun getMiddleware(client: OkHttpClient, apiUrl: String): IPortinariApi {
        val retrofitCore = Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(configDateConverter())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofitCore.create(IPortinariApi::class.java)
    }



    private fun configDateConverter(): GsonConverterFactory{
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        return GsonConverterFactory.create(gson)
    }
}