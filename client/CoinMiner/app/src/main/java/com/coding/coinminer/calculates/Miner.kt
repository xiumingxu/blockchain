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

    constructor() {
//        version = b.version
//        prevBlockhash = b.prevBlockhash
//        merkleRoot = b.merkleRoot
//        difficulty =  b.difficultyTarget
//        Nonce = AtomicInteger(b.Nonce.toInt())
//        timestamp = b.timestamp
    }


//     companion object {
//         fun run() {
//             Log.d("one thread miner", "one miner")
//
//             Log.d("one thread miner Nonce",  Model.MiningData.Nonce.toString())
//             Model.MiningData.Nonce.incrementAndGet()
//         }
//     }

    companion object {
        fun run(counter:AtomicInteger) {
            Log.d("one thread miner", "one miner")

            Log.d("one thread miner Nonce", counter.toString())

        }

    }







    // Returns the hash result string after sha256
    fun calculateHash():String{

        return HashUtil.sha256(HashUtil.sha256(calculateBlockHash()))
    }


    // Returns the hash content based on content and nonce

    fun calculateBlockHash(): String{
        // TODO produce the new content
//        	record := string(block.Index) + block.Timestamp + string(block.BPM) + block.PrevHash
//        return block.toString() + nonce + ""
        return ""
    }


    // Returns the hash content based on content and nonce
    fun isHashValid(hash: String): Boolean {
        var prefix = "0".repeat(this.difficulty)
        return hash.startsWith(prefix)
    }


}


//// Message types for counterActor
//sealed class CounterMsg
//object IncCounter : CounterMsg() // one-way message to increment counter
//class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // a request with reply
//
//
//// This function launches a new counter actor
//fun CoroutineScope.counterActor() = actor<CounterMsg> {
//    var counter = 0 // actor state
//    for (msg in channel) { // iterate over incoming messages
//        when (msg) {
//            is IncCounter -> counter++
//            is GetCounter -> msg.response.complete(counter)
//        }
//    }
//}