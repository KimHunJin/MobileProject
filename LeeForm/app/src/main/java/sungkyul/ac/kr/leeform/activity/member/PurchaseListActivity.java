package sungkyul.ac.kr.leeform.activity.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by KyungHee on 2016-05-20.
 */
public class PurchaseListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    ListView lstPurchaseList;
    private MainListAdapter adapter;
    ArrayList<MainListItem> listItem = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0,0);

        //툴바 완료버튼 보이지 않게 하기
        ImageView tvOK = (ImageView) findViewById(R.id.imgOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeLayout();
        setListener();

    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout() {
    }

    /**
     * 컴포넌트에 Listener 설정
     */
    private void setListener() {
    }
}
