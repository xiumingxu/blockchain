/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates


import com.coding.coinminer.data.Model
import com.coding.coinminer.utils.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

@ExperimentalCoroutinesApi

object Distributor{

    // A producer for producing the nounce for miner to hash
    fun CoroutineScope.processNonces(nonce: Long, end: Long)  = produce <Long>(capacity = 5) {
        for( n in nonce..end){
            log("NonceSender: " + n)
            send(n)
        }
    }

    // A producer of completed nonces to calculate
    fun CoroutineScope.activateMiners(nonces: ReceiveChannel<Long>,  blockHeader: Model.Header) = produce <Pair<Boolean, Long>> {
        nonces.consumeEach {
            // defered done
            log("Processing nonces: $it")
            // For each nonce active the miner
            coroutineScope {

                var res = Miner( blockHeader ).verifyNonce(it)

                send(Pair(res, it))

            }
        }
    }
}