package com.coding.coinminer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coding.coinminer.data.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        val url = "https://localhost:3000/work"
        val url = "http://10.0.2.2:3000/work"

        doAsync {
            Request(url).run()
            uiThread {
                toast("Request performed")
            }
        }


        //TODO  make Asyncval executor = Executors.newScheduledThreadPool(4)

    }





    fun isHashValid(hash: String,  difficulty: Int): Boolean {
        var prefix = "0".repeat(difficulty)
        return hash.startsWith(prefix)
    }



}
