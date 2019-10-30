/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates

import com.coding.coinminer.utils.HashUtil
import org.json.JSONObject

class Miner{


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

    private var end: Int
    private var block: JSONObject
    private var nonce:Int
    private var difficulty: Int


    constructor(input: JSONObject, range: IntArray, difficulty:Int){
        this.block =  input
        this.end = range[1]
        this.nonce = range[0]
        this.difficulty = difficulty
    }


    fun run(){
        while(nonce < end ){
            if (isHashValid(calculateHash()))
                break
//                return setToServer

            nonce++;
        }
    }

    /**
     * Returns the hash result string after sha256
     */
    fun calculateHash():String{

        return HashUtil.sha256(calculateBlockHash());
    }

    /**
     * Returns the hash content based on content and nonce
     */
    fun calculateBlockHash(): String{
        // TODO produce the new content
        //	record := string(block.Index) + block.Timestamp + string(block.BPM) + block.PrevHash
        return block.toString() + nonce + ""
    }


    /**
     * Returns the hash content based on content and nonce
     */
    fun isHashValid(hash: String): Boolean {
        var prefix = "0".repeat(this.difficulty)
        return hash.startsWith(prefix)
    }
}