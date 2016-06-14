package sungkyul.ac.kr.leeform.activity.knowhow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.credit.DemoCreditPage;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.KnowHowDetailBean;
import sungkyul.ac.kr.leeform.dto.OnlyErrBean;
import sungkyul.ac.kr.leeform.dto.WritingDetailReplyListBean;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by YongHoon on 2016-05-18.
 * 노하우 상세정보
 */
public class
DetailKnowHowActivity extends AppCompatActivity {
    StringBuffer sbContent;
    View layKnowhowDetail;
    private ImageView imgScrapImage;
    private LinearLayout btnKnowHowDetailShare, btnKnowHowDetailReply, btnKnowHowDetailScrap;
    private Toolbar toolbar;
    private ImageView imgKnowHowDetailMain, imgKnowHowDetailUserInfo, imgKnowHowDetailBuying;
    private TextView txtKnowHowDetailName, txtKnowHowDetailShortExplain, txtKnowHowDetailTime, txtKnowHowDetailUserName;
    private TextView txtKnowHowDetailLevel, txtKnowHowDetailMakeTime, txtKnowHowDetailMakingPrice, txtToolBarTitle;
    private TextView txtKnowHowDetailReplyCount, txtKnowHowDetailScrapCount;
    private boolean isScrap = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_how_detail);

        // 공유할때 보낼 컨텐츠 담을 버퍼 생성
        sbContent = new StringBuffer();

        //툴바 완료버튼 보이지 않게 하기
        ImageView imgOk = (ImageView) findViewById(R.id.imgOk);
        imgOk.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeLayout();

        imgKnowHowDetailBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DemoCreditPage.class));
            }
        });

        btnKnowHowDetailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itSendContent = new Intent();
                itSendContent.setAction(Intent.ACTION_SEND);
                itSendContent.putExtra(Intent.EXTRA_TEXT, sbContent.toString());
                itSendContent.setType("text/plain");
                startActivity(itSendContent);
            }
        });


        Intent it = getIntent();
        final String knowHowKey = it.getExtras().getString("knowhowkey");
        String imageUrl = it.getExtras().getString("image");

        Picasso.with(getApplicationContext()).load(imageUrl).resize(1080, 720).centerCrop().into(imgKnowHowDetailMain);
        getItem(knowHowKey);

        getKnowHowReplyCount(knowHowKey);

        scrapCheck(knowHowKey);

        btnKnowHowDetailReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),DetailKnowHowReplyActivity.class);
                it.putExtra("key",knowHowKey);
                startActivity(it);
                overridePendingTransition(R.anim.commons_slide_bottom_to_up, R.anim.commons_slide_bottom_to_up);
            }
        });

        btnKnowHowDetailScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스크랩 처리
                if(isScrap == true) {
                    // 스크랩 취소
                    cancelScrap(knowHowKey);
                } else {
                    // 스크랩
                    setScrap(knowHowKey);
                }
                scrapCheck(knowHowKey);
            }
        });

    }

    /**
     * 설명과 사진(동영상) 아이템을 넣는 메소드
     *
     * @param position 해당 아이템이 몇번째인지
     * @param explain  해당 아이템의 설명
     * @param url      사진이나 동영상의 url
     */
    private void makeKnowhowItem(int position, String explain, String url) {
        View itemView;

        itemView = View.inflate(getApplicationContext(), R.layout.item_knowhow_detail, null);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgKnowHowDetailContentsPicture);

        Picasso.with(getApplicationContext()).load(url).resize(1030, 600).centerCrop().into(imageView);

        TextView tv1 = (TextView) itemView.findViewById(R.id.tvKnowhowDetailExplain);
        tv1.setText(position + ". " + explain);

        LinearLayout layKnowhowDetailItem = (LinearLayout) itemView.findViewById(R.id.layKnowhowDetailItem);
        ((LinearLayout) layKnowhowDetail).addView(layKnowhowDetailItem);
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout() {
        btnKnowHowDetailShare = (LinearLayout)findViewById(R.id.btnKnowHowDetailShare);
        layKnowhowDetail = findViewById(R.id.layKnowhowDetail);
        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0, 0);
        imgKnowHowDetailBuying = (ImageView) findViewById(R.id.imgKnowHowDetailBuying);
        imgKnowHowDetailMain = (ImageView) findViewById(R.id.imgKnowHowDetailMain);
        imgKnowHowDetailUserInfo = (ImageView) findViewById(R.id.imgKnowHowDetailUserInfo);
        txtKnowHowDetailLevel = (TextView) findViewById(R.id.txtKnowHowDetailLevel);
        txtKnowHowDetailMakeTime = (TextView) findViewById(R.id.txtKnowHowDetailMakeTime);
        txtKnowHowDetailMakingPrice = (TextView) findViewById(R.id.txtKnowHowDetailMakingPrice);
        txtKnowHowDetailName = (TextView) findViewById(R.id.txtKnowHowDetailName);
        txtKnowHowDetailShortExplain = (TextView) findViewById(R.id.txtKnowHowDetailShortExplain);
        txtKnowHowDetailUserName = (TextView) findViewById(R.id.txtKnowHowDetailUserName);
        txtKnowHowDetailTime = (TextView) findViewById(R.id.txtKnowHowDetailTime);
        txtToolBarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        btnKnowHowDetailReply = (LinearLayout)findViewById(R.id.btnKnowHowReply);
        btnKnowHowDetailScrap = (LinearLayout)findViewById(R.id.btnKnowHowScrap);
        txtKnowHowDetailScrapCount = (TextView)findViewById(R.id.txtKnowHowDetailScrapCount);
        txtKnowHowDetailReplyCount = (TextView)findViewById(R.id.txtKnowHowDetailReplyCount);
        imgScrapImage = (ImageView)findViewById(R.id.imgScrapImage);
    }

    private void getItem(String writingUniqueKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        Log.e("writingKey", writingUniqueKey);
        Call<KnowHowDetailBean> call = connectService.getKnowHowDetail(writingUniqueKey);
        call.enqueue(new Callback<KnowHowDetailBean>() {
            @Override
            public void onResponse(Call<KnowHowDetailBean> call, Response<KnowHowDetailBean> response) {
                KnowHowDetailBean decode = response.body();
                Log.e("size", decode.getWriting_data1().get(0).getImg() + "");
                Picasso.with(getApplicationContext()).load(decode.getWriting_data1().get(0).getImg()).resize(0, imgKnowHowDetailUserInfo.getHeight()).into(imgKnowHowDetailUserInfo);
                txtKnowHowDetailLevel.setText(decode.getWriting_data1().get(0).getLevel().toString());
                txtKnowHowDetailMakeTime.setText(decode.getWriting_data1().get(0).getMaking_time().toString());
                txtKnowHowDetailMakingPrice.setText(decode.getWriting_data1().get(0).getCost().toString());
                txtKnowHowDetailName.setText(decode.getWriting_data1().get(0).getWriting_name().toString());
                txtKnowHowDetailShortExplain.setText(decode.getWriting_data1().get(0).getExplanation().toString());
                txtKnowHowDetailUserName.setText(decode.getWriting_data1().get(0).getName().toString());
                txtKnowHowDetailTime.setText(decode.getWriting_data1().get(0).getWriting_date());
                txtToolBarTitle.setText(decode.getWriting_data1().get(0).getWriting_name());
                txtKnowHowDetailScrapCount.setText(decode.getWriting_data1().get(0).getScrap_amount().toString());

                sbContent.append("★" + decode.getWriting_data1().get(0).getWriting_name().toString() + "★\n" +
                        decode.getWriting_data1().get(0).getExplanation().toString() +
                        "\n\n"+
                        "난이도 : " + decode.getWriting_data1().get(0).getLevel().toString() + " / " +
                        "소요시간 : " + decode.getWriting_data1().get(0).getCost().toString() + " / " +
                        "소요비용 : " + decode.getWriting_data1().get(0).getCost().toString() +
                        "\n\n" +
                        "<제작방법>" +
                        "\n"
                );
                for (int i = 0; i < decode.getWriting_data2().size(); i++) {
                    makeKnowhowItem(i + 1, decode.getWriting_data2().get(i).getWriting_contents().toString(), decode.getWriting_data2().get(i).getPicture_url().toString());
                    sbContent.append((i+1) + ". " + decode.getWriting_data2().get(i).getWriting_contents().toString());
                    if(i+1 != decode.getWriting_data2().size()){
                        sbContent.append("\n");
                    }
                }
            }

            @Override
            public void onFailure(Call<KnowHowDetailBean> call, Throwable t) {

            }
        });
    }

    private void getKnowHowReplyCount(String writingUniqueKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<WritingDetailReplyListBean> call = connectService.getWritingDetailReply(writingUniqueKey);
        call.enqueue(new Callback<WritingDetailReplyListBean>() {
            @Override
            public void onResponse(Call<WritingDetailReplyListBean> call, Response<WritingDetailReplyListBean> response) {
                WritingDetailReplyListBean decode = response.body();
                txtKnowHowDetailReplyCount.setText(decode.getReply_list().size()+"");
            }

            @Override
            public void onFailure(Call<WritingDetailReplyListBean> call, Throwable t) {

            }
        });
    }

    private void scrapCheck(final String writingKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        Map<String, String> map = new HashMap<>();
        map.put("user_unique_key", SaveDataMemberInfo.getAppPreferences(getApplicationContext(),"user_key"));
        map.put("writing_unique_key",writingKey);
        Call<OnlyErrBean> call = connectService.getCheckScrap(map);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {
                OnlyErrBean decode = response.body();
                if(decode.getErr()=="3") {
                    isScrap = true;
                    imgScrapImage.setImageDrawable(getResources().getDrawable(R.drawable.knowhow_like));
                } else if(decode.getErr()=="5") {
                    isScrap = false;
                    imgScrapImage.setImageDrawable(getResources().getDrawable(R.drawable.knowhow_unlike));
                }
                getKnowHowScrapCount(writingKey);
            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {

            }
        });

    }

    private void cancelScrap(final String writingKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_unique_key",SaveDataMemberInfo.getAppPreferences(getApplicationContext(),"user_key"));
        map.put("writing_unique_key",writingKey);

        Call<OnlyErrBean> call = connectService.unScrap(map);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {

            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {

            }
        });

    }

    private void setScrap(final String writingKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_unique_key",SaveDataMemberInfo.getAppPreferences(getApplicationContext(),"user_key"));
        map.put("writing_unique_key",writingKey);

        Call<OnlyErrBean> call = connectService.setScrap(map);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {
                push(writingKey);
            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {

            }
        });
    }

    private void getKnowHowScrapCount(String writingUniqueKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        Log.e("writingKey", writingUniqueKey);
        Call<KnowHowDetailBean> call = connectService.getKnowHowDetail(writingUniqueKey);
        call.enqueue(new Callback<KnowHowDetailBean>() {
            @Override
            public void onResponse(Call<KnowHowDetailBean> call, Response<KnowHowDetailBean> response) {
                KnowHowDetailBean decode = response.body();
                txtKnowHowDetailScrapCount.setText(decode.getWriting_data1().get(0).getScrap_amount().toString());

            }

            @Override
            public void onFailure(Call<KnowHowDetailBean> call, Throwable t) {

            }
        });
    }

    private void push(String writingUniqueKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<OnlyErrBean> call = connectService.push(writingUniqueKey);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {

            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {

            }
        });
    }
}
