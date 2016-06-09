package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.KnowHowDetailBeanContentsItem;
import sungkyul.ac.kr.leeform.items.KnowHowDetailBeanPictureItem;

/**
 * Created by HunJin on 2016-06-09.
 */
public class KnowHowDetailBean {
    String err;
    List<KnowHowDetailBeanContentsItem> writing_data1;
    List<KnowHowDetailBeanPictureItem> writing_data2;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<KnowHowDetailBeanContentsItem> getWriting_data1() {
        return writing_data1;
    }

    public void setWriting_data1(List<KnowHowDetailBeanContentsItem> writing_data1) {
        this.writing_data1 = writing_data1;
    }

    public List<KnowHowDetailBeanPictureItem> getWriting_data2() {
        return writing_data2;
    }

    public void setWriting_data2(List<KnowHowDetailBeanPictureItem> writing_data2) {
        this.writing_data2 = writing_data2;
    }
}
