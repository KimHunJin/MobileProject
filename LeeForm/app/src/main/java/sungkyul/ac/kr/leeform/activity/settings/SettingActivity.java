package sungkyul.ac.kr.leeform.activity.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.member.LoginActivity;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.AlarmCheckBean;
import sungkyul.ac.kr.leeform.dto.getAlarmStateBean;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * 설정
 * Created by MiSeon on 2016-06-02.
 */
public class SettingActivity extends PreferenceActivity {
    private Toolbar toolbar;
    CheckBoxPreference checkPush;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_preference_screen_layout);
        addPreferencesFromResource(R.xml.pref_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0, 0);
        //툴바 완료버튼 보이지 않게 하기
        ImageView imgOk = (ImageView) findViewById(R.id.imgOk);
        imgOk.setVisibility(View.INVISIBLE);

        TextView txtToolBarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        txtToolBarTitle.setText("설정");

        LoadActivityList.actList.add(SettingActivity.this);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBackOk);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkPush = (CheckBoxPreference) findPreference("useNotice");

        checkPush();

        checkPush.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                chacngePush();
                return true;
            }
        });

        Preference leaveLeeForm = findPreference("leaveLeeForm");
        leaveLeeForm.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                destory();
                return true;
            }
        });

        Preference notice = findPreference("notice");
        notice.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getApplicationContext(), NoticeActivity.class));
                return true;
            }
        });

        Preference aboutUs = findPreference("aboutUs");
        aboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                return true;
            }
        });

        Preference license = findPreference("license");
        license.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getApplicationContext(), LicenseActivity.class));
                return true;
            }
        });
    }

    private void redirectLoginActivity() {
        Intent it = new Intent(getApplicationContext(), LoginActivity.class);
        Log.e("redi Session ", Session.getCurrentSession() + "");
        startActivity(it);
        LoadActivityList loadActivityList = new LoadActivityList();
        loadActivityList.closeActivity();
    }

    private void destory() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }


    private void chacngePush() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<AlarmCheckBean> call = connectService.change(SaveDataMemberInfo.getAppPreferences(getApplicationContext(),"user_key"));
        call.enqueue(new Callback<AlarmCheckBean>() {
            @Override
            public void onResponse(Call<AlarmCheckBean> call, Response<AlarmCheckBean> response) {
                AlarmCheckBean decode = response.body();
                if(decode.getSet_alarm()==1) {
                    checkPush.setChecked(true);
                } else {
                    checkPush.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<AlarmCheckBean> call, Throwable t) {

            }
        });

    }

    private void checkPush() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<getAlarmStateBean> call = connectService.checkAlarmState(SaveDataMemberInfo.getAppPreferences(getApplicationContext(),"user_key"));
        call.enqueue(new Callback<getAlarmStateBean>() {
            @Override
            public void onResponse(Call<getAlarmStateBean> call, Response<getAlarmStateBean> response) {
                getAlarmStateBean decode = response.body();
                if(decode.getSet_alarm().get(0).getSet_alarm()==1) {
                    checkPush.setChecked(true);
                } else {
                    checkPush.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<getAlarmStateBean> call, Throwable t) {

            }
        });
    }
}
