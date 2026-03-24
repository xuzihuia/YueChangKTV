package com.yuechang.ktv.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 协程扩展函数
 */

suspend fun <T> withIO(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

suspend fun <T> withMain(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main, block)
}