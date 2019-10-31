/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates

import android.util.Log
import com.coding.coinminer.utils.HashUtil
import com.coding.coinminer.data.Model
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

class Miner {
    // Calculating the hashing work

    private var version: Long = 0
    private var prevBlockhash: String = ""
    private var merkleRoot: String = ""
    private var timestamp: String = ""
    private var difficulty: Int = 0
    private var Nonce: AtomicInteger = AtomicInteger()


    var b = Model.MiningData

        companion object {

        fun run(counter:AtomicInteger) {

            Log.d("one thread miner", "one miner")
            Log.d("one thread miner Nonce", counter.toString())

        }

    }

    // Using suspend function to get the calculation

    // Returns the hash result string after sha256
    suspend fun calculateHash(s: String):String{

        return HashUtil.sha256(HashUtil.sha256(s))
    }


    // Returns the hash content based on content and nonce

    suspend fun calculateContentToBeHashed(nonce:Long): String{
        // TODO produce the new content
//        	record := string(block.Index) + block.Timestamp + string(block.BPM) + block.PrevHash
//        return block.toString() + nonce + ""
        return ""
    }


    // Returns the hash content based on content and nonce
    suspend fun isHashValid(hash: String): Boolean {
        var prefix = "0".repeat(this.difficulty)
        return hash.startsWith(prefix)
    }


}
