/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data
import android.util.Log
import java.net.URL

class Request(private val url: String){

    fun run(){
        val json = URL(url).readText();
        println("request " + json)
        Log.d(javaClass.simpleName, json);
    }
}