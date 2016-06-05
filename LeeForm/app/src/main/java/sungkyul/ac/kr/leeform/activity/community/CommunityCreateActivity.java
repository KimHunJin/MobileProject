package sungkyul.ac.kr.leeform.activity.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityWritingBean;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;

/**
 * Created by MiSeon on 2016-05-18.
 * 커뮤니티 작성
 */
public class CommunityCreateActivity extends AppCompatActivity {
    ImageView camera, album;
    EditText edtCommunity;
    private static String URL = StaticURL.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_create);

        //툴바 텍스트 변경
        TextView tv = (TextView) findViewById(R.id.txtToolBarTitle);
        tv.setText("커뮤니티 작성");

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtCommunity = (EditText) findViewById(R.id.edtCommunity);

        TextView tv2 = (TextView) findViewById(R.id.tvOk);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCommunityCreate();
                finish();
            }
        });
    }

    private void setCommunityCreate() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String content = edtCommunity.getText().toString();
        Map<String, String> data = new HashMap<>();
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        Log.e("key", key);
        Log.e("Content", content);
        data.put("user_unique_key", key); //user_unique_key 가져오기
        data.put("community_writing_contents", content);

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<CommunityWritingBean> call = connectService.setCommunity(data);
        call.enqueue(new Callback<CommunityWritingBean>() {
            @Override
            public void onResponse(Call<CommunityWritingBean> call, Response<CommunityWritingBean> response) {
                CommunityWritingBean decodedResponse = response.body();
                Log.e("err", decodedResponse.getErr());
                Toast.makeText(getApplicationContext(), "완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommunityWritingBean> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }
}