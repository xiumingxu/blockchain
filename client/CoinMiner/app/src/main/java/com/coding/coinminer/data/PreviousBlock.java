/*
 * Copyright (c) 2019. Xiuming Xu
 * All rights reserved
 */

package com.coding.coinminer.data;

class PreviousBlock {
    private static final PreviousBlock ourInstance = new PreviousBlock();

    static PreviousBlock getInstance() {
        return ourInstance;
    }

    private PreviousBlock() {

    }
}
