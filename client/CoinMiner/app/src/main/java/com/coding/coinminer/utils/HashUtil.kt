/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.utils

import java.lang.Long.parseLong
import java.security.MessageDigest

// Singleton
object HashUtil{

        fun sha512(input: ByteArray) = hashBytes("SHA-512", input)

        fun sha256(input: ByteArray) = hashBytes("SHA-256", input)

        fun sha1(input: ByteArray) = hashBytes("SHA-1", input)

        /**
         * Supported algorithms on Android:
         *
         * Algorithm	Supported API Levels
         * MD5          1+
         * SHA-1	    1+
         * SHA-224	    1-8,22+
         * SHA-256	    1+
         * SHA-384	    1+
         * SHA-512	    1+
         */
        private fun hashBytes(type: String, inputs: ByteArray): ByteArray {
            val bytes = MessageDigest
                .getInstance(type)
                .digest(inputs)

            return bytes
//            val result = StringBuilder()
//
//            bytes.forEach {
//                result.append("%2x".format(it))
//            }


//            fold in understanding
//            println("fold " +  bytes.fold("", { str, it -> str + "%02x".format(it) }))
//            var hex = result.toString()
////            var i = Long.parseLong(hex, 16)
//            println("hex " + hex)
//            println("result " +  result.toString())
//            return result.toString()

        }

        fun byteArrayToString(bytes: ByteArray):String{
            val result = StringBuilder()

            bytes.forEach {
                result.append("%2x".format(it))
            }
                return  result.toString()

        }

        fun hexToBinary(hex: String): String {
            return parseLong(hex, 16).toString(2)
        }
}
