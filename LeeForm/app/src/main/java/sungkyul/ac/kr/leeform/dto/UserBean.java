package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.UserBeanItem;

/**
 * Created by user on 2016-06-11.
 */
public class UserBean {
    String err;
    List<UserBeanItem> myinfo_detail;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<UserBeanItem> getMyinfo_detail() {
        return myinfo_detail;
    }

    public void setMyinfo_detail(List<UserBeanItem> myinfo_detail) {
        this.myinfo_detail = myinfo_detail;
    }
}
