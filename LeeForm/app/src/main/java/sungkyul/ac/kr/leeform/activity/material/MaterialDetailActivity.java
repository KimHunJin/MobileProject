package sungkyul.ac.kr.leeform.activity.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by Kim on 2016-05-22.
 */
public class MaterialDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        initializeLayout();
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout(){

    }
}
