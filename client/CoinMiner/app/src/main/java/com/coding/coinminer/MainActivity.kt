package com.coding.coinminer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coding.coinminer.calculates.Miner
import com.coding.coinminer.data.Model
import com.coding.coinminer.data.RepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var block: LiveData<Model.Block>


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        val repo = RepositoryProvider.blockRepository()

        block = repo.getNewBlockHeader()

        // when loadded
        block.observe(this, Observer {
            // run miner in several threads

            logger.setText(it.blockHeader.merkleRoot)
            var miningData:  MutableLiveData<Model.Header>

//            miningData.value = Model.Header(it.blockHeader)
            Miner(it.blockHeader).run()

        })

    }



}
