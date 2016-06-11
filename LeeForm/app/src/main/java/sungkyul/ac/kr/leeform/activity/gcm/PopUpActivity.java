package sungkyul.ac.kr.leeform.activity.gcm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.intro.SplashActivity;

public class PopUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                // 키잠금 해제하기
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                // 화면 켜기
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        TextView tv = (TextView)findViewById(R.id.tv_content);

        Intent it = getIntent();
        String message = it.getExtras().getString("message");
        tv.setText(message);

        Button btnOk = (Button)findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
