package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.getAlarmStateBeanItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class getAlarmStateBean {
    int err;
    List<getAlarmStateBeanItem> set_alarm;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<getAlarmStateBeanItem> getSet_alarm() {
        return set_alarm;
    }

    public void setSet_alarm(List<getAlarmStateBeanItem> set_alarm) {
        this.set_alarm = set_alarm;
    }
}
