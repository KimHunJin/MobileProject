package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by user on 2016-06-03.
 * json으로 파싱 된 값을 가져오는 클래스
 */
public class CommunityBeanList {
    private String err;
    private String count;
    private List<CommunityBeanListItem> community_list;

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

    public List<CommunityBeanListItem> getCommunity_list() {
        return community_list;
    }

    public void setCommunity_list(List<CommunityBeanListItem> community_list) {
        this.community_list = community_list;
    }
}
