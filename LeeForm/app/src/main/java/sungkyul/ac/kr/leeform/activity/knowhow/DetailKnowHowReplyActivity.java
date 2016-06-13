package sungkyul.ac.kr.leeform.activity.knowhow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityReplyLIstAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.OnlyErrBean;
import sungkyul.ac.kr.leeform.dto.WritingDetailReplyListBean;
import sungkyul.ac.kr.leeform.items.ReplyItem;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

public class DetailKnowHowReplyActivity extends Activity {
    LinearLayout layKnowhowDetailReply;
    EditText edtContents;
    TextView txtRegist;
    ListView lstReply;

    private InputMethodManager keyboard;
    private CommunityReplyLIstAdapter adapter;

    ArrayList<ReplyItem> listItem = new ArrayList<>();

    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_know_how_reply);

        Intent it = getIntent();
        number = Integer.parseInt(it.getExtras().getString("key"));

        layoutSetting();
        lstReply.setAdapter(adapter);

        Log.e("numer", number + "");

        getWritingReply();


        txtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtContents.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    setKnowHowDetailReply();
                    edtContents.setText("");
                    //스크린키보드
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(edtContents.getWindowToken(), 0);
                }
            }
        });

        layKnowhowDetailReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void layoutSetting() {
        layKnowhowDetailReply = (LinearLayout)findViewById(R.id.layKnowhowDetailReply);
        edtContents = (EditText) findViewById(R.id.edtKnowHowDetailReplyContents);
        txtRegist = (TextView) findViewById(R.id.txtKnowHowDetailReplyRegister);
        adapter = new CommunityReplyLIstAdapter(getApplicationContext(), R.layout.item_list_reply, listItem);
        lstReply = (ListView) findViewById(R.id.lstKnowHowDetailReply);
        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        keyboard.hideSoftInputFromWindow(edtContents.getWindowToken(), 0);
        listItem.clear();
    }

    private void setKnowHowDetailReply() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        Map<String, String> data = new HashMap<>();
        data.put("user_unique_key", SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key"));
        data.put("writing_unique_key", number + "");
        data.put("reply_writing_contents", edtContents.getText().toString());

        Call<OnlyErrBean> call = connectService.setWritingDetailReply(data);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {
                getWritingReply();
            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {

            }
        });

    }

    private void getWritingReply() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        Call<WritingDetailReplyListBean> call = connectService.getWritingDetailReply(number + "");
        call.enqueue(new Callback<WritingDetailReplyListBean>() {
            @Override
            public void onResponse(Call<WritingDetailReplyListBean> call, Response<WritingDetailReplyListBean> response) {
                WritingDetailReplyListBean decode = response.body();
                int size = decode.getReply_list().size();
                Log.e("size", size + "");
                listItem.clear();
                for (int i = 0; i < size; i++) {
                    listItem.add(new ReplyItem(decode.getReply_list().get(i).getName().toString(),
                            decode.getReply_list().get(i).getReply_writing_contents(),
                            decode.getReply_list().get(i).getImg(),
                            decode.getReply_list().get(i).getReply_date()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WritingDetailReplyListBean> call, Throwable t) {

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.commons_slide_up_to_bottom, R.anim.commons_slide_up_to_bottom);
    }
}
