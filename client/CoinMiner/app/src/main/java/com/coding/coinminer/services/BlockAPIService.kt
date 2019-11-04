/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.services


import com.coding.coinminer.data.Model
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BlockAPIService {


    // Definition: get new blockheader to mine from /work
    @GET("work")
    fun getBlockHeader(): Observable<Model.Block>

    // TODO Add Authorization Credentials to the header
    // Return token add to the submit and work in the future
    //    @POST("login")
    //    fun longIn(@Body login: String): String

    @POST("submit")
    fun postNonce(@Body block: Model.Block): Call<Model.Block>
    // TODO  @Header("Authorization") authHeader: String


    // Companion object to create the BLOCK API service
    companion object Factory {

        val BASE_URL = "http://54.191.76.81:3000/"
        // Generate retrofit service
        fun create(): BlockAPIService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(BlockAPIService::class.java)
        }
    }

}
