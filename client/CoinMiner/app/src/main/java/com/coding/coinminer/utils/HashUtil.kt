/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.utils

import java.security.MessageDigest

// Singleton
object HashUtil{

        fun sha512(input: String) = hashString("SHA-512", input)

        fun sha256(input: String) = hashString("SHA-256", input)

        fun sha1(input: String) = hashString("SHA-1", input)

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
        private fun hashString(type: String, input: String): String {
            val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
            val result = StringBuilder()


            bytes.forEach {
                result.append("%2x".format(it))
            }


//            fold in understanding
            println("fold " +  bytes.fold("", { str, it -> str + "%02x".format(it) }))
            var hex = result.toString()
//            var i = Long.parseLong(hex, 16)
            println("hex " + hex)
            println("result " +  result.toString())
            return result.toString()


        }
//        fun hexToBinary(hex: String): String {
////            return Long(hex, 16).toString(2)
//        }
}
