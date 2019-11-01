/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data


object Model {

    object MiningData {

        var version: String = ""
        var prevBlockhash: String = ""
        var merkleRoot: String = ""
        var timestamp: String = ""
        var difficultyTarget: Long = 0
        var bits: String = ""
        // Volatile fields provide memory visibility
        // guarantee that the value that is being read, comes from the main memory and not the cpu-cache
        var Nonce: Long = 0

    }

    fun setUpMiningData(i: Header) {

        MiningData.version = i.version
        MiningData.prevBlockhash = i.prevBlockhash
        MiningData.merkleRoot = i.merkleRoot
        MiningData.timestamp = i.timestamp
        MiningData.difficultyTarget = i.difficultyTarget
        MiningData.bits = i.bits
        MiningData.Nonce = i.Nonce

    }


    data class Block(
        var jobId: Int,
        var clientId: Int,
        var blockHeader: Header

    )


    data class Header(
        var version: String,
        var prevBlockhash: String,
        var merkleRoot: String,
        var timestamp: String,
        var bits: String,
        var difficultyTarget: Long,
        var Nonce: Long

        )

}