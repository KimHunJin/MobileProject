package sungkyul.ac.kr.leeform.activity.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.member.LoginActivity;

/**
 * Created by Kim on 2016-05-22.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_preference_screen_layout);
        addPreferencesFromResource(R.xml.pref_settings);

        //툴바 완료버튼 보이지 않게 하기
        TextView tvOK = (TextView) findViewById(R.id.tvOk);
        tvOK.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
                return true;
            }
        });
    }

    private void redirectLoginActivity() {
        Intent it = new Intent(getApplicationContext(), LoginActivity.class);
        Log.e("redi Session ",Session.getCurrentSession()+"");
        startActivity(it);
        finish();
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
//                                            redirectSignupActivity();
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
}