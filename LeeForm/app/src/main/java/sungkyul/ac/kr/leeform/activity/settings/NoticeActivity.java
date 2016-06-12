package sungkyul.ac.kr.leeform.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import sungkyul.ac.kr.leeform.adapter.NoticeListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.NoticeBean;
import sungkyul.ac.kr.leeform.items.NoticeItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by MiSeon on 2016-06-02.
 * 공지사항
 */
public class NoticeActivity extends AppCompatActivity {

    private ListView lstNotice;
    private NoticeListAdapter noticeListAdapter;
    private ImageView imgBack, imgOk;
    TextView txtToolBarTitle;
    Toolbar toolbar;

    ArrayList<NoticeItem> noticeItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        layoutSetting();

        getNotice();

        txtToolBarTitle.setText("공지사항");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void layoutSetting() {
        txtToolBarTitle = (TextView)findViewById(R.id.txtToolBarTitle);
        imgBack = (ImageView)findViewById(R.id.imgBackOk);
        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        imgOk = (ImageView)findViewById(R.id.imgOk);

        toolbar.setContentInsetsAbsolute(0,0);
        imgOk.setVisibility(View.INVISIBLE);

        lstNotice = (ListView) findViewById(R.id.lstNotice);
        noticeListAdapter = new NoticeListAdapter(getApplicationContext(), R.layout.item_notice, noticeItemArrayList);
        lstNotice.setAdapter(noticeListAdapter);
    }

    private void getNotice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<NoticeBean> call = connectService.getNotice();
        call.enqueue(new Callback<NoticeBean>() {
            @Override
            public void onResponse(Call<NoticeBean> call, Response<NoticeBean> response) {
                NoticeBean decode = response.body();
                for (int i = 0; i < decode.getNotice_list().size(); i++) {
                    noticeItemArrayList.add(new NoticeItem(decode.getNotice_list().get(i).getNotice_unique_key(), decode.getNotice_list().get(i).getNotice_writing_contents(), decode.getNotice_list().get(i).getNotice_date(), decode.getNotice_list().get(i).getNotice_number()));
                }
                noticeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NoticeBean> call, Throwable t) {

            }
        });
    }
}
