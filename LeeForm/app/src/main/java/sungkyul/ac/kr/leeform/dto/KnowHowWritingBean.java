package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.KnowHowWritingBeanItem;

/**
 * Created by HunJin on 2016-05-28.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
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
