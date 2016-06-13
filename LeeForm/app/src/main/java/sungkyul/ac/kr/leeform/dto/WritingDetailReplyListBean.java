package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.WritingDetailReplyListBeanItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class WritingDetailReplyListBean {
    int err;
    List<WritingDetailReplyListBeanItem> reply_list;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<WritingDetailReplyListBeanItem> getReply_list() {
        return reply_list;
    }

    public void setReply_list(List<WritingDetailReplyListBeanItem> reply_list) {
        this.reply_list = reply_list;
    }
}
