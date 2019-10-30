package com.coding.coinminer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.coding.coinminer.data.ApiService
import com.coding.coinminer.data.Request
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    // global variable for retrieving data

    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        val url = "https://localhost:3000/work"
//        val url = "http://10.0.2.2:3000/work"
//
//        doAsync {
//            Request(url).run()
//            uiThread {
//                toast("Request performed")
//            }
//        }




        beginSearch()

//        btn_search.setOnClickListener {
//            if (edit_search.text.toString().isNotEmpty()) {
//                beginSearch(edit_search.text.toString())
//            }
//        }



        //TODO  make Asyncval executor = Executors.newScheduledThreadPool(4)

    }


    private fun beginSearch() {
        disposable = apiService.retrieveData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.d("JAVA HAHA", result.BlockHeader.merkleRoot) },
                { error -> Log.d("JAVA error", error.message) }
            )
    }


    fun isHashValid(hash: String,  difficulty: Int): Boolean {
        var prefix = "0".repeat(difficulty)
        return hash.startsWith(prefix)
    }



}
