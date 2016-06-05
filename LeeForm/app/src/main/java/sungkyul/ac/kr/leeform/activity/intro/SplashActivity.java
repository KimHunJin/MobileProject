package sungkyul.ac.kr.leeform.activity.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sungkyul.ac.kr.leeform.MainActivity;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.member.LoginActivity;

/**
 * Created by YongHoon on 2016-06-03.
 * 매인 스팰래시 작성
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 화면이 보이고 2초 뒤에 로그인 페이지로 넘어감
        Thread mTimer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent mIntro = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mIntro);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
