package sungkyul.ac.kr.leeform.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MypageFragmentAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by KyungHee on 2016-05-21.
 */
public class MyPageActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TextView txtModify, txtToolBarTitle;
    private ImageView imgBack, imgMypageUser;
    private Toolbar toolbar;
    private static String URL = StaticURL.BASE_URL;
    String imgUrl, image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);

        txtToolBarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        txtToolBarTitle.setText("마이페이지");

        //뒤로가기 버튼
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgMypageUser = (ImageView) findViewById(R.id.imgMypageUser);

        getUserImage();

        txtModify = (TextView) findViewById(R.id.txtMypageModify);
        txtModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageModifyActivity.class);
                intent.putExtra("imageurl", image);
                startActivity(intent);
            }
        });
        tabInitialization();
    }

    /**
     * 탭 초기화
     */
    private void tabInitialization() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MypageFragmentAdapter mainFragmentAdapter = new MypageFragmentAdapter(getSupportFragmentManager(), MyPageActivity.this);
        viewPager.setAdapter(mainFragmentAdapter);
        tabLayout = (TabLayout) findViewById(R.id.myPageTab);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * 유저 이미지 가져오기
     */
    public void getUserImage() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        final Call<UserBean> call = connectService.getUserDetail(key);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decodedResponse = response.body();
                imgUrl = decodedResponse.getMyinfo_detail().get(0).getImg();
                new DownloadImageTask(imgMypageUser).execute(imgUrl);
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}
