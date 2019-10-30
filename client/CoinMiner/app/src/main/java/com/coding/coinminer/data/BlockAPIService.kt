/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface BlockAPIService {

    // Definition: get http request to [/work]
    @GET("work")
    fun getBlockHeader(): Observable<Model.Block>



    // Companion object to create the BLOCKAPI service

    companion object Factory {

        // Generates retrofit service
        fun create(): BlockAPIService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:3000/")
                .build()

            return retrofit.create(BlockAPIService::class.java)
        }
    }

}