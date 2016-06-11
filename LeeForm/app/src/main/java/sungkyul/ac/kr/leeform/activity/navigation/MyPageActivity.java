package sungkyul.ac.kr.leeform.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;
import sungkyul.ac.kr.leeform.adapter.MypageAdapter;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;

/**
 * Created by KyungHee on 2016-05-21.
 */
public class MyPageActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TextView  txtModify;
    private ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //뒤로가기 버튼
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtModify=(TextView)findViewById(R.id.txtMypageModify);
        txtModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MyPageModifyActivity.class);
                startActivity(intent);
            }
        });

        tabInitialization();

    }
   private void tabInitialization() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MypageAdapter mainFragmentAdapter = new MypageAdapter(getSupportFragmentManager(), MyPageActivity.this);
        viewPager.setAdapter(mainFragmentAdapter);

        tabLayout = (TabLayout) findViewById(R.id.myPageTab);
        tabLayout.setupWithViewPager(viewPager);

    }

}
