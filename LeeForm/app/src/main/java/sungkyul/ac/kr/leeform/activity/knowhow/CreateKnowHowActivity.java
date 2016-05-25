package sungkyul.ac.kr.leeform.activity.knowhow;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CreateKnowHowGridAdapter;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.items.CreateKnowHowItem;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

public class CreateKnowHowActivity extends AppCompatActivity {

    Button btnPlusKnowHow, btnMinusKnowHow;
    LinearLayout lineCreateView;
    LinearLayout linePlusView[];
    RelativeLayout relPlusView[];
    ImageView imgPlus[], imgAdd[];
    EditText edtContents[];
    LinearLayout lineAddImg[];
    int count = 0;

    private GridView grvCreate;
    private CreateKnowHowGridAdapter cAdapter;

    private ArrayList<String> strUrl = new ArrayList<>();
    private ArrayList<String> strExplain = new ArrayList<>();

    ArrayList<CreateKnowHowItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_know_how);

        //툴바 텍스트 변경
        TextView tv=(TextView)findViewById(R.id.txtToolBarTitle);
        tv.setText("노하우 작성");

        //뒤로가기 버튼
        ImageView imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        btnPlusKnowHow = (Button) findViewById(R.id.btnContentsPlus);
//      btnMinusKnowHow = (Button) findViewById(R.id.btnContentsMinus);
        lineCreateView = (LinearLayout) findViewById(R.id.lineCreateView);

        linePlusView = new LinearLayout[100];
        relPlusView = new RelativeLayout[100];
        imgPlus = new ImageView[100];
        imgAdd = new ImageView[100];
        edtContents = new EditText[100];
        lineAddImg = new LinearLayout[100];

        grvCreate = (GridView)findViewById(R.id.grvCreateView);
        cAdapter = new CreateKnowHowGridAdapter(getApplicationContext(),R.layout.item_grid_create,gridItems);
        grvCreate.setAdapter(cAdapter);

        init();

        grvCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == gridItems.size()-1) {
                    Intent it = new Intent(getApplicationContext(),CreateKnowHowExplainActivity.class);
                    it.putExtra("image",strUrl);
                    it.putExtra("explain",strExplain);
                    startActivityForResult(it,1000);
                }
            }
        });
    }

    void init() {
        gridItems.clear();
        for(int i=0;i<strUrl.size();i++) {
            gridItems.add(new CreateKnowHowItem(i,strUrl.get(i),strExplain.get(i)));
        }
        gridItems.add(new CreateKnowHowItem(gridItems.size(),R.drawable.ic_control_point_black_36dp,"클릭"));
        cAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if(resultCode == RESULT_OK) {
                strUrl = data.getStringArrayListExtra("image");
                strExplain = data.getStringArrayListExtra("explain");
                init();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
