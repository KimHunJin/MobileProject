package sungkyul.ac.kr.leeform.activity.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.NoticeListAdapter;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView imgBack, imgOk;
    private TextView txtToolBarTitle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        settingLayout();
        txtToolBarTitle.setText("About Us");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void settingLayout() {
        txtToolBarTitle = (TextView)findViewById(R.id.txtToolBarTitle);
        imgBack = (ImageView)findViewById(R.id.imgBackOk);
        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        imgOk = (ImageView)findViewById(R.id.imgOk);

        toolbar.setContentInsetsAbsolute(0,0);
        imgOk.setVisibility(View.INVISIBLE);
    }
}
