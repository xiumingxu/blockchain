/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.calculates

import com.coding.coinminer.data.Model
import com.coding.coinminer.utils.HashUtil
import com.swallowsonny.convertextlibrary.writeInt32LE
import java.lang.Long.parseLong
import java.lang.StringBuilder
import java.nio.ByteBuffer
import android.widget.Toast


// Using cocurrency pipeline to generate the mining work

class Miner(
    val blockHeader: Model.Header
) {

    // Calculating the hashing work
    private lateinit var merkleRootArrayReversed: ByteArray
    private lateinit var preHashArrayReversed: ByteArray
    private lateinit var versionArray: ByteArray
    private lateinit var timeBitsNonceArr: ByteArray
    private lateinit var target: ByteArray
    private var difficultyTarget: Int = 0

    init {
        // TODO get hashed content in another class
        initializeHashContent()
        initializeTarget()
    }


    // return the hash to see whether it is potential target
    fun verifyNonce(nonce: Long): Boolean {
        var content = generateHash(nonce)
        var hashed = sha256256(content)

        // Due to Little Endinn: array need to be reversed
        var result = reverseByteArray(hashed)

        // Translate bytes array to string
        var res = StringBuilder()
        result.forEach { it ->
            var binary = it.toUByte().toString(2)
            if (binary.length < 8)
                binary = "0".repeat(8 - binary.length) + binary
            res.append(binary)
        }

        // An easier way to calculate difficulty target
        if (res.toString().startsWith("0".repeat(this.difficultyTarget)))
            return true
        return false

    }

    // Returns the hash result string after sha256 sha256: for distribute the work
    private fun sha256256(s: ByteArray): ByteArray {
        return HashUtil.sha256(HashUtil.sha256(s))
    }


    private fun generateHash(nonce: Long): ByteArray {
        timeBitsNonceArr = getTimeBitsNonceArr(nonce)
        return versionArray + preHashArrayReversed + merkleRootArrayReversed + timeBitsNonceArr
    }


    // Returns the hash content based on block header
    // TODO could be done in parallel
    private fun initializeHashContent() {

        var b = blockHeader

        timeBitsNonceArr = getTimeBitsNonceArr(b.Nonce)

        versionArray = getByteArrayFromStringInSize(b.version, 4)

        // previous block hash
        // from large endian to little endine
        preHashArrayReversed = reverseByteArray(hexStringToByteArray(b.prevBlockhash))
        merkleRootArrayReversed = reverseByteArray(hexStringToByteArray(b.merkleRoot))

    }


    private fun initializeTarget() {

        // Real Case: Target Generated based on bits
        var bits = parseLong(blockHeader.bits, 16)
        var exponent = bits shr 24
        var mantissa = bits and 0xffffff

        var targetInt =
            (mantissa * Math.pow(2.0, 8.0 * (exponent - 3))).toBigDecimal().toBigInteger()

        var targetStr = targetInt.toString(16)
        targetStr = "0".repeat(64 - targetStr.length) + targetStr

        target = hexStringToByteArray(targetStr)
        // difficultyTarget
        difficultyTarget = blockHeader.difficultyTarget.toInt()

    }


    private fun getTimeBitsNonceArr(nonce: Long): ByteArray {
        var b = blockHeader
        var timeBitsNonceArr: ByteArray = ByteBuffer.allocate(12).array()

        var time = parseLong(b.timestamp, 16)
        var bits = parseLong(b.bits, 16)

        timeBitsNonceArr.writeInt32LE(time, 0)
        timeBitsNonceArr.writeInt32LE(bits, 4)
        timeBitsNonceArr.writeInt32LE(nonce, 8)
        return timeBitsNonceArr
    }


    // reverse the array
    private fun reverseByteArray(array: ByteArray): ByteArray {
        var left: Int = 0
        var right = array.size - 1
        while (left < right) {
            val tmp = array[left]
            array[left] = array[right]
            array[right] = tmp
            left++
            right--
        }
        return array
    }


    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] =
                ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    // Allocate size space for byte array
    private fun initByteArray(size: Int): ByteArray {
        var res: ByteArray = ByteBuffer.allocate(size).array()
        return res
    }


    //Return the version byte array
    private fun getByteArrayFromStringInSize(str: String, size: Int): ByteArray {
        var array = initByteArray(size)
        var value = parseLong(str, 16)

        array.writeInt32LE(value, 0)
        return array
    }

}


