/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.services

object RepositoryProvider {

    fun blockRepository(): BlockRepository {

        // Provider create the api Service
        val apiService =
            BlockAPIService.create()


        return BlockRepository(apiService)
    }
    // Could be extended to block repo1 block repo 3
}