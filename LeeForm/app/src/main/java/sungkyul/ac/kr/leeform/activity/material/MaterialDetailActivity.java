package sungkyul.ac.kr.leeform.activity.material;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.slider.Animations.DescriptionAnimation;
import com.hkm.slider.SliderLayout;
import com.hkm.slider.SliderTypes.BaseSliderView;
import com.hkm.slider.SliderTypes.TextSliderView;
import com.hkm.slider.TransformerL;
import com.hkm.slider.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.credit.DemoCreditPage;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.MaterialDetailBean;
import sungkyul.ac.kr.leeform.utils.DataProvider;
import sungkyul.ac.kr.leeform.utils.NumZeroForm;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by KyungHee on 2016-05-19.
 */
public class MaterialDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Toolbar toolbar;

    private SliderLayout mDemoSlider;
    DataProvider dataProvider;
    private Button btnBuying;
    String material_unique_key;
    private ImageView imgMaterialDetailSeller;
    private TextView txtMaterialDetailName, txtMaterialDetailExplain, txtMaterialDetailCost, txtToolBarTitle;
//    private TextView txtMaterialDetailSellerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        Intent it = getIntent();
        material_unique_key = it.getExtras().getString("material_unique_key");

        //툴바 완료버튼 보이지 않게 하기
        ImageView imgOk = (ImageView) findViewById(R.id.imgOk);
        imgOk.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeLayout();


        Log.e("material_unique_key", material_unique_key + "");
        getDetailInfo();
        btnBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DemoCreditPage.class));
            }
        });
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout() {

        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0, 0);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        dataProvider = new DataProvider();

        btnBuying = (Button) findViewById(R.id.btnMaterialDetailBuying);

        txtMaterialDetailName = (TextView) findViewById(R.id.txtMaterialDetailName);
        txtMaterialDetailCost = (TextView) findViewById(R.id.txtMaterialDetailCost);
        txtMaterialDetailExplain = (TextView) findViewById(R.id.txtMaterialDetailExplain);
//        txtMaterialDetailSellerName = (TextView) findViewById(R.id.txtMaterialDetailSellerName);
        txtToolBarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        imgMaterialDetailSeller = (ImageView)findViewById(R.id.imgMaterialDetailSeller);

    }

    /**
     * setHashFile(String, String) : 파일을 URL로 가져와 저장합니다.
     * setHashFile(String, Integer) : 파일을 src로 가져와 저장합니다.
     */
    private void getMaterialImageUrl() {
        // dataProvider에 이미지를 저장합니다.

        Log.e("getMaterial", "getMaterial");
        for (int i = 0; i < 4; i++) {
//            dataProvider.setHashFile((i + 1) + "", R.drawable.panza);
        }
    }

    // 이미지를 URL로 가져올 땐 이 부분을 사용합니다.
    // 이미지를 src로 가져오기 위해서는 이 부분을 주석처리 하면 됩니다.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void defaultCompleteSlider(final HashMap<String, String> maps) {
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

    // 이미지를 src로 가져올 땐 이 부분을 사용합니다.
    // URL로 가져오기 위해서는 이 부분을 주석처리 하면 됩니다.
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    protected void defaultCompleteSlider(final HashMap<String, Integer> maps) {
//        for (String name : maps.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .enableSaveImageByLongClick(getFragmentManager())
//                    .setOnSliderClickListener(this);
//            //add your extra information
//            textSliderView.getBundle().putString("extra", name);
//            mDemoSlider.addSlider(textSliderView);
//        }
//    }


    /**
     * 슬라이드를 통해 이미지를 처리하는 매서드입니다.
     */
    @SuppressLint("ResourceAsColor")
    private void setupSlider() {
        // remember setup first
        Log.e("setup", "setup slider");
        mDemoSlider.setPresetTransformer(TransformerL.DepthPage);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setOffscreenPageLimit(4);
        mDemoSlider.setSliderTransformDuration(400, new LinearOutSlowInInterpolator());
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.colorAccentBasic, R.color.colorAccent);
        final NumZeroForm n = new NumZeroForm(this);
        mDemoSlider.setNumLayout(n);
//        mDemoSlider.presentation(SliderLayout.PresentationConfig.Numbers);
        mDemoSlider.setPresetTransformer("DepthPage");
        mDemoSlider.stopAutoCycle();

        //and data second. it is a must because you will except the data to be streamed into the pipline.
//        defaultCompleteSlider(DataProvider.getFileSrcHorizontal());
//       defaultCompleteSlider(dataProvider.getFileSrcHorizontal());
        defaultCompleteSlider(dataProvider.getFileSrcHorizontal());
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
//        Toast.makeText(this, coreSlider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDemoSlider.stopAutoCycle();
    }

    private void getDetailInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<MaterialDetailBean> call = connectService.getMaterialDetail(material_unique_key);
        call.enqueue(new Callback<MaterialDetailBean>() {
            @Override
            public void onResponse(Call<MaterialDetailBean> call, Response<MaterialDetailBean> response) {
                MaterialDetailBean decode = response.body();
                txtMaterialDetailName.setText(decode.getMaterial_detail1().get(0).getMaterial_name().toString());
                txtToolBarTitle.setText(decode.getMaterial_detail1().get(0).getMaterial_name().toString());
//                txtMaterialDetailSellerName.setText(decode.getMaterial_detail1().get(0).getSubcontractor_name());
                txtMaterialDetailCost.setText(decode.getMaterial_detail1().get(0).getMaterial_price());
                txtMaterialDetailExplain.setText(decode.getMaterial_detail1().get(0).getMaterial_explanation());
                Picasso.with(getApplicationContext()).load(decode.getMaterial_detail1().get(0).getSubcontractor_image_url()).resize(100,45).centerCrop().into(imgMaterialDetailSeller);

                for (int i = 0; i < decode.getMaterial_detail2().size(); i++) {
                    Log.e("file url", decode.getMaterial_detail2().get(i).getMaterial_picture_url().toString());
                    dataProvider.setHashFile((i + 1) + "", decode.getMaterial_detail2().get(i).getMaterial_picture_url().toString());
                }

                setupSlider();  // 이미지 슬라이드를 불러옵니다.
            }

            @Override
            public void onFailure(Call<MaterialDetailBean> call, Throwable t) {

            }
        });
    }

}