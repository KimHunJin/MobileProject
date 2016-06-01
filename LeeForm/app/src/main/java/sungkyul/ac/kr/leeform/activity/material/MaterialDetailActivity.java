package sungkyul.ac.kr.leeform.activity.material;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hkm.slider.Animations.DescriptionAnimation;
import com.hkm.slider.SliderLayout;
import com.hkm.slider.SliderTypes.BaseSliderView;
import com.hkm.slider.SliderTypes.TextSliderView;
import com.hkm.slider.TransformerL;
import com.hkm.slider.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.utils.DataProvider;
import sungkyul.ac.kr.leeform.utils.NumZero;

/**
 * Created by Kim on 2016-05-22.
 */
public class MaterialDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    DataProvider dataProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

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

        initializeLayout();
        setupSlider();  // 이미지 슬라이드를 불러옵니다.
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout(){
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        dataProvider = new DataProvider();

        getMaterialImageUrl();
    }

    /**
     * setHashFile(String, String) : 파일을 URL로 가져와 저장합니다.
     * setHashFile(String, Integer) : 파일을 src로 가져와 저장합니다.
     */
    private void getMaterialImageUrl() {
        // dataProvider에 이미지를 저장합니다.

        Log.e("getMaterial","getMaterial");
        for(int i=0;i<4;i++) {
            dataProvider.setHashFile((3-i)+1+")",R.drawable.panza);
        }
    }

    // 이미지를 URL로 가져올 땐 이 부분을 사용합니다.
    // 이미지를 src로 가져오기 위해서는 이 부분을 주석처리 하면 됩니다.
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    protected void defaultCompleteSlider(final HashMap<String, String> maps) {
//        for (String name : maps.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(maps.getCommunityList(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .enableSaveImageByLongClick(getFragmentManager())
//                    .setOnSliderClickListener(this);
//            //add your extra information
//            textSliderView.getBundle().putString("extra", name);
//            mDemoSlider.addSlider(textSliderView);
//        }
//    }

    // 이미지를 src로 가져올 땐 이 부분을 사용합니다.
    // URL로 가져오기 위해서는 이 부분을 주석처리 하면 됩니다.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void defaultCompleteSlider(final HashMap<String, Integer> maps) {
        for (String name : maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .enableSaveImageByLongClick(getFragmentManager())
                    .setOnSliderClickListener(this);
            //add your extra information
            textSliderView.getBundle().putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }
    }


    /**
     * 슬라이드를 통해 이미지를 처리하는 매서드입니다.
     */
    @SuppressLint("ResourceAsColor")
    private void setupSlider() {
        // remember setup first
        Log.e("setup","setup slider");
        mDemoSlider.setPresetTransformer(TransformerL.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setOffscreenPageLimit(4);
        mDemoSlider.setSliderTransformDuration(400, new LinearOutSlowInInterpolator());
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.colorAccent, R.color.colorAccentBasic);
        final NumZero n = new NumZero(this);
        mDemoSlider.setNumLayout(n);
//        mDemoSlider.presentation(SliderLayout.PresentationConfig.Numbers);
        mDemoSlider.setPresetTransformer("Accordion");
        mDemoSlider.stopAutoCycle();

        //and data second. it is a must because you will except the data to be streamed into the pipline.
//        defaultCompleteSlider(DataProvider.getFileSrcHorizontal());
//       defaultCompleteSlider(dataProvider.getFileSrcHorizontal());
        defaultCompleteSlider(dataProvider.getFileSrc());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView coreSlider) {
        Toast.makeText(this, coreSlider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDemoSlider.stopAutoCycle();
    }

}