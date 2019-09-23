package com.shopifychallenge.cardgame.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import kotlinx.coroutines.Deferred
import retrofit2.http.Query

private const val BASE_URL = "https://shopicruit.myshopify.com/"


private val moshi = Moshi.Builder()
        .add(NULL_TO_EMPTY_STRING_ADAPTER)
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


interface GameApiService {

    @GET("admin/products.json")
    fun getProducts(@Query("page") page: Int,
                      @Query("access_token") accessToken: String):
            Deferred<Products>
}


object RetrofitApi {
    val RETROFIT_SERVICE : GameApiService by lazy { retrofit.create(GameApiService::class.java) }
}


object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}