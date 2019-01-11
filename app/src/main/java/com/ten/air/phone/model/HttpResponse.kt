package com.ten.air.phone.model

/**
 * Http调用响应
 */
class HttpResponse {

    var code: Int? = null
    var msg: String? = null
    var data: String? = null

    override fun toString(): String {
        return "HttpResponse(code=$code, msg=$msg, data=$data)"
    }

}
