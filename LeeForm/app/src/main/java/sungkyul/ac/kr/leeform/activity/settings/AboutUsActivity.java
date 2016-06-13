package sungkyul.ac.kr.leeform.activity.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.NoticeListAdapter;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView imgBack, imgOk, imgHidden;
    private TextView txtToolBarTitle;
    private Toolbar toolbar;
    LinearLayout lin;
    int count = 0;

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

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count%10==0) {
                    imgHidden.setVisibility(View.VISIBLE);
                    imgHidden.setImageDrawable(getResources().getDrawable(R.drawable.dog));
                }
            }
        });
    }

    private void settingLayout() {
        txtToolBarTitle = (TextView)findViewById(R.id.txtToolBarTitle);
        imgBack = (ImageView)findViewById(R.id.imgBackOk);
        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        imgOk = (ImageView)findViewById(R.id.imgOk);
        imgHidden = (ImageView)findViewById(R.id.imgHiden);
        toolbar.setContentInsetsAbsolute(0,0);
        imgOk.setVisibility(View.INVISIBLE);
        lin = (LinearLayout)findViewById(R.id.linAbout);
        count = 0;

    }

}
