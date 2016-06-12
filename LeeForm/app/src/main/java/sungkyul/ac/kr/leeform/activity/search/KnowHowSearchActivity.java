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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.KnowHowBean;
import sungkyul.ac.kr.leeform.items.MainListItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

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
    private InputMethodManager keyboard;

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

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listItems.clear();
                    getSearchResult(v.getText().toString());
                    keyboard.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    /**
     * 레이아웃 셋팅
     */
    void layoutSetting() {
        imgSearch = (ImageView) findViewById(R.id.imgSearchOk);
        edtSearch = (EditText) findViewById(R.id.edtToolBarSearch);
        imgBack = (ImageView) findViewById(R.id.imgBackSearch);
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
                listItems.clear();
                getSearchResult(edtSearch.getText().toString());
                keyboard.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                break;
            }
        }
    }

    /**
     * 검색한 노하우의 가격, 제작시간, 스크랩 수, 이미지 url, 제목, 간단한 설명 가져오기
     *
     * @param text
     */
    private void getSearchResult(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<KnowHowBean> call = connectService.getKnowHowSearch(text);
        call.enqueue(new Callback<KnowHowBean>() {
            @Override
            public void onResponse(Call<KnowHowBean> call, Response<KnowHowBean> response) {
                KnowHowBean decode = response.body();
                for (int i = 0; i < decode.getSearch_data().size(); i++) {
                    listItems.add(new MainListItem(
                                    Integer.parseInt(decode.getSearch_data().get(i).getWriting_unique_key()),
                                    decode.getSearch_data().get(i).getPrice(),
                                    decode.getSearch_data().get(i).getMaking_time(),
                                    decode.getSearch_data().get(i).getScrap_amount(),
                                    decode.getSearch_data().get(i).getPicture_url(),
                                    decode.getSearch_data().get(i).getWriting_name(),
                                    decode.getSearch_data().get(i).getExplanation()
                            )
                    );
                }
                mainListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<KnowHowBean> call, Throwable t) {

            }
        });
    }
}
