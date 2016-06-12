package sungkyul.ac.kr.leeform.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.navigation.MyPageModifyActivity;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.RegistBean;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.utils.SaveData;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by YongHoon on 2016-06-01.
 */
public class RegistSellerActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnRegistSeller;
    ImageView imgOk, imgBack;
    String URL = StaticURL.BASE_URL;
    String accountNumber;
    String accountName;
    String bankName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_seller);
        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0, 0);

        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgOk = (ImageView) findViewById(R.id.imgOk);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgOk.setVisibility(View.INVISIBLE);

        btnRegistSeller = (Button) findViewById(R.id.btnRegistSeller);

        btnRegistSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserDetails();
            }
        });
    }

    /**
     * 사용자가 은행명,계좌번호,계좌명을 적지 않았을 경우
     * MyPageModifyActivity.class 로 가서 작성하게 함
     * <p>
     * parameter
     * user_key
     */
    private void getUserDetails() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        final Call<UserBean> call = connectService.getUserDetail(key);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decodedResponse = response.body();
                accountNumber = decodedResponse.getMyinfo_detail().get(0).getAccount_number();
                bankName = decodedResponse.getMyinfo_detail().get(0).getBank_name();
                accountName = decodedResponse.getMyinfo_detail().get(0).getAccount_name();

                if(accountName == null | bankName == null | accountNumber == null){
                    Intent intent = new Intent(getApplicationContext(), MyPageModifyActivity.class);
                    startActivityForResult(intent, 3000);
                    return;
                }
                if (accountName.equals("") | bankName.equals("") | accountNumber.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), MyPageModifyActivity.class);
                    startActivityForResult(intent, 3000);
                    return;
                }

                setUserDetails();
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    /**
     * 사용자가 작성한 은행명,계좌번호,계좌명을 가져온다.
     * 가져온 값이 널이 아닌 경우일 때만 setUserDetails() 호출
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 3000) {
                bankName = data.getExtras().getString("bank_name");
                accountNumber = data.getExtras().getString("account_number");
                accountName = data.getExtras().getString("account_name");
                if (accountName.equals("") | bankName.equals("") | accountNumber.equals("")) {
                    Toast.makeText(getApplicationContext(), "널값저장", Toast.LENGTH_SHORT).show();
                } else {
                    setUserDetails();
                }
            }
        }
    }

    /**
     * 판매자등록을 한다.
     * <p>
     * parameter
     * key
     * accountName
     * accountNumber
     * bankName
     */
    private void setUserDetails() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        Map<String, String> data = new HashMap<>();

        data.put("user_unique_key", key);
        data.put("account_name", accountName);
        data.put("account_number", accountNumber);
        data.put("bank_name", bankName);

        final Call<RegistBean> call = connectService.setResigster(data);

        call.enqueue(new Callback<RegistBean>() {
            @Override
            public void onResponse(Call<RegistBean> call, Response<RegistBean> response) {
                RegistBean decodedResponse = response.body();
                SaveData.setAppPreferences(getApplicationContext(), "isAuthority","1");
                Toast.makeText(RegistSellerActivity.this, "등록되셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<RegistBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });

    }
}