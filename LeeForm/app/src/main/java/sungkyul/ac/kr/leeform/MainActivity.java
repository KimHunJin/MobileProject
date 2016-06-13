package sungkyul.ac.kr.leeform;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import sungkyul.ac.kr.leeform.activity.navigation.MyPageActivity;
import sungkyul.ac.kr.leeform.activity.navigation.PurchaseListActivity;
import sungkyul.ac.kr.leeform.activity.navigation.RegistSellerActivity;
import sungkyul.ac.kr.leeform.activity.navigation.SaleListActivity;
import sungkyul.ac.kr.leeform.activity.search.KnowHowSearchActivity;
import sungkyul.ac.kr.leeform.activity.search.MaterialSearchActivity;
import sungkyul.ac.kr.leeform.activity.settings.SettingActivity;
import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.dto.UserInfoBean;
import sungkyul.ac.kr.leeform.service.RegistrationIntentService;
import sungkyul.ac.kr.leeform.utils.BackPressCloseHandler;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;
import sungkyul.ac.kr.leeform.utils.QuickstartPreferences;
import sungkyul.ac.kr.leeform.utils.SaveData;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by HunJin on 2016-05-09.
 * <p>
 * 애플리케이션의 기본이 되는 메인 액티비티입니다.
 */
public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private static String URL = StaticURL.BASE_URL;

    private BackPressCloseHandler backPressCloseHandler;

    private SimpleSideDrawer slidingMenu;
    private TabLayout tabLayout;
    private ListView lstNavItem;
    private ImageView imgNavUser;
    private TextView txtNavUserNickName;

    private long userId;
    private String userUniqueKey;
    private String userNickName, userImagePath;
    private String userNickNameIn, userImagePathIn;

    private Toolbar toolbar;

    String[] item;

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
        LoadActivityList.actList.add(MainActivity.this);

        Intent it = getIntent();
        userId = it.getExtras().getLong("UserId");
        userNickName = it.getExtras().getString("NickName");
        userImagePath = it.getExtras().getString("Image");

        tabInitialization();
        initializeLayout();
        checkUser();
        setListener();
        getAuthority();
        gcm();
    }

    private void gcm() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.e("sentToken", R.string.gcm_send_message + "");
                } else {
                    Log.e("information", R.string.token_error_message + "");
                }
            }
        };
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    /**
     * 회원의 기본적인 정보를 저장하는 메서드입니다.
     * 첫 로그인일 경우 디비에 저장을 하고,
     * 이후에는 디비로부터 데이터를 가져옵니다.
     *
     * @param userId
     * @param userNickName
     * @param userImagePath
     */
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

    /**
     * 회원이 등록이 되어있는지 체크를 합니다.
     * 등록이 되어있을 경우 회원정보를 가져오며,
     * 등록이 되지 않은 경우 등록을 하는
     * setUser() 메서드를 호출합니다.
     */
    private void checkUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<UserInfoBean> call = connectService.getUserInfo(userId + "");
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                UserInfoBean decode = response.body();
                Log.e("err", decode.getErr());
                String err = decode.getErr();
                if (err.equals("0")) {
                    userUniqueKey = decode.getKakao_user_info().get(0).getUser_unique_key();
                    userNickNameIn = decode.getKakao_user_info().get(0).getName();
                    userImagePathIn = decode.getKakao_user_info().get(0).getImg();

                    SaveDataMemberInfo.setAppPreferences(getApplicationContext(), "user_key", userUniqueKey);
                    navigationSetting();
                } else if (err.equals("4")) {
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

        txtNavUserNickName.setText(userNickNameIn);
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
        slidingMenu = new SimpleSideDrawer(MainActivity.this);
        slidingMenu.setLeftBehindContentView(R.layout.nav_view);
        lstNavItem = (ListView) slidingMenu.findViewById(R.id.lstNavItem);

        // 취소버튼 눌렀을 때 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);

        // 네비게이션 정보 설정
        imgNavUser = (ImageView) findViewById(R.id.imgNavUser);
        txtNavUserNickName = (TextView) findViewById(R.id.txtNavUserNickName);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
    }

    /**
     * 리스너 설정
     */
    private void setListener() {
        ImageView imgNav = (ImageView) findViewById(R.id.imgNav);
        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggleLeftDrawer();
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

        item = getResources().getStringArray(R.array.nav);

        if (SaveData.getAppPreferences(getApplicationContext(), "isAuthority").equals("1")) {
            item[2] = "판매 내역";
        }else{
            item[2] = "판매자 등록";
        }

        lstNavItem = (ListView) slidingMenu.findViewById(R.id.lstNavItem);
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
                        if (SaveData.getAppPreferences(getApplicationContext(), "isAuthority").equals("1")) {
                            Intent itSetting = new Intent(getApplicationContext(), SaleListActivity.class);
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
                slidingMenu.closeLeftSide();
            }
        });
    }

    private void getAuthority() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String userKey = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        final Call<UserBean> call = connectService.getUserDetail(userKey);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {

                UserBean decode = response.body();
                String authority = decode.getMyinfo_detail().get(0).getAuthority().toString();
                SaveData.setAppPreferences(getApplicationContext(), "isAuthority",authority);
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }


    /**
     * 뒤로가기 키를 눌렀을 때
     */
    @Override
    public void onBackPressed() {
        // 네비게이션이 열려있으면
        if (!slidingMenu.isClosed()) {
            slidingMenu.closeLeftSide();
            return;
        }
        //핸들러 작동
        backPressCloseHandler.onBackPressed();
        Toast.makeText(getApplicationContext(), "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
    }

    /**
     * 탭 뷰 초기화
     */
    private void tabInitialization() {
        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        viewPager.setOffscreenPageLimit(2);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(mainFragmentAdapter);

        tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        setListener();
    }

}
