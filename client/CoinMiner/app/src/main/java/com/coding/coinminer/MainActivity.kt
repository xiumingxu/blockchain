package com.coding.coinminer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.coding.coinminer.calculates.Miner
import com.coding.coinminer.calculates.activateMiners
import com.coding.coinminer.calculates.processNonces
import com.coding.coinminer.data.Model
import com.coding.coinminer.data.Model.MiningData.Nonce
import com.coding.coinminer.data.Model.setUpMiningData
import com.coding.coinminer.data.RepositoryProvider
import com.coding.coinminer.utils.log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.channels.produce

import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext


@kotlinx.coroutines.ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {


    val coroutineContext: CoroutineContext get() =  Dispatchers.Main

    lateinit var block: LiveData<Model.Block>


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Load Datas
        val repo = RepositoryProvider.blockRepository()
        block = repo.getNewBlockHeader()

        // When data loadded
        block.observe(this, Observer {

            // Log information in the view
            logger.setText(it.blockHeader.merkleRoot)

            // Setup data model
            setUpMiningData(it.blockHeader)

            // Running mining
            runBlocking {
                withContext(Dispatchers.Default) {
                    // producer
                    val noncesChannel = processNonces(Nonce.toLong(),  Long.MAX_VALUE)

                    // consumer
                    val minerReceived = activateMiners(noncesChannel, Miner())

                    for (i in 1..5)
                        log(minerReceived.receive()) // print first five
                    log("Done!") // we are done
                    coroutineContext.cancelChildren() // cancel children coroutines

                }
            }
        })

    }



}



