/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates

import android.util.Log
import com.coding.coinminer.utils.HashUtil
import com.coding.coinminer.data.Model

class Miner{
    // Multithread running Miner

    /**
     * {
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

    private var version: Long
    private var prevBlockhash: String
    private var merkleRoot: String
    private var timestamp: String
    private var difficulty: Int
    private var Nonce: Long


    constructor(b: Model.Header){
        version = b.version
        prevBlockhash = b.prevBlockhash
        merkleRoot = b.merkleRoot
        difficulty =  b.difficultyTarget
        Nonce = b.Nonce
        timestamp = b.timestamp
    }

    fun run(){
        Log.d("one thread miner", "one miner")
    }

//    fun run(){
//        while(nonce < end ){
//            if (isHashValid(calculateHash()))
//                break
////                return setToServer
//
//            nonce++;
//        }
//    }

    // Returns the hash result string after sha256
    fun calculateHash():String{

        return HashUtil.sha256(calculateBlockHash());
    }

    /**
     * Returns the hash content based on content and nonce
     */
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