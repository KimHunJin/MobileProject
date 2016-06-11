package sungkyul.ac.kr.leeform.activity.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityDetailBean;
import sungkyul.ac.kr.leeform.items.ReplyItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.TimeTransForm;

/**
 * Created by user on 2016-06-10.
 */
public class MyPageModifyActivity extends AppCompatActivity{
    EditText edtUserName,edtAddress,edtBankName,edtBankNumber,edtPhoneNumber;
    String URL = StaticURL.BASE_URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypagemodify);

        layoutSetting();
    }

    private void layoutSetting() {
        edtUserName=(EditText)findViewById(R.id.edtUserName);
        edtAddress=(EditText)findViewById(R.id.edtAddress);
        edtBankName=(EditText)findViewById(R.id.edtBankName);
        edtBankNumber=(EditText)findViewById(R.id.edtBankNumber);
        edtPhoneNumber=(EditText)findViewById(R.id.edtPhoneNumber);
    }

    //정보수정하는거추가
   /* private void setUserInformation(){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ConnectService connectService = retrofit.create(ConnectService.class);

            final Call<CommunityDetailBean> call = connectService.get();

            call.enqueue(new Callback<CommunityDetailBean>() {
                @Override
                public void onResponse(Call<CommunityDetailBean> call, Response<CommunityDetailBean> response) {

                }

                @Override
                public void onFailure(Call<CommunityDetailBean> call, Throwable t) {
                    Log.e("failure", t.getMessage());
                }
            });
        }
*/

}
