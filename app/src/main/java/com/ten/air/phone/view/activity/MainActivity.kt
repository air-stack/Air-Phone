package com.ten.air.phone.view.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.ten.air.phone.presenter.HttpCallbackListener
import com.ten.air.phone.presenter.HttpUtil
import kotlinx.android.synthetic.main.activity_main.*
import com.ten.air.phone.model.AirRecord
import com.ten.air.phone.model.HttpResponse
import phone.air.ten.com.air_phone.R
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var showData1: TextView? = null
    private var showData2: TextView? = null
    private var showData3: TextView? = null
    private var showData4: TextView? = null

    private val handler = Handler()

    private val executorService = ScheduledThreadPoolExecutor(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        regisViews()

        setInitText()

        // 每隔3s调度一次
        executorService.schedule(object : Runnable {
            override fun run() {
                HttpUtil.sendHttpRequest(RECORD_URL, HttpCallbackListenerImpl())
                handler.post(this)
            }
        }, 3000, TimeUnit.MILLISECONDS)

    }

    /**
     * 注册TextView组件
     */
    private fun regisViews() {
        showData1 = show_data_1
        showData2 = show_data_2
        showData3 = show_data_3
        showData4 = show_data_4
    }

    /**
     * 初始化TextView内容
     */
    private fun setInitText() {
        showData1!!.text = INIT_TEXT
        showData2!!.text = INIT_TEXT
        showData3!!.text = INIT_TEXT
        showData4!!.text = INIT_TEXT
    }

    /**
     * 设置TextView内容
     */
    fun setTextContent(airRecord: AirRecord?) {
        showData1!!.text = airRecord!!.temperature
        showData2!!.text = airRecord.humidity
        showData3!!.text = airRecord.pm25
        showData4!!.text = airRecord.undefinedData
    }

    /**
     * HTTP回调子对象
     */
    internal inner class HttpCallbackListenerImpl : HttpCallbackListener {

        override fun onFinish(response: String) {
            val httpResponse = JSON.parseObject(response, HttpResponse::class.java)
            if (httpResponse.code == 0) {
                val airRecord = JSON.parseObject(httpResponse.data, AirRecord::class.java)
                setTextContent(airRecord)
            } else {
                setInitText()
            }
        }

        override fun onError(e: Exception) {
            e.printStackTrace()

            val airRecord = AirRecord()
            airRecord.imei = "未获取到设备..."
            airRecord.temperature = ERROR_MSG
            airRecord.humidity = ERROR_MSG
            airRecord.pm25 = ERROR_MSG
            airRecord.undefinedData = ERROR_MSG

            setTextContent(airRecord)
        }


    }


    companion object {
        private val ERROR_MSG = "网络不通畅..."

        private val INIT_TEXT = "正在获取数据中..."

        /**
         * 获取最近的数据
         */
        private val RECORD_URL = "http://192.168.43.74:8090/air/record/last"
    }

}
