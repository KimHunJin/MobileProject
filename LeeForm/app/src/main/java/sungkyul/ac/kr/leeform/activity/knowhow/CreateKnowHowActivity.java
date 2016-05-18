package sungkyul.ac.kr.leeform.activity.knowhow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sungkyul.ac.kr.leeform.R;

public class CreateKnowHowActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlusKnowHow, btnMinusKnowHow;
    LinearLayout lineCreateView;
    LinearLayout linePlusView[];
    RelativeLayout relPlusView[];
    ImageView imgPlus[], imgAdd[];
    EditText edtContents[];
    LinearLayout lineAddImg[];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_know_how);

        btnPlusKnowHow = (Button) findViewById(R.id.btnContentsPlus);
        btnMinusKnowHow = (Button) findViewById(R.id.btnContentsMinus);
        lineCreateView = (LinearLayout) findViewById(R.id.lineCreateView);

        linePlusView = new LinearLayout[100];
        relPlusView = new RelativeLayout[100];
        imgPlus = new ImageView[100];
        imgAdd = new ImageView[100];
        edtContents = new EditText[100];
        lineAddImg = new LinearLayout[100];

        btnPlusKnowHow.setOnClickListener(this);
        btnMinusKnowHow.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContentsPlus: {
                Log.e("count", count + "");
                DisplayMetrics dm = getResources().getDisplayMetrics();
                if (count < 100) {
                    // 리니어 레이아웃 크기 조정
                    int size = Math.round(4 * dm.density);
                    LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size * 20);
                    mainParams.setMargins(size, size, size, size);

                    Bitmap bmA = BitmapFactory.decodeResource(getResources(), R.drawable.tables2);
                    Bitmap bmB = BitmapFactory.decodeResource(getResources(), R.drawable.ic_collections_white_18dp);
                    Bitmap bmC = BitmapFactory.decodeResource(getResources(), R.drawable.retangle_box);

                    // horizontal 형식의 리니어 레이아웃 생성
                    linePlusView[count] = new LinearLayout(this);
                    linePlusView[count].setOrientation(LinearLayout.HORIZONTAL);

                    // 이미지를 위한 릴레이티브 레이아웃 (이미지 안에 이미지)
                    relPlusView[count] = new RelativeLayout(this); // 릴레이티브 레이아웃 생성
                    imgPlus[count] = new ImageView(this); // 이미지 뷰 생성 (꽉차야 하는 이미지)
                    imgPlus[count].setImageBitmap(bmA);
//                    imgPlus[count].setImageDrawable(getDrawable(R.drawable.tables2));
                    imgPlus[count].setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃에 꽉차게 함

                    // 사진을 얻기 위한 작은 이미지
                    imgAdd[count] = new ImageView(this); // 이미지 뷰 생성 (작은 이미지)
                    imgAdd[count].setImageBitmap(bmB);
//                    imgAdd[count].setImageDrawable(getDrawable(R.drawable.ic_collections_white_18dp));

                    // 작은 이미지가 항상 보이게 하기위한 리니어 레이아웃 설정
                    lineAddImg[count] = new LinearLayout(this);
                    lineAddImg[count].setBackgroundColor(getResources().getColor(R.color.colorRelativeDark)); // 배경색을 반투명하여 넣음
                    lineAddImg[count].addView(imgAdd[count], LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams relParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // 이미지 크기 조정
                    relParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); // 이미지 위치 조정 (아래쪽)
                    relParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); // 이미지 위치 조정 (오른쪽)

                    // 이미지를 위한 릴레이티브 레이아웃에 두 이미지 삽입
                    relPlusView[count].addView(imgPlus[count], RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); // 큰 이미지 삽입과 크기 조정
                    relPlusView[count].addView(lineAddImg[count], relParam);
                    LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.4f); // 4:6으로 최상위 리니어 레이아웃에 삽입을 하기 위한 파람 생성
                    imgParams.setMargins(size, 0, size, 0); // 마진 값 생성
                    LinearLayout.LayoutParams edtParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.6f); // 4:6으로 최상위 리니어 레이아웃에 삽입을 하기 위한 파람 생성


                    // 에디트 텍스트 생성
                    edtContents[count] = new EditText(this);
                    edtContents[count].setHint("내용");
                    edtContents[count].setHintTextColor(getResources().getColor(R.color.colorGray));
                    edtContents[count].setGravity(Gravity.TOP);
                    edtContents[count].setPadding(size, size, size, size);
                    edtContents[count].setTextColor(getResources().getColor(R.color.colorBlack));
                    edtContents[count].setBackgroundResource(R.drawable.retangle_box);

                    // 가로 리니어 레이아웃에 이미지를 위한 릴레이티브 레이아웃과 에디트텍스트 삽입
                    linePlusView[count].addView(relPlusView[count], imgParams);
                    linePlusView[count].addView(edtContents[count], edtParams);


                    // 동적으로 추가가 될 리니어 레이아웃에 만들어진 가로 리니어 레이아웃 삽입
                    lineCreateView.addView(linePlusView[count], mainParams);
                    count++; // count를 증가시킴으로서 아이디 값 재 설정
                }
                break;
            }
            case R.id.btnContentsMinus: {
                
                break;
            }
        }
    }


}
