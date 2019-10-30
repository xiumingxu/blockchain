/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data

object RepositoryProvider {

    fun blockRepository(): BlockRepository {
        val apiService = BlockAPIService.create()
        return BlockRepository(apiService)
    }
}