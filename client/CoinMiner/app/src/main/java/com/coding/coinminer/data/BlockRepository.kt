/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coding.coinminer.utils.log
import com.coding.coinminer.data.Model.Block
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockRepository(val apiService: BlockAPIService) {


    // send get http request and return live data
    // TODO: put data into ROOM
    fun getNewBlockHeader(): LiveData<Model.Block> {
        var block =  MutableLiveData<Model.Block>()
        apiService.getBlockHeader()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                run {
                    block.value = result
                    Log.d(
                        "Result from server",
                        result.toString()
                    )

                }

            }, { error ->
                Log.d("Error", error.message)
            })
        return block
    }


    fun postNonce(pre: Block, nonceFound: Long) {

        log("nonceFound " + nonceFound)
        pre.blockHeader.Nonce = nonceFound
        apiService.postNonce(pre)
            .enqueue(object: Callback<Block>{
                val LOG_TAG = "POST"

                override fun onFailure(call: Call<Block>, t: Throwable) {
                    Log.d( LOG_TAG,"No Success")
                }

                override fun onResponse(call: Call<Block>, response: Response<Block>) {
                    if (!response.isSuccessful()) {
                        Log.d( LOG_TAG,"No Success")
                    }
                    Log.d( LOG_TAG, "Something again from server " +   response.body().toString()!!)

                }



            })
    }

}