package com.ten.air.phone.model

/**
 * Atmospheric monitoring record 大气监测记录
 */
class AirRecord {
    /**
     * Primary Key ID
     */
    var id: Int? = null
    /**
     * Device Source 设备来源 0:真机 1:虚拟
     */
    var source: String? = null
    /**
     * Record Imei 记录设备
     */
    var imei: String? = null
    /**
     * Temperature 温度
     */
    var temperature: String? = null
    /**
     * Humidity 湿度
     */
    var humidity: String? = null
    /**
     * PM 2.5 Concentration PM25浓度
     */
    var pm25: String? = null
    /**
     * Undefined data 待定义数据
     */
    var undefinedData: String? = null
    /**
     * Record Time 记录时间
     */
    var recordTime: String? = null
    /**
     * Update Time 更新时间
     */
    var updateTime: String? = null
    /**
     * is deleted 0:n 1:y
     */
    var isDeleted: Int? = null

    override fun toString(): String {
        return "AirRecord(id=$id, source=$source, imei=$imei, temperature=$temperature, humidity=$humidity, pm25=$pm25, undefinedData=$undefinedData, recordTime=$recordTime, updateTime=$updateTime, isDeleted=$isDeleted)"
    }

}
