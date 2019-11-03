package com.coding.coinminer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coding.coinminer.calculates.Distributor.activateMiners
import com.coding.coinminer.calculates.Distributor.processNonces
import com.coding.coinminer.data.Model

import com.coding.coinminer.services.RepositoryProvider
import kotlinx.android.synthetic.main.activity_main.*
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import com.coding.coinminer.calculates.Miner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis



@kotlinx.coroutines.ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {


    private var disposable: Disposable? = null
    val repo = RepositoryProvider.blockRepository()
    var nonce =  CompletableDeferred<Long>()




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        logger.setMovementMethod(ScrollingMovementMethod())


        minerBtn.setOnClickListener{
            logger.append("\n-----------------------------------------\n")
            logger.append("Start Mining\n")
            minerBtn.setEnabled(false)
            beginMining(nonce){
                onNonceFound->
                minerBtn.setEnabled(true)
                logger.append("The nonce you found: " + onNonceFound.second)
                repo.postNonce(onNonceFound.first, onNonceFound.second)

            }

        }

    }


    fun beginMining(nonceFound: CompletableDeferred<Long>, onNonceFound: (nonce: Pair<Model.Block, Long>) -> Unit){
        repo.getBlockHeader()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    logger.append("The block header you got:\n")
                    logger.append(result.toString() + "\n")

                       var nonceFond = singleThreadMiner(result, nonce)
                       onNonceFound.invoke(Pair(result, nonceFond))

//                    An attempt to do the mineing task in a parallel way
//                    unBlocking{
//                        distributeMiners(it = result, nonceFound = nonceFound)
//                    }
//                    logger.append("Nonce you found: " +  nonceFound.getCompleted() + "\n")

//                    repo.postNonce(result, nonceFound.getCompleted())

                },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )

    }

    fun singleThreadMiner(it:Model.Block, nonceFound: CompletableDeferred<Long>): Long{
        logger.append("Single Thread \n")
        var nonce = it.blockHeader.Nonce

        var time = measureTimeMillis {
            while(Miner(it.blockHeader).verifyNonce(nonce) != true && nonce < Long.MAX_VALUE){
                nonce++;
            }
        }
        logger.append("Time consumed: " + time + "\n")
        nonceFound.complete(nonce)
        return nonce
    }

    fun distributeMiners(it: Model.Block, nonceFound: CompletableDeferred<Long>) {
        logger.append("Multi Thread \n")
        runBlocking{
            withContext(Dispatchers.Unconfined) {
                var time = measureTimeMillis {
                    // producer
                    val noncesChannel = processNonces(it.blockHeader.Nonce, Long.MAX_VALUE)
                    // consumer: distribute the nonces to miners to work
                    var resultChannel = activateMiners(noncesChannel, it.blockHeader)

                    var found = resultChannel.receive()
                    while (found.first != true) {
//                        logger.append(found.second.toString() + "\n")
                        found = resultChannel.receive()
                    }

                    nonceFound.complete(found.second)
                }
                logger.append("Time consumed: " + time + "\n")
                coroutineContext.cancelChildren() // cancel children coroutines
            }
        }
    }

}



