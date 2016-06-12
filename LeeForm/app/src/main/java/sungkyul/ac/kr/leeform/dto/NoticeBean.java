package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.NoticeBeanItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class NoticeBean {
    int err;
    List<NoticeBeanItem> notice_list;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<NoticeBeanItem> getNotice_list() {
        return notice_list;
    }

    public void setNotice_list(List<NoticeBeanItem> notice_list) {
        this.notice_list = notice_list;
    }
}
