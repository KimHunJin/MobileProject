package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-05-31.
 */
public class WritingBean {
    String err;
    String count;
    List<WritingBeanItem> writing_list;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<WritingBeanItem> getWriting_list() {
        return writing_list;
    }

    public void setWriting_list(List<WritingBeanItem> writing_list) {
        this.writing_list = writing_list;
    }
}