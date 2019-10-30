/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data


object Model {
    /**
     *
    {
    jobId: 2
    clientId: 5,
    blockHeader: {
    version: 2,
    prevBlockhash: 00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81,
    merkleRoot: 2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3,
    timestamp: 1305998791,
    difficultyTarget: 17,
    Nonce: 2504433986
    }
    }
     */

//    var end: Int = 0
//    var info: Block? = null


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