package sungkyul.ac.kr.leeform.activity.knowhow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by YongHoon on 2016-05-18.
 * 노하우 상세정보
 */
public class KnowHowDetailActivity extends AppCompatActivity {
    View layKnowhowDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowhow_detail);

        //툴바 완료버튼 보이지 않게 하기
//        TextView tvOK = (TextView) findViewById(R.id.imgOk);
//        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeLayout();


        for (int i = 0; i < 13; i++) {
            makeKnowhowItem(i + 1, "설명입니다 ㅎㅎㅎ " + (i + 1) + "번째.", null);
        }
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

        TextView tv1 = (TextView) itemView.findViewById(R.id.tvKnowhowDetailExplain);
        tv1.setText(position + ". " + explain);

        LinearLayout layKnowhowDetailItem = (LinearLayout) itemView.findViewById(R.id.layKnowhowDetailItem);
        ((LinearLayout) layKnowhowDetail).addView(layKnowhowDetailItem);
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout() {
        layKnowhowDetail = findViewById(R.id.layKnowhowDetail);
    }
}
