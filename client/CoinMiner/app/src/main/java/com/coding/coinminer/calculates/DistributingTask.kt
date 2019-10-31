/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates


import com.coding.coinminer.utils.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce


@ExperimentalCoroutinesApi
// A producer for producing the nounce for miner to hash
fun CoroutineScope.processNonces(nonce: Long, end: Long)  = produce {
    for( n in nonce..end){
        log("NonceSender: " + n)
        send(n)
    }
}

// A producer of completed nonces to calculate
fun CoroutineScope.activateMiners(nonces: ReceiveChannel<Long>, miner: Miner) = produce <Long> {
    for (n in nonces) {

        log("Processing nonces: $n")

        coroutineScope {
            val contentDeferred = async { miner.calculateContentToBeHashed(n) }
            val hashDeferred = async { miner.calculateHash(contentDeferred.await()) }
            val validation = async { miner.isHashValid(hashDeferred.await()) }

            send(n)

        }
    }
}