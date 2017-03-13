package monster.com.myconstellation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {
        private EditText ed_Constellation,ed_time;
        private TextView name,data_time,all,love,lover,luckynumber,money,work,summary;
    private String KEY = "3ee5e9a3cc165bdcf2d0f95861bbb8f6";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ed_Constellation = (EditText) findViewById(R.id.ed_Constellation);
        ed_time = (EditText) findViewById(R.id.ed_time);
        name  = (TextView) findViewById(R.id.name);
        data_time  = (TextView) findViewById(R.id.data);
        all  = (TextView) findViewById(R.id.all);
        love  = (TextView) findViewById(R.id.love);
        lover  = (TextView) findViewById(R.id.lover);
        luckynumber  = (TextView) findViewById(R.id.number);
        money  = (TextView) findViewById(R.id.money);
        work  = (TextView) findViewById(R.id.work);
        summary  = (TextView) findViewById(R.id.summary);

    }
    public void find(View view){
        new Thread(){
            @Override
            public void run() {
                try {
                    data = "consName=" +ed_Constellation.getText() + "&type=" + ed_time.getText() +"&key=" + KEY;
                    URL strurl = new URL("http://web.juhe.cn:8080/constellation/getAll?" + data);
                    OkHttpUtils.get().url(String.valueOf(strurl)).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(MainActivity.this,"得不到数据",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, String response) {
                            //response为json的流对象
                            Gson gson = new Gson();
                            Bean bean = new Bean();
                            bean  = gson.fromJson(response,Bean.class);
                            String reciverAll = bean.getAll();


                            //Log.d("TAG",receiverData+receiverLove+receiverName);
                            //Log.d("TAG","all"+all);
                           all.setText(reciverAll);
                            String receiverData = bean.getDatetime();
                           data_time.setText(receiverData);
                            String receiverName = bean.getName();
                            name.setText(receiverName);
                            String receiverLove = bean.getLove();
                            love.setText(receiverLove);
                            int receiverNumber = bean.getNumber();
                            String s = String.valueOf(receiverNumber);
                           luckynumber.setText(s);
                            //Log.d("TAG","rec"+receiverNumber);
                           String receiverSummary = bean.getSummary();
                            summary.setText(receiverSummary);
                            String receiverMoney = bean.getMoney();
                           money.setText(receiverMoney);
                           String receiverQfriend = bean.getQFriend();
                           lover.setText(receiverQfriend);
                            String receiverWork= bean.getWork();
                           work.setText(receiverWork);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
