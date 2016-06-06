package sungkyul.ac.kr.leeform.activity.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by YongHoon on 2016-05-23.
 * 노하우 검색
 */
public class KnowHowSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText edtSearch;
    private ImageView imgBack, imgSearch;
    private MainListAdapter mainListAdapter;
    private ListView lst;

    ArrayList<MainListItem> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_how_search);

        layoutSetting();

        toolbar.setContentInsetsAbsolute(0, 0);
        //뒤로가기 버튼
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        edtSearch.setHint("노하우를 입력하세요.");

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
        imgSearch = (ImageView) findViewById(R.id.imgSearchOk);
        edtSearch = (EditText) findViewById(R.id.edtToolBarSearch);
        imgBack = (ImageView) findViewById(R.id.imgBackSearch);
        toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.commons_slide_from_left, R.anim.commons_slide_to_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBackSearch: {
                finish();
                break;
            }
            case R.id.imgSearchOk: {
                // 검색
                break;
            }
        }
    }
}
