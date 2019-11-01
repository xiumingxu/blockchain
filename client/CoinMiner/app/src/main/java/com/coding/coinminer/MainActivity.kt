package com.coding.coinminer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.coding.coinminer.calculates.Distributor.activateMiners
import com.coding.coinminer.calculates.Distributor.processNonces
import com.coding.coinminer.data.Model
import com.coding.coinminer.data.Model.MiningData.Nonce
import com.coding.coinminer.data.Model.setUpMiningData
import com.coding.coinminer.data.RepositoryProvider
import kotlinx.android.synthetic.main.activity_main.*
import android.text.method.ScrollingMovementMethod
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import kotlin.system.measureTimeMillis


@kotlinx.coroutines.ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {



    lateinit var block: LiveData<Model.Block>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        logger.setMovementMethod(ScrollingMovementMethod())


        // Load Data in async task
        val repo = RepositoryProvider.blockRepository()
        block = repo.getNewBlockHeader()



        // TODO change to callback
        block.observe(this, Observer {

            // Log information in the view
            logger.append(it.toString() + "\n")

            // Setup data model
            setUpMiningData(it.blockHeader)

            // Running mining
            runBlocking {
                withContext(Dispatchers.Default) {
                    var time = measureTimeMillis {
                    // producer
                        val noncesChannel = processNonces(Nonce,  Long.MAX_VALUE)
                        // consumer: distribute the nonces to miners to work
                        var resultChannel = activateMiners(noncesChannel, it.blockHeader)


                        var found = resultChannel.receive()
                        while(found.first != true) {
                            logger.append(found.second.toString() + "\n")
                            found = resultChannel.receive()
                        }

                        // send to server the nounce found
                        logger.append("Nonce Found: " + found + "\n")

                        // Previous block and nonce found
                        repo.postNonce(it, found.second)

//                        postMeg.enqueue(object: Callback<Model.Block>){
//                            override fun onrespo
//
//                        }


                    }
                    logger.append("Time consumed: " + time + "\n")
                    coroutineContext.cancelChildren() // cancel children coroutines
                }
            }

        })



    }


}



