/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET
import java.net.URL

class BlockRepository(val apiService: BlockAPIService) {


    // send get http request and return live data

    // TODO: put data into ROOM
    //https://developer.android.com/jetpack/docs/guide
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

}