package sungkyul.ac.kr.leeform.activity.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

/**
 * Created by KyungHee on 2016-05-22.
 * 재료 검색
 */
public class MaterialSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtSearch;
    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;
    private Toolbar toolbar;
    private ImageView imgBack, imgSearch;

    ArrayList<MaterialGridItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_search);

        layoutSetting();

        toolbar.setContentInsetsAbsolute(0, 0);
        edtSearch.setHint("재료를 입력하세요.");

        imgSearch.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        grvMaterial = (GridView) findViewById(R.id.grvMaterialSearch);
        mAdapter = new MaterialGridAdapter(getApplicationContext(), R.layout.item_grid_material, gridItems);

        grvMaterial.setAdapter(mAdapter);

        init();
    }

    void layoutSetting() {
        imgBack = (ImageView) findViewById(R.id.imgBackSearch);
        imgSearch = (ImageView) findViewById(R.id.imgSearchOk);
        edtSearch = (EditText) findViewById(R.id.edtToolBarSearch);
        toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            gridItems.add(new MaterialGridItem(i, "판자", R.drawable.panza));
        }
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
