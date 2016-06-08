package sungkyul.ac.kr.leeform.activity.community;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
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
import sungkyul.ac.kr.leeform.dto.CommunityDetailBean;
import sungkyul.ac.kr.leeform.dto.CommunityListBean;
import sungkyul.ac.kr.leeform.items.ReplyItem;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.TimeTransForm;

/**
 * Created by MiSeon on 2016-05-18.
 * 커뮤니티 디테일
 * 커뮤니티 리스트에서 선택한 커뮤니티와 댓글을 나타낸다.
 */
public class CommunityDetailActivity extends AppCompatActivity {

    private CommunityReplyLIstAdapter adapter;
    ArrayList<ReplyItem> listItem = new ArrayList<>();
    private static String URL = StaticURL.BASE_URL;
    int number;
    TextView content, replyCount, userName, txtReplyRegister, txtTime;
    ImageView userImg, imgBack;
    EditText edtContents;
    ListView lst;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        //툴바 완료버튼 보이지 않게 하기
        ImageView imgOk = (ImageView) findViewById(R.id.imgOk);
        imgOk.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //inflater를 통해 item_list_community.xml을 가져온다.
        header = getLayoutInflater().inflate(R.layout.header_community_detail, null, false);
        //adapter를 통해 item.list.reply.xml을 ArrayList(listItem)에 설정한다.
        adapter = new CommunityReplyLIstAdapter(getApplicationContext(), R.layout.item_list_reply, listItem);
        lst = (ListView) findViewById(R.id.replyList);
        //lst에 adapter를 등록한다.
        lst.addHeaderView(header);
        lst.setAdapter(adapter);

        //리스트 윗부분에 넣기

        layoutSetting();
        getCommunityDetail();
        getCommunityReply();

        txtReplyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtContents.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    setCommunityReply();
                    edtContents.setText("");
                    //스크린키보드
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(edtContents.getWindowToken(), 0);
                }
            }
        });
    }

    private void layoutSetting() {
        txtTime=(TextView)header.findViewById(R.id.txtHeaderCommunityTime);
        content = (TextView) header.findViewById(R.id.txtHeaderContentCommunity);
        replyCount = (TextView) findViewById(R.id.txtHeaderReplyCount);
        userName = (TextView) header.findViewById(R.id.txtHeaderCommunityUserName);
        userImg = (ImageView) header.findViewById(R.id.imgHeaderCommunityDetail);
        txtReplyRegister = (TextView) findViewById(R.id.txtCommunityReplyRegister);
        edtContents = (EditText) findViewById(R.id.edtCommunityReplyContents);
    }

    /**
     * 커뮤니티 디테일부분
     * 선택한 커뮤니티의 작성자이름,내용,이미지 가져오는 부분
     * 가져온 내용을 TextView나 ImageView에 설정
     * parameter
     * int Number
     * String name
     * String image
     * String time
     * String contents
     */
    private void getCommunityDetail() {

        Intent it = getIntent();
        number = it.getExtras().getInt("Number");
        Log.e("number", number + "");
        content.setText(it.getExtras().getString("contents"));
        userName.setText(it.getExtras().getString("name"));
        txtTime.setText(it.getExtras().getString("time"));
        new DownloadImageTask(userImg).execute(it.getExtras().getString("image"));

        adapter.notifyDataSetChanged();
    }

    /**
     * 커뮤니티 댓글부분
     * 댓글 작성한 닉네임,내용,작성자 이미지 가져오기
     * 가져온 내용을 리스트에 추가
     **/
    private void getCommunityReply() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        final Call<CommunityDetailBean> call = connectService.get(number + "");

        call.enqueue(new Callback<CommunityDetailBean>() {
            @Override
            public void onResponse(Call<CommunityDetailBean> call, Response<CommunityDetailBean> response) {
                Log.e("response", response.code() + "");
                Log.e("selectnumber", number + "");

                //CommunityBeanDetail로 디코딩
                CommunityDetailBean decode = response.body();
                Log.e("replycount", decode.getCommunity_reply().size() + "");
                //선택한 커뮤니티의 댓글수를 TextView에 설정
                replyCount.setText(decode.getCommunity_reply().size() + "");

                listItem.clear();
                //listItem에 ReplyItem(댓글작성한닉네임,내용,작성자 이미지)를 추가
                for (int i = 0; i < decode.getCommunity_reply().size(); i++) {
                    try {
                        listItem.add(new ReplyItem(decode.getCommunity_reply().get(i).getName(), decode.getCommunity_reply().get(i).getReply_community_contents(), decode.getCommunity_reply().get(i).getImg(), TimeTransForm.formatTimeString(decode.getCommunity_reply().get(i).getReply_date())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommunityDetailBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    private void setCommunityReply() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        Map<String, String> data = new HashMap<>();
        Log.e("community_unique_key", number + "");
        data.put("user_unique_key", SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key"));
        data.put("community_unique_key", number + "");
        data.put("reply_community_contents", edtContents.getText().toString());

        Call<CommunityListBean> call = connectService.setCommunityReply(data);

        call.enqueue(new Callback<CommunityListBean>() {
            @Override
            public void onResponse(Call<CommunityListBean> call, Response<CommunityListBean> response) {
                Log.e("response", response.code() + "");
                Log.e("selectnumber", number + "");

                //CommunityBeanDetail로 디코딩
                CommunityListBean decode = response.body();
                Log.e("err", decode.getErr());
                if (decode.getErr().equals("0")) {
                    getCommunityReply();
                }
            }

            @Override
            public void onFailure(Call<CommunityListBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

}