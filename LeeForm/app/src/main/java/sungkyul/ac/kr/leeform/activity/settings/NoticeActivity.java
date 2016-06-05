package sungkyul.ac.kr.leeform.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by MiSeon on 2016-06-02.
 * 공지사항
 */
public class NoticeActivity extends AppCompatActivity {

    TextView txtNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

    }
}
