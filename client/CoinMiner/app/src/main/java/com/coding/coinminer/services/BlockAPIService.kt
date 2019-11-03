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
import retrofit2.http.POST

interface BlockAPIService {


    // Definition: get new blockheader to mine from /work
    @GET("work")
    fun getBlockHeader(): Observable<Model.Block>

    // TODO Add Authorization Credentials to the header
    // fun getBlockHeader(@Header("Authorization") String credentials): Observable<Model.Model>

    @POST("submit")
    fun postNonce(@Body block: Model.Block): Call<Model.Block>


    // Companion object to create the BLOCK API service
    companion object Factory {

        val BASE_URL = "http://34.217.130.218:3000/"
        // Generates retrofit service
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