package sungkyul.ac.kr.leeform.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityReplyLIstAdapter;

/**
 * Created by Kim on 2016-05-22.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_preference_screen_layout);
        addPreferencesFromResource(R.xml.pref_settings);

        //툴바 완료버튼 보이지 않게 하기
        TextView tvOK=(TextView)findViewById(R.id.tvOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
