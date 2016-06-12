package sungkyul.ac.kr.leeform.activity.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
import sungkyul.ac.kr.leeform.adapter.LicenseListAdapter;
import sungkyul.ac.kr.leeform.adapter.NoticeListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.LicenseBean;
import sungkyul.ac.kr.leeform.dto.NoticeBean;
import sungkyul.ac.kr.leeform.items.LicenseItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

public class LicenseActivity extends AppCompatActivity {

    private ImageView imgBack, imgOk;
    private TextView txtToolBarTitle;
    private Toolbar toolbar;
    private ListView lstLicense;

    ArrayList<LicenseItem> licenseItemArrayList = new ArrayList<>();
    LicenseListAdapter licenseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        layoutSetting();

        getLicense();

        txtToolBarTitle.setText("라이센스");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lstLicense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(),LicenseDetailActivity.class);
                it.putExtra("name",licenseItemArrayList.get(position).getLicenseName());
                it.putExtra("contents",licenseItemArrayList.get(position).getLicenseContents());
                startActivity(it);
            }
        });
    }

    private void layoutSetting() {
        txtToolBarTitle = (TextView)findViewById(R.id.txtToolBarTitle);
        imgBack = (ImageView)findViewById(R.id.imgBackOk);
        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        imgOk = (ImageView)findViewById(R.id.imgOk);
        lstLicense = (ListView)findViewById(R.id.lstLicense);

        licenseListAdapter = new LicenseListAdapter(getApplicationContext(),R.layout.item_license,licenseItemArrayList);

        lstLicense.setAdapter(licenseListAdapter);

        toolbar.setContentInsetsAbsolute(0,0);
        imgOk.setVisibility(View.INVISIBLE);
    }

    private void getLicense() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<LicenseBean> call = connectService.getLicense();
        call.enqueue(new Callback<LicenseBean>() {
            @Override
            public void onResponse(Call<LicenseBean> call, Response<LicenseBean> response) {
                LicenseBean decode = response.body();
                for(int i=0;i<decode.getLicense_list().size();i++) {
                    licenseItemArrayList.add(new LicenseItem(decode.getLicense_list().get(i).getLicense_unique_key(),decode.getLicense_list().get(i).getLicense_name(),decode.getLicense_list().get(i).getLicense_writing_contents()));
                }
                licenseListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LicenseBean> call, Throwable t) {

            }
        });

    }
}
