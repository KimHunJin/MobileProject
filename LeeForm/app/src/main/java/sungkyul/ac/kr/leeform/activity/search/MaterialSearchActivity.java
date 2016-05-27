package sungkyul.ac.kr.leeform.activity.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MaterialSearchActivity extends AppCompatActivity {
    private LinearLayout linMain, linSearch;
    private EditText edtSearch;
    private ImageView imgSearch;
    private TextView tvOk2;
    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;

    ArrayList<MaterialGridItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_search);

        //뒤로가기 버튼
        ImageView imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //완료를 검색으로 텍스트 변경
        TextView tvOk=(TextView)findViewById(R.id.tvOk);
        tvOk.setText("검색");

       /* linMain = (LinearLayout) findViewById(R.id.linMainToolbar);
        linSearch = (LinearLayout) findViewById(R.id.linSearchToolbar);

        linMain.setVisibility(View.GONE);
        linSearch.setVisibility(View.VISIBLE);

        imgSearch = (ImageView) findViewById(R.id.imgSearch2);
        edtSearch = (EditText) findViewById(R.id.edtToolBarTitle);*/

        linMain = (LinearLayout) findViewById(R.id.backToolbar);
        linSearch = (LinearLayout) findViewById(R.id.linSearchToolbar);
        linMain.setVisibility(View.GONE);
        linSearch.setVisibility(View.VISIBLE);

        //   imgBack2=(ImageView)findViewById(R.id.imgBack2);
        tvOk2 = (TextView) findViewById(R.id.tvOk2);
        edtSearch = (EditText) findViewById(R.id.edtToolBarTitle);
        //검색화면의 뒤로가기 버튼
        ImageView imgBack2=(ImageView)findViewById(R.id.imgBack2);
        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        grvMaterial = (GridView)findViewById(R.id.grvMaterialSearch);
        mAdapter = new MaterialGridAdapter(getApplicationContext(), R.layout.item_grid_material, gridItems);

        grvMaterial.setAdapter(mAdapter);

        init();
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            gridItems.add(new MaterialGridItem(i, "판자", R.drawable.panza));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.commons_slide_from_left,R.anim.commons_slide_to_right);
    }
}
