package sungkyul.ac.kr.leeform.activity.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;
import sungkyul.ac.kr.leeform.adapter.MypageAdapter;

/**
 * Created by KyungHee on 2016-05-21.
 */
public class MyPageActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView tvWriting, tvScrap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //툴바 완료버튼 보이지 않게 하기
        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0, 0);


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
