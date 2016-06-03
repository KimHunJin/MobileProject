package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-06-01.
 */
public class KnowHowWritingBean {
    String err;
    List<KnowHowWritingBeanItem> writing_unique_key;

    public List<KnowHowWritingBeanItem> getWriting_unique_key() {
        return writing_unique_key;
    }

    public void setWriting_unique_key(List<KnowHowWritingBeanItem> writing_unique_key) {
        this.writing_unique_key = writing_unique_key;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
