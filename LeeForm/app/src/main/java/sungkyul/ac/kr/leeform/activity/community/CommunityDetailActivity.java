package sungkyul.ac.kr.leeform.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityReplyLIstAdapter;
import sungkyul.ac.kr.leeform.items.ReplyItem;

/**
 * Created by user on 2016-05-17.
 */
public class CommunityDetailActivity extends AppCompatActivity {

    private CommunityReplyLIstAdapter adapter;
    ArrayList<ReplyItem> listItem = new ArrayList<>();
    TextView tv1, tv2, tv3;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        Intent intent = getIntent(); //인텐트 값 가져오기

        String content = intent.getStringExtra("Content");
        String count = intent.getStringExtra("Count");
        String name = intent.getStringExtra("Name");
        int image = intent.getIntExtra("Image", 0);

        //item_list_community.xml 가져오기
        final View header = getLayoutInflater().inflate(R.layout.item_list_community, null, false);

        adapter = new CommunityReplyLIstAdapter(getApplicationContext(), R.layout.item_list_reply, listItem);

        ListView lst = (ListView) findViewById(R.id.replyList);
        lst.setAdapter(adapter);

        //리스트 윗부분에 넣기
        lst.addHeaderView(header);



        /***
         * 클릭한 커뮤니티 리스트의 내용, 댓글수, 작성자, 이미지 가져오기
         * */
        tv1 = (TextView) header.findViewById(R.id.contentCommunity);
        tv1.setText(content);
        tv2 = (TextView) header.findViewById(R.id.replyCount);
        tv2.setText(count);
        tv3 = (TextView) header.findViewById(R.id.userName);
        tv3.setText(name);
        img = (ImageView) header.findViewById(R.id.img);
        img.setImageResource(image);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), (position + 1) + "댓글 선택", Toast.LENGTH_SHORT).show();
            }
        });

        init();

    }

    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new ReplyItem("박현경2", "예쁘네여", R.drawable.circle_img)); //리스트에 추가
        }
    }
}