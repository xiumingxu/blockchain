package com.coding.coinminer

import com.coding.coinminer.calculates.Miner
import com.coding.coinminer.data.Model.Header
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {



//
//    blockHeader: {
//        version: 2,
//        prevBlockhash: 00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81,
//        merkleRoot: 2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3,
//        timestamp: 1305998791,
//        difficultyTarget: 17,
//        Nonce: 2504433986
//    }

    var version: String = "20000000"
    var prevBlockhash: String = "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266"
    var merkleRoot: String = "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978"
    var timestamp: String = "5A50EB51"
    var bits: String = "180091c1"
    var difficultyTarget: Long = 17
    var Nonce: Long = 45291990


    var header  = Header(version, prevBlockhash, merkleRoot, timestamp, bits, difficultyTarget, Nonce)



    @Test
    fun addition_isCorrect() {
//        var test  = Miner(header).verifyNonce(Nonce)
        while(!Miner(header).verifyNonce(Nonce)){
            println("Nonce Validated " + Nonce)
            Nonce++
        }
//        assertEquals(4, 2 + 2)
    }
}
