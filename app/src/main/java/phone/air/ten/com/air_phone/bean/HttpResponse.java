package phone.air.ten.com.air_phone.bean;

import android.support.annotation.NonNull;

/**
 * Http调用响应
 */
public class HttpResponse {

    private Integer code;
    private String msg;
    private String data;

    @NonNull
    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
