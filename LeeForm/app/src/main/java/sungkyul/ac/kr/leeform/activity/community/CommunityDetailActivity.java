package sungkyul.ac.kr.leeform.activity.community;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityReplyLIstAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityBean;
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.items.ReplyItem;

/**
 * Created by user on 2016-05-17.
 */
public class CommunityDetailActivity extends AppCompatActivity {

    private CommunityReplyLIstAdapter adapter;
    ArrayList<ReplyItem> listItem = new ArrayList<>();
    private static String URL = "http://14.63.196.255/api/";
    int number, count;
    TextView tv1, tv2, tv3;
    ImageView img;
    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        //툴바 완료버튼 보이지 않게 하기
        TextView tvOK=(TextView)findViewById(R.id.tvOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       /* Intent intent = getIntent(); //인텐트 값 가져오기

         number=intent.getExtras().getInt("Number");*/

        /*String content = intent.getStringExtra("Content");
        String count = intent.getStringExtra("Count");
        String name = intent.getStringExtra("Name");
        int image = intent.getIntExtra("Image", 0);*/

        //item_list_community.xml 가져오기
        final View header = getLayoutInflater().inflate(R.layout.item_list_community, null, false);

        adapter = new CommunityReplyLIstAdapter(getApplicationContext(), R.layout.item_list_reply, listItem);

        lst = (ListView) findViewById(R.id.replyList);
        lst.setAdapter(adapter);

        //리스트 윗부분에 넣기
        lst.addHeaderView(header);
        tv1 = (TextView) header.findViewById(R.id.contentCommunity);
        tv2 = (TextView)findViewById(R.id.replyCount);
        tv3 = (TextView) header.findViewById(R.id.userName);
        img = (ImageView) header.findViewById(R.id.img);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(getApplicationContext(), (position + 1) + "댓글 선택", Toast.LENGTH_SHORT).show();*/
            }
        });

        //init();

        communityDetail();

        communityReply();
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new ReplyItem("박현경2", "예쁘네여", R.drawable.circle_img)); //리스트에 추가
        }
    }
 //커뮤니티 디테일부분(리스트에서 선택한 작성자이름,내용 가져오기
    private void communityDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent(); //인텐트 값 가져오기
        number=intent.getExtras().getInt("Number");
        Log.e("num",number+"");

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<CommunityBean> call = connectService.getCommunityList();
        call.enqueue(new Callback<CommunityBean>() {
            @Override
            public void onResponse(Call<CommunityBean> call, Response<CommunityBean> response) {
                Log.e("response", response.code() + "");
                CommunityBean decode = response.body();
                Log.e("err",decode.getErr());
                Log.e("count",decode.getCount());

               tv1.setText(decode.getCommunity_list().get(number-1).getCommunity_writing_contents()); //내용
             //  tv2.setText("5"); //댓글카운트
                tv3.setText(decode.getCommunity_list().get(number-1).getCommunity_writing_name()); //작성자 이름
               // Log.e("url",decode.getCommunity_list().get(number-1).getCommunity_picture_url()+"");
                //img.setImageResource(decode.getCommunity_list().get(0).getCommunity_picture_url());
                //decode.getCommunity_list().get(0).getCommunity_writing_date();


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommunityBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
    //댓글 가져오기
    private void communityReply(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService= retrofit.create(ConnectService.class);

        final Call<CommunityBean> call= connectService.get(number+"");
        call.enqueue(new Callback<CommunityBean>() {
            @Override
            public void onResponse(Call<CommunityBean> call, Response<CommunityBean> response) {
                Log.e("response", response.code() + "");
                CommunityBean decode=response.body();
                count=decode.getCommunity_reply().size();

                tv2.setText(count+"");
                for(int i=0;i<decode.getCommunity_reply().size();i++){
                 listItem.add(new ReplyItem(decode.getCommunity_reply().get(i).getNickname(),decode.getCommunity_reply().get(i).getReply_writing_contents(),R.drawable.circle_img));
                }
            }

            @Override
            public void onFailure(Call<CommunityBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}