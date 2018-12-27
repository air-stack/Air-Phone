package phone.air.ten.com.air_phone.http

/**
 * @description 子线程的回调接口
 */
interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}
