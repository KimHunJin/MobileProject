package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.CommunityListBeanItem;

/**
 * Created by MiSeon on 2016-05-26.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityListBean {
    private String err;
    private String count;
    private List<CommunityListBeanItem> community_list;

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

    public List<CommunityListBeanItem> getCommunity_list() {
        return community_list;
    }

    public void setCommunity_list(List<CommunityListBeanItem> community_list) {
        this.community_list = community_list;
    }
}
