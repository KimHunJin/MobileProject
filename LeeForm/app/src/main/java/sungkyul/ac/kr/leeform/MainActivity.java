package sungkyul.ac.kr.leeform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.activity.member.PurchaseListActivity;
import sungkyul.ac.kr.leeform.activity.member.RegistSellerActivity;
import sungkyul.ac.kr.leeform.activity.navigation.MyPageActivity;
import sungkyul.ac.kr.leeform.activity.search.KnowHowSearchActivity;
import sungkyul.ac.kr.leeform.activity.search.MaterialSearchActivity;
import sungkyul.ac.kr.leeform.activity.settings.SettingActivity;
import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.UserInfoBean;
import sungkyul.ac.kr.leeform.utils.BackPressCloseHandler;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;
import sungkyul.ac.kr.leeform.utils.SaveData;
import sungkyul.ac.kr.leeform.utils.Util;

public class MainActivity extends AppCompatActivity {

    private static String URL = "http://14.63.196.255/api/";

    private BackPressCloseHandler backPressCloseHandler;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private SimpleSideDrawer mSlidingMenu;
    private TabLayout tabLayout;
    private TabLayout.Tab tab;
    private ListView lstNavItem;

    private ImageView imgNavUser;
    private TextView txtNavUserNickName;

    private long userId;
    private String userUniqueKey;
    private String userNickName, userImagePath;
    private String userNickNameIn, userImagePathIn;
    private String errorCode = "-1";


    private LinearLayout lineMain;

    Handler handler = new Handler();

    /**
     * getIntent 값
     * UserId : 유저 식별 키
     * NickName : 카카오 사용자 닉네임
     * Image : 유저 썸네일 이미지
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그아웃 할 때 열려있는 액티비티 모두 닫기 위해 리스트에 저장
        new LoadActivityList().actList.add(MainActivity.this);

        Intent it = getIntent();
        userId = it.getExtras().getLong("UserId");
        userNickName = it.getExtras().getString("NickName");
        userImagePath = it.getExtras().getString("Image");


        Log.e("userIdMain", userId + " : " + userNickName + " : " + userImagePath);

        checkUser();


    }

    // 유저 등록 (미완성)
    private void setUser(final long userId, String userNickName, String userImagePath) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> data = new HashMap<>();
        data.put("kakao_unique_key", userId + "");
        data.put("img", userImagePath);
        data.put("name", userNickName);

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<UserInfoBean> call = connectService.setUserInfo(data);
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                Log.e("setUser", response.code() + "");
                checkUser();
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });

    }

    private void checkUser() {
        Log.e("userid", userId + "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.e("abcd", "abcd" + userNickName + " " + userImagePath);
        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<UserInfoBean> call = connectService.getUserInfo(userId + "");
        Log.e("call", "call");
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                Log.e("resonpse", response.code() + "");
                UserInfoBean decode = response.body();
                Log.e("err", decode.getErr());
                String err = decode.getErr();
                errorCode = err;
                if (err.equals("0")) {
                    Log.e("yse", "yes");
                    userUniqueKey = decode.getKakao_user_info().get(0).getUser_unique_key();
                    userNickNameIn = decode.getKakao_user_info().get(0).getName();
                    userImagePathIn = decode.getKakao_user_info().get(0).getImg();

                    Util.setAppPreferences(getApplicationContext(), "user_key", userUniqueKey);
                    Log.e("user_key", Util.getAppPreferences(getApplicationContext(), "user_key"));

                    tabInitialization();
                    initializeLayout();
                    setListener();
                    navigationSetting();

                } else if (err.equals("4")) {
                    Log.e("sibar", "sibar");
                    setUser(userId, userNickName, userImagePath);
                }

            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    /**
     * 네비게이션 세팅 (사용자 이미지, 닉네임)
     */
    private void navigationSetting() {
//        Intent it = getIntent();
//        userId = it.getExtras().getLong("UserId");
//        userNickName = it.getExtras().getString("NickName");
//        userImagePath = it.getExtras().getString("Image");
        Log.e("userUniqueKeyIn", userUniqueKey);
        Log.e("userNickNameIn", userNickNameIn);
        Log.e("userImagePathIn", userImagePathIn);

        txtNavUserNickName.setText(userNickNameIn);
        Log.e("userImagePath", userImagePathIn);
        if (userImagePath != null) {
            userImageSetting();
        }
    }

    /**
     * 스레드를 사용하여 이미지 가져오기
     */
    private void userImageSetting() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(userImagePath);
                    InputStream inputStream = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imgNavUser.setImageBitmap(bm);
                        }
                    });
                    imgNavUser.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mSlidingMenu = new SimpleSideDrawer(MainActivity.this);
        mSlidingMenu.setLeftBehindContentView(R.layout.nav_view);
        lstNavItem = (ListView) mSlidingMenu.findViewById(R.id.lstNavItem);

        // 취소버튼 눌렀을 때 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);

        // 네비게이션 정보 설정
        imgNavUser = (ImageView) findViewById(R.id.imgNavUser);
        txtNavUserNickName = (TextView) findViewById(R.id.txtNavUserNickName);
    }

    /**
     * 리스너 설정
     */
    private void setListener() {
        ImageView imgNav = (ImageView) findViewById(R.id.imgNav);
        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenu.toggleLeftDrawer();
            }
        });

        ImageView imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Intent it = new Intent(getApplicationContext(), KnowHowSearchActivity.class);
                    startActivity(it);
                    overridePendingTransition(R.anim.commons_slide_from_right, R.anim.commons_slide_to_left);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    startActivity(new Intent(getApplicationContext(), MaterialSearchActivity.class));
                    overridePendingTransition(R.anim.commons_slide_from_right, R.anim.commons_slide_to_left);
                } else {
                    Toast.makeText(getApplicationContext(), "커뮤니티에선 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] item = getResources().getStringArray(R.array.nav);

        if (SaveData.getAppPreferences(getApplicationContext(), "isSeller").equals("true")) {
            item[2] = "판매 내역";
        }

        lstNavItem = (ListView) mSlidingMenu.findViewById(R.id.lstNavItem);
        ArrayAdapter<String> yada = new ArrayAdapter<String>(this, R.layout.nav_item, item);
        lstNavItem.setAdapter(yada);

        lstNavItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    // 내 정보
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        break;

                    // 구매내역
                    case 1:
                        Intent itPurchaseList = new Intent(getApplicationContext(), PurchaseListActivity.class);
                        startActivity(itPurchaseList);
                        break;

                    // 판매자 등록
                    case 2:
                        // 판매자 등록이 된상태면 판매내역으로
                        if (SaveData.getAppPreferences(getApplicationContext(), "isSeller").equals("true")) {
                            Intent itSetting = new Intent(getApplicationContext(), SettingActivity.class);
                            startActivity(itSetting);
                        }
                        // 판매자 등록이 안되어 있으면 판매자 등록으로
                        else {
                            Intent itRegistSeller = new Intent(getApplicationContext(), RegistSellerActivity.class);
                            startActivity(itRegistSeller);
                        }
                        break;

                    // 설정
                    case 3:
                        Intent itSetting = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(itSetting);
                        break;
                }
                mSlidingMenu.closeLeftSide();
            }
        });
    }

    //취소버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        // 네비게이션이 열려있으면
        if (!mSlidingMenu.isClosed()) {
            mSlidingMenu.closeLeftSide();
            return;
        }
        //핸들러 작동
        backPressCloseHandler.onBackPressed();
        Toast.makeText(getApplicationContext(), "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
    }

    private void tabInitialization() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(mainFragmentAdapter);


        tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tab = tabLayout.getTabAt(i);
            tab.setCustomView(mainFragmentAdapter.getTabView(i));
        }
    }
}
