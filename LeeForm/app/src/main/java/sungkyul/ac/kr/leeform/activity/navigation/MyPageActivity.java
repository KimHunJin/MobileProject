package sungkyul.ac.kr.leeform.activity.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by KyungHee on 2016-05-21.
 */
public class MyPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        //툴바 완료버튼 보이지 않게 하기
        TextView tvOK = (TextView) findViewById(R.id.tvOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
