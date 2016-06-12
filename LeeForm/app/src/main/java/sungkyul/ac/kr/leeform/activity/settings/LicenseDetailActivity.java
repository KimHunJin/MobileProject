package sungkyul.ac.kr.leeform.activity.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.LicenseListAdapter;

public class LicenseDetailActivity extends AppCompatActivity {
    private ImageView imgBack, imgOk;
    private TextView txtToolBarTitle, txtLicenseDetail;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_detail);

        layoutSetting();

        Intent it = getIntent();
        String name = it.getExtras().getString("name");
        String contents = it.getExtras().getString("contents");

        txtToolBarTitle.setText(name);
        txtLicenseDetail.setText(contents);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void layoutSetting() {
        txtToolBarTitle = (TextView)findViewById(R.id.txtToolBarTitle);
        txtLicenseDetail = (TextView)findViewById(R.id.txtLicenseDetailContents);
        imgBack = (ImageView)findViewById(R.id.imgBackOk);
        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        imgOk = (ImageView)findViewById(R.id.imgOk);

        toolbar.setContentInsetsAbsolute(0,0);
        imgOk.setVisibility(View.INVISIBLE);
    }
}
