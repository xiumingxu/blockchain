/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data

import com.coding.coinminer.data.Model.MiningData
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong


object Model {


    object MiningData {

        var version: Long = 0
        var prevBlockhash: String = ""
        var merkleRoot: String = ""
        var timestamp: String = ""
        var difficultyTarget: Int = 0
        // Volatile fields provide memory visibility
        // guarantee that the value that is being read, comes from the main memory and not the cpu-cache
        @Volatile
        var Nonce: AtomicLong = AtomicLong()

    }

    fun setUpMiningData(i: Header) {

        MiningData.version = i.version
        MiningData.prevBlockhash = i.prevBlockhash
        MiningData.merkleRoot = i.merkleRoot
        MiningData.timestamp = i.timestamp
        MiningData.difficultyTarget = i.difficultyTarget
        MiningData.Nonce = AtomicLong(i.Nonce.toLong())

    }


    data class Block(
        var jobId: Int,
        var clientId: Int?,
        var blockHeader: Header

    )


    data class Header(
        var version: Long,
        var prevBlockhash: String,
        var merkleRoot: String,
        var timestamp: String,
        var difficultyTarget: Int,
        var Nonce: Long

        )

}