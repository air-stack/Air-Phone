package com.ten.air.phone.presenter

/**
 * @description 子线程的回调接口
 */
interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}
