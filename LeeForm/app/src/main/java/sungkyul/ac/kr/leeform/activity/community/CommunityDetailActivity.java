package sungkyul.ac.kr.leeform.activity.community;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityReplyLIstAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityBeanDetail;
import sungkyul.ac.kr.leeform.dto.CommunityBeanList;
import sungkyul.ac.kr.leeform.items.ReplyItem;

/**
 * Created by miseon on 2016-05-17.
 * 커뮤니티 디테일
 * 커뮤니티 리스트에서 선택한 커뮤니티와 댓글을 나타낸다.
 */
public class CommunityDetailActivity extends AppCompatActivity {

    private CommunityReplyLIstAdapter adapter;
    ArrayList<ReplyItem> listItem = new ArrayList<>();
    private static String URL = "http://14.63.196.255/api/";
    int number;
    TextView content, replyCount, userName;
    ImageView userImg;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        //툴바 완료버튼 보이지 않게 하기
        TextView tvOK = (TextView) findViewById(R.id.tvOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //inflater를 통해 item_list_community.xml을 가져온다.
        final View header = getLayoutInflater().inflate(R.layout.item_list_community, null, false);
        //adapter를 통해 item.list.reply.xml을 ArrayList(listItem)에 설정한다.
        adapter = new CommunityReplyLIstAdapter(getApplicationContext(), R.layout.item_list_reply, listItem);
        lst = (ListView) findViewById(R.id.replyList);
        //lst에 adapter를 등록한다.
        lst.setAdapter(adapter);

        //리스트 윗부분에 넣기
        lst.addHeaderView(header);

        content = (TextView) header.findViewById(R.id.contentCommunity);
        replyCount = (TextView) findViewById(R.id.replyCount);
        userName = (TextView) header.findViewById(R.id.userName);
        userImg = (ImageView) header.findViewById(R.id.img);

        layoutSetting();
        communityDetail();
        communityReply();
    }

    private void layoutSetting() {

    }

    /**
     * 커뮤니티 디테일부분
     * 선택한 커뮤니티의 작성자이름,내용,이미지 가져오는 부분
     * 가져온 내용을 TextView나 ImageView에 설정
     * parameter
     * int Number
     */
    private void communityDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //CommunityFragment에서 보낸 Intent값 가져오기
        final Intent intent = getIntent();
        number = intent.getExtras().getInt("Number");
        Log.e("num", number + ""); //선택한 커뮤니티 넘버

        ConnectService connectService = retrofit.create(ConnectService.class);
        //http://14.63.196.255/api/community_list.php?
        Call<CommunityBeanList> call = connectService.getCommunityList();
        call.enqueue(new Callback<CommunityBeanList>() {
            @Override
            public void onResponse(Call<CommunityBeanList> call, Response<CommunityBeanList> response) {
                Log.e("response", response.code() + "");
                //CommunityBeanList 형식으로 디코딩
                CommunityBeanList decode = response.body();
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("urllll", decode.getCommunity_list().get(number).getImg()+"");
                //선택한 커뮤니티 내용을 텍스트뷰에 설정
                content.setText(decode.getCommunity_list().get(number).getCommunity_writing_contents());
                //선택한 커뮤니티 작성자 이름을 텍스트뷰에 설정
                userName.setText(decode.getCommunity_list().get(number).getName());
                //가져온 이미지를 이미지뷰에 설정
                new DownloadImageTask(userImg)
                        .execute(decode.getCommunity_list().get(number).getImg());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommunityBeanList> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    /**
     * 커뮤니티 댓글부분
     * 댓글 작성한 닉네임,내용,작성자 이미지 가져오기
     * 가져온 내용을 리스트에 추가
     **/
    private void communityReply() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        // http://14.63.196.255/api/community_detail.php?community_unique_key=number
        final Call<CommunityBeanDetail> call = connectService.get(number + 1 + "");

        call.enqueue(new Callback<CommunityBeanDetail>() {
            @Override
            public void onResponse(Call<CommunityBeanDetail> call, Response<CommunityBeanDetail> response) {
                Log.e("response", response.code() + "");
                Log.e("selectnumber", number + "");

                //CommunityBeanDetail로 디코딩
                CommunityBeanDetail decode = response.body();
                //선택한 커뮤니티의 댓글수를 TextView에 설정
                replyCount.setText(decode.getCommunity_reply().size()+"");

                //listItem에 ReplyItem(댓글작성한닉네임,내용,작성자 이미지)를 추가
                for (int i = 0; i < decode.getCommunity_reply().size(); i++) {
                    listItem.add(new ReplyItem(decode.getCommunity_reply().get(i).getName(), decode.getCommunity_reply().get(i).getReply_writing_contents(),decode.getCommunity_reply().get(i).getImg()));

                }
            }

            @Override
            public void onFailure(Call<CommunityBeanDetail> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
    // 비동기식으로 이미지를 가지고 온다.
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}