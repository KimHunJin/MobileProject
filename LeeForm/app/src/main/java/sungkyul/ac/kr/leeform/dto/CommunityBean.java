package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-05-30.
 */
public class CommunityBean {
    private String err;
    private String count;
    private List<CommunityBeanItem> community_list;

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

    public List<CommunityBeanItem> getCommunity_list() {
        return community_list;
    }

    public void setCommunity_list(List<CommunityBeanItem> community_list) {
        this.community_list = community_list;
    }
}
