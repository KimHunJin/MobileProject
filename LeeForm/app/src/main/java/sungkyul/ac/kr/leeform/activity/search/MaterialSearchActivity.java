package sungkyul.ac.kr.leeform.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.MaterialListBean;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by KyungHee on 2016-05-22.
 * 재료 검색
 */
public class MaterialSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSearch;
    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;
    private Toolbar toolbar;
    private ImageView imgBack, imgSearch;
    private InputMethodManager keyboard;

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

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    gridItems.clear();
                    getSearchResult(v.getText().toString());
                    keyboard.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 레이아웃 셋팅
     */
    void layoutSetting() {
        imgBack = (ImageView) findViewById(R.id.imgBackSearch);
        imgSearch = (ImageView) findViewById(R.id.imgSearchOk);
        edtSearch = (EditText) findViewById(R.id.edtToolBarSearch);
        toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                gridItems.clear();
                getSearchResult(edtSearch.getText().toString());
                keyboard.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                break;
            }
        }
    }

    /**
     * 검색한 재료 정보 가져오기
     *
     * @param text
     */
    private void getSearchResult(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<MaterialListBean> call = connectService.getMaterialSearch(text);
        call.enqueue(new Callback<MaterialListBean>() {
            @Override
            public void onResponse(Call<MaterialListBean> call, Response<MaterialListBean> response) {
                MaterialListBean decode = response.body();
                for (int i = 0; i < (decode.getSearch_data().size()); i++) {
                    gridItems.add(new MaterialGridItem(decode.getSearch_data().get(i).getMaterial_unique_key(), decode.getSearch_data().get(i).getMaterial_picture_url(), decode.getSearch_data().get(i).getMaterial_name(), decode.getSearch_data().get(i).getMaterial_price()));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MaterialListBean> call, Throwable t) {

            }
        });

    }
}
