/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data


object Model {
    // TODO Store in a way of variable, should be stored in a database/file
    var token: String
        get() {
            return token
        }
        set(value) { token = value}


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