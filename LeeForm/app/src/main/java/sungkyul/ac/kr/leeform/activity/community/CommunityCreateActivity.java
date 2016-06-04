package sungkyul.ac.kr.leeform.activity.community;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
<<<<<<< HEAD
import retrofit2.Callback;
import retrofit2.Response;
=======
>>>>>>> cdf23c9ab449981ff835a9e3702e0d82b7b6f3d9
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.dao.CoritingBean;
import sungkyul.ac.kr.leeform.fragment.CommunityFragment;
import sungkyul.ac.kr.leeform.utils.Util;

/**
 * Created by miseon on 2016-05-16.
 * 커뮤니티 작성
 */

public class CommunityCreateActivity extends AppCompatActivity {
    ImageView camera,album;
    EditText edtCommunity;
    private static String URL = "http://14.63.196.255/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_create);

        //툴바 텍스트 변경
        TextView tv=(TextView)findViewById(R.id.txtToolBarTitle);
        tv.setText("커뮤니티 작성");

        //뒤로가기 버튼
        ImageView imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
<<<<<<< HEAD
        TextView tv2=(TextView)findViewById(R.id.tvOk);
        tv2.setOnClickListener(new View.OnClickListener() {
======ditText)findViewById(R.id.

        /*camera=(ImageView)findViewById(R.id.imgCamera);
        album=(ImageView) findViewById(R.id.imgAlbum);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,1);
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(albumIntent);
            }
        });*/
edtComunity);

        camera=(ImageView)findViewById(R.id.imgCamera);
        album=(ImageView) findViewById(R.id.imgAlbum);

        camera.setOnClickListener(new View.OnClickListener() {
>>>>>>> cdf23c9ab449981ff835a9e3702e0d82b7b6f3d9
            @Override
            public void onClick(View v) {
                create();
                finish();
            }
        });
    }

    private void create(){
        Retrofit retrofit=new Retrofi2uilder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String content=edtCommunity.getText().toString();

        Map<String,String> data= new HashMap<>();
        // http://14.63.196.255/api/write_community.php?user_unique_key=key&community_writing_contents=content
        String key = Util.getAppPreferences(getApplicationContext(),"user_key");
        Log.e("key",key);
        Log.e("Content",content);
        data.put("user_unique_key",key); //user_unique_key 가져오기
        data.put("community_writing_contents",content);

        ConnectService connectService=retrofit.create(ConnectService.class);
        Call<CommunityWritingBean> call=connectService.setCommunity(data);
        call.enqueue(nonResponse(Call<CommunityWritingBean> call, Response<CommunityWritingBean> response) {
                CommunityWritingBean des= response.body();
                Log.e("ok","oooook");
                Lo

    private void test2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> data = new HashMap<>();
        data.put("user_unique_key", "3"); // user_unique_key에 2를 적고
        data.put("nickname", "돼지"); // nickname에 돼지를 적는다.

        // 즉 이 쿼리는
        // http://14.63.196.255/api/modify_myinfo.php?user_unique_key=2&nickname=돼지
        // 와 같이 날라간다.

        NetworkService networkService = retrofit.create(ConnectService.class);
        Call<UserBean> call = networkService.getUser(data); // json으로 뿌려질 값을 받는다.
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Log.e("Response", response.code() + ""); // 리스폰스 값을 받는다. 200일 경우 성공이다.
                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        Log.e("Not Success", response.errorBody().string());
                    } catch (IOException e) {
                        // do nothing
                    }
                    return;
                }

                UserBean decodedResponse = response.body(); // UserBean의 값을 디코드 시켜서 저장한다.
                if (decodedResponse == null) return; // 아무것도 없으면 리턴시킨다.
                else {
                    if (decodedResponse.getErr() == "0") { // err 값이 0일 경우 성공이다.
                        Log.e("Success", "닉네임 변경");
                    } else {
                        Log.e("Error", decodedResponse.getErr()); // err 값이 0이 아닐 경우 오류 명세서를 확인한다.
                    }
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("On Failure", t.getMessage() + "");
            }
        });
    }g.e("writeerr",des.getErr()+"");
                Toast.makeText(getApplicationContext(),"글작성ok",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<CommunityWritingBean> call, Throwable t) {
                Log.e("failure",t.getMessage());
            }
        });


    }
}
