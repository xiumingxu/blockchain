/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.utils

import android.util.Log

fun log(v: Any) = Log.d("Coroutine", "[${Thread.currentThread().name}] $v")