package sungkyul.ac.kr.leeform.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import sungkyul.ac.kr.leeform.MainActivity;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.navigation.MyPageModifyActivity;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.RegistBean;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;
import sungkyul.ac.kr.leeform.utils.SaveData;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by YongHoon on 2016-06-01.
 */
public class RegistSellerActivity extends AppCompatActivity {
    Button btnRegistSeller;
    ImageView imgOk,imgBack;
    String URL = StaticURL.BASE_URL;
    String accountNumber;
    String address;
    String bankName;
    String accountName,a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_seller);

        imgBack=(ImageView)findViewById(R.id.imgBackOk);
        imgOk=(ImageView)findViewById(R.id.imgOk);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRegistSeller = (Button) findViewById(R.id.btnRegistSeller);

        btnRegistSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   SaveData.setAppPreferences(getApplicationContext(), "isSeller", "true");
                LoadActivityList.actList.clear();
                Intent itMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(itMain);
                finish();*/
                getUserDetails();
            }
        });
    }
    private void getUserDetails(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");

        Map<String,String> data=new HashMap<>();
        data.put("user_unique_key",key);
        data.put("account_name",accountName);
        data.put("account_number",accountNumber);
        data.put("bank_name",bankName);


        final Call<UserBean> call = connectService.getUserDetail(key);
        final Call<RegistBean> call2 = connectService.setResigster(data);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decodedResponse = response.body();


                 accountNumber= decodedResponse.getMyinfo_detail().get(0).getAccount_number();
                 address=decodedResponse.getMyinfo_detail().get(0).getAddress();
                 bankName=decodedResponse.getMyinfo_detail().get(0).getBank_name();
                 accountName=decodedResponse.getMyinfo_detail().get(0).getAccount_name();

                if(accountNumber.equals("")|address.equals("")|bankName.equals("")|accountName.equals("")){
                  Intent intent=new Intent(getApplicationContext(), MyPageModifyActivity.class);
                   startActivityForResult(intent,2000);

                }else {
                    call2.enqueue(new Callback<RegistBean>() {
                        @Override
                        public void onResponse(Call<RegistBean> call, Response<RegistBean> response) {
                            RegistBean decode= response.body();
                            Log.e("rrrrrrrr",decode.getErr());
                        }

                        @Override
                        public void onFailure(Call<RegistBean> call, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            //액티비티가 정상적으로 종료
            if(requestCode==1){
                //호출된 액티비티에서 호출한 경우메나 처리
                //등록하기버튼
            }
        }
    }
    /* private void setRegister(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);

        Map<String,String> data=new HashMap<>();
        String user_key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        data.put("user_unique_key",user_key);

        final Call<UserBean> call = connectService.setResigster(data);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decodedResponse = response.body();
                String accountNumber= decodedResponse.getMyinfo_detail().get(0).getAccount_number();
                String address=decodedResponse.getMyinfo_detail().get(0).getAddress();
                String bankName=decodedResponse.getMyinfo_detail().get(0).getBank_name();
                String accountName=decodedResponse.getMyinfo_detail().get(0).getAccount_name();

                if(accountNumber.equals("")|address.equals("")|bankName.equals("")|accountName.equals("")){
                    Intent intent=new Intent(getApplicationContext(), MyPageModifyActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }*/

}
