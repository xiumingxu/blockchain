/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

//package com.coding.coinminer
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Observer
//import com.coding.coinminer.calculates.Miner
//import com.coding.coinminer.data.Model
//import com.coding.coinminer.data.Model.setUpMiningData
//import com.coding.coinminer.data.RepositoryProvider
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.coroutines.*
//import kotlinx.coroutines.sync.Mutex
//import kotlinx.coroutines.sync.withLock
//import kotlin.system.measureTimeMillis
//
//class MainActivity2 : AppCompatActivity() {
//
//    lateinit var block: LiveData<Model.Model>
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main)
//
//        // Load Datas
//        val repo = RepositoryProvider.blockRepository()
//        block = repo.getNewBlockHeader()
//        // When data loadded
//        block.observe(this, Observer {
//
//            // Log information in the view
//            logger.setText(it.blockHeader.merkleRoot)
//
//            // Setup data model
//            setUpMiningData(it.blockHeader)
//
//
//
//
//            var counter = Model.MiningData.Nonce
//            // based on nonce to seperate the tasks
//
//
//
//
//            val mutex = Mutex()
//
//            runBlocking {
//                withContext(Dispatchers.IO) {
//
//                    // Make nonce public
//                    activateMiners {
//                        Miner(it.blockHeader).verifyNonce(counter)
//                        mutex.withLock {
//                            counter++
//                        }
//                    }
//                }
//            }
//
//        })
//
//    }
//
//
//
//    suspend fun activateMiners(action: suspend () -> Unit) {
//        val n = 100  // number of coroutines to launch
//        val k = 1000 // times an action is repeated by each coroutine
//        val time = measureTimeMillis {
//            coroutineScope { // scope for coroutines
//                repeat(n) {
//                    launch {
//                        repeat(k) { action() }
//                    }
//                }
//            }
//        }
//        println("Completed ${n * k} actions in $time ms")
//    }
//
//
//
//
//
//
//}
