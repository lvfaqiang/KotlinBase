package com.lvfq.kotlinbase.entities

/**
 * LoadingState2021/2/4 9:50 PM
 * @desc :
 *
 */
class LoadingState(
    val loading: Boolean,
    val message: String = "",
    val cancelable: Boolean = true
)