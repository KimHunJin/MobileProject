package sungkyul.ac.kr.leeform.activity.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by YongHoon on 2016-05-23.
 * 노하우 검색
 */
public class KnowHowSearchActivity extends AppCompatActivity {

    private LinearLayout linMain, linSearch;
    private EditText edtSearch;
    private TextView tvOk;
    private ImageView imgBack2;
    private MainListAdapter mainListAdapter;
    private ListView lst;

    ArrayList<MainListItem> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_how_search);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //완료를 검색으로 텍스트 변경
        tvOk.setText("검색");

        layoutSetting();

        linMain.setVisibility(View.GONE);
        linSearch.setVisibility(View.VISIBLE);

        //검색화면의 뒤로가기 버튼
        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mainListAdapter = new MainListAdapter(getApplicationContext(), R.layout.item_list_main, listItems);

        lst = (ListView) findViewById(R.id.listKnowHowSearch);
        lst.setAdapter(mainListAdapter);

        init();
    }

    void init() {
        for (int i = 0; i < 10; i++) {
//            listItems.add(new MainListItem(i, "23,000", "4", "2000",R.drawable.tables2)); //리스트에 추가
        }
    }

    void layoutSetting() {
        linMain = (LinearLayout) findViewById(R.id.backToolbar);
        linSearch = (LinearLayout) findViewById(R.id.linSearchToolbar);
        tvOk = (TextView) findViewById(R.id.tvOk);
        edtSearch = (EditText) findViewById(R.id.edtToolBarTitle);
        ImageView imgBack2 = (ImageView) findViewById(R.id.imgBack2);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.commons_slide_from_left, R.anim.commons_slide_to_right);
    }
}
