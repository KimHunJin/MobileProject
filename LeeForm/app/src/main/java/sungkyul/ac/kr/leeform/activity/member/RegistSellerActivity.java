package sungkyul.ac.kr.leeform.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sungkyul.ac.kr.leeform.MainActivity;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;
import sungkyul.ac.kr.leeform.utils.SaveData;

/**
 * Created by YongHoon on 2016-06-01.
 */
public class RegistSellerActivity extends AppCompatActivity {
    Button btnRegistSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_seller);

        btnRegistSeller = (Button) findViewById(R.id.btnRegistSeller);

        btnRegistSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData.setAppPreferences(getApplicationContext(), "isSeller", "true");
                LoadActivityList.actList.clear();
                Intent itMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(itMain);
                finish();
            }
        });
    }
}
