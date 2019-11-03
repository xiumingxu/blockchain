/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data


object Model {


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