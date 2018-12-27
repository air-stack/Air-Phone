package phone.air.ten.com.air_phone;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import phone.air.ten.com.air_phone.http.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import phone.air.ten.com.air_phone.bean.AirRecord;
import phone.air.ten.com.air_phone.bean.HttpResponse;
import phone.air.ten.com.air_phone.http.HttpCallbackListener;

public class MainActivity extends AppCompatActivity {

    private TextView showData1;
    private TextView showData2;
    private TextView showData3;
    private TextView showData4;

    private Handler handler = new Handler();

    private ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1);

    private static final String INIT_TEXT = "正在获取数据中...";

    /**
     * 获取最近的数据
     */
    private static final String RECORD_URL = "http://192.168.43.74:8090/air/record/last";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regisViews();

        setInitText();

        // 每隔3s调度一次
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                HttpUtil.INSTANCE.sendHttpRequest(RECORD_URL, new HttpCallbackListenerImpl());
                handler.post(this);
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }

    /**
     * 注册TextView组件
     */
    private void regisViews() {
        showData1 = findViewById(R.id.show_data_1);
        showData2 = findViewById(R.id.show_data_2);
        showData3 = findViewById(R.id.show_data_3);
        showData4 = findViewById(R.id.show_data_4);
    }

    /**
     * 初始化TextView内容
     */
    private void setInitText() {
        showData1.setText(INIT_TEXT);
        showData2.setText(INIT_TEXT);
        showData3.setText(INIT_TEXT);
        showData4.setText(INIT_TEXT);
    }

    /**
     * 设置TextView内容
     */
    public void setTextContent(AirRecord airRecord) {
        showData1.setText(airRecord.getTemperature());
        showData2.setText(airRecord.getHumidity());
        showData3.setText(airRecord.getPm25());
        showData4.setText(airRecord.getUndefinedData());
    }

    /**
     * HTTP回调子对象
     */
    class HttpCallbackListenerImpl implements HttpCallbackListener {

        @Override
        public void onFinish(@NotNull String response) {
            HttpResponse httpResponse = JSON.parseObject(response, HttpResponse.class);
            if (httpResponse.getCode() == 0) {
                AirRecord airRecord = JSON.parseObject(httpResponse.getData(), AirRecord.class);
                setTextContent(airRecord);
            } else {
                setInitText();
            }
        }

        private static final String ERROR_MSG = "网络不通畅...";

        @Override
        public void onError(@NotNull Exception e) {
            e.printStackTrace();

            AirRecord airRecord = new AirRecord();
            airRecord.setImei("未获取到设备...");
            airRecord.setTemperature(ERROR_MSG);
            airRecord.setHumidity(ERROR_MSG);
            airRecord.setPm25(ERROR_MSG);
            airRecord.setUndefinedData(ERROR_MSG);

            setTextContent(airRecord);
        }
    }

}
