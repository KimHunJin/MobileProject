package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-06-01.
 */
public class UserInfoBean {
    String err;
    List<UserInfoBeanItem> kakao_user_info;

    public List<UserInfoBeanItem> getKakao_user_info() {
        return kakao_user_info;
    }

    public void setKakao_user_info(List<UserInfoBeanItem> kakao_user_info) {
        this.kakao_user_info = kakao_user_info;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}