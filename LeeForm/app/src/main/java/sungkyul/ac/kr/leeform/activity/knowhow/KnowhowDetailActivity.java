package sungkyul.ac.kr.leeform.activity.knowhow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by Kim on 2016-05-16.
 */
public class KnowhowDetailActivity extends AppCompatActivity{
    View layKnowhowDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowhow_detail);

        initializeLayout();


        for(int i=0; i<13; i++){
            makeKnowhowItem(i+1,"설명입니다 ㅎㅎㅎ "+(i+1)+"번째.",null);
        }
    }

    /**
     * 설명과 사진(동영상) 아이템을 넣는 메소드
     * @param position  몇번째 아이템인지 알려줌
     * @param explain   해당 아이템의 설명이 들어감
     * @param url       사진이나 동영상의 url이 들어감
     */
    private void makeKnowhowItem(int position, String explain, String url){
        View itemView;

        itemView = View.inflate(getApplicationContext(),R.layout.item_knowhow_detail,null);

        TextView tv1 = (TextView)itemView.findViewById(R.id.tvKnowhowDetailExplain);
        tv1.setText(position + ". " + explain);

        LinearLayout layKnowhowDetailItem = (LinearLayout)itemView.findViewById(R.id.layKnowhowDetailItem);
        ((LinearLayout)layKnowhowDetail).addView(layKnowhowDetailItem);
    }

    /**
     * xml 파일로부터 컴포넌트들의 아이디 가져오는 메소드
     */
    private void initializeLayout() {
        layKnowhowDetail = findViewById(R.id.layKnowhowDetail);
    }
}
