package sungkyul.ac.kr.leeform.activity.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

public class MaterialSearchActivity extends AppCompatActivity {
    private LinearLayout linMain, linSearch;
    private EditText edtSearch;
    private ImageView imgSearch;

    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;

    ArrayList<MaterialGridItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_search);

        linMain = (LinearLayout) findViewById(R.id.linMainToolbar);
        linSearch = (LinearLayout) findViewById(R.id.linSearchToolbar);

        linMain.setVisibility(View.GONE);
        linSearch.setVisibility(View.VISIBLE);

        imgSearch = (ImageView) findViewById(R.id.imgSearch2);
        edtSearch = (EditText) findViewById(R.id.edtToolBarTitle);

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
