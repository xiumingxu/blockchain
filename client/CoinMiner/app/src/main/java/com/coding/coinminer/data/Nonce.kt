/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data

import com.coding.coinminer.calculates.Miner
import com.coding.coinminer.utils.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.measureTimeMillis


sealed class Nounce
object IncCounter : Nounce() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : Nounce() // a request with reply


// This function launches a new counter actor

fun CoroutineScope.counterActor() = actor<Nounce> {

    var counter = 0 // actor state

    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}


suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 100000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}




