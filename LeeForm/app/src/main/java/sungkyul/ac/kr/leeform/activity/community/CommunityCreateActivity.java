package sungkyul.ac.kr.leeform.activity.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by MiSeon on 2016-05-18.
 * 커뮤니티 작성
 */
public class CommunityCreateActivity extends AppCompatActivity {
    EditText edtCommunity;
    Toolbar toolbar;
    TextView txtToolBarTitle;
    ImageView imgBack, imgOk;
    private static String URL = StaticURL.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_create);

        layoutSetting();

        toolbar.setContentInsetsAbsolute(0, 0);
        //툴바 텍스트 변경
        txtToolBarTitle.setText("커뮤니티 작성");

        //뒤로가기 버튼
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //확인 버튼
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //내용을 작성하지 않았을 때 메시지 띄우기
                if (edtCommunity.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    //내용을 작성했을 때
                    setCommunityCreate();
                    finish();
                }
            }
        });
    }

    /**
     * 레이아웃 셋팅
     */
    private void layoutSetting() {
        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        txtToolBarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        edtCommunity = (EditText) findViewById(R.id.edtCommunity);
        imgOk = (ImageView) findViewById(R.id.imgOk);
    }

    /**
     * 커뮤니티 작성하기
     * 서버에 커뮤니티 내용과 작성자 키 보내기
     */
    private void setCommunityCreate() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String content = edtCommunity.getText().toString();
        Map<String, String> data = new HashMap<>();

        String userKey = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");

        data.put("user_unique_key", userKey);
        data.put("community_writing_contents", content);

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<CommunityWritingBean> call = connectService.setCommunity(data);
        call.enqueue(new Callback<CommunityWritingBean>() {
            @Override
            public void onResponse(Call<CommunityWritingBean> call, Response<CommunityWritingBean> response) {
                CommunityWritingBean decodedResponse = response.body();
                //Err값이 0이면 성공
                if (decodedResponse.getErr().equals("0")) {
                    Toast.makeText(getApplicationContext(), "완료", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommunityWritingBean> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }
}