/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coding.coinminer.data.Model
import com.coding.coinminer.utils.log
import com.coding.coinminer.data.Model.Block
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockRepository(val apiService: BlockAPIService) {


    // send get http request and return live data
    // TODO: put data into ROOM
    fun getBlockHeader(): Observable<Block> {
        //TODO  An authorization header
//        if (Model.token.equals("")) {
//            Log.d("login", "new miner")
//            return apiService.getBlockHeader("NEWMINER")
//        } else {
//            Log.d("login", "token")
//            return apiService.getBlockHeader(Model.token)
//        }

        return apiService.getBlockHeader()


    }

    fun postNonce(pre: Block, nonceFound: Long): Boolean {

        pre.blockHeader.Nonce = nonceFound

        var success = CompletableDeferred<Boolean>()
        apiService.postNonce(pre)
            .enqueue(object : Callback<Block> {
                val LOG_TAG = "POST"

                override fun onFailure(call: Call<Block>, t: Throwable) {
                    success.complete(false)

                }

                override fun onResponse(call: Call<Block>, response: Response<Block>) {
                    if (!response.isSuccessful()) {
                        Log.d(LOG_TAG, "No Success")
                    }
                    success.complete(true)
                    Log.d(LOG_TAG, "Something again from server " + response.body().toString())
                }

            })
        return success.getCompleted()
    }

}
