package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.UserInfoBeanItem;

/**
 * Created by HunJin on 2016-06-01.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
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
