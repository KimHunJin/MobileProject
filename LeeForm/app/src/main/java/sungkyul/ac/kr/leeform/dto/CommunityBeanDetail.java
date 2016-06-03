package sungkyul.ac.kr.leeform.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-05-30.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityBeanDetail {
    private String err;
    private String count;
    private List<CommunityBeanDetailItem> community_detail;
    private List<CommunityBeanReplyItem> community_reply;

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

    public List<CommunityBeanDetailItem> getCommunity_detail() {
        return community_detail;
    }

    public void setCommunity_detail(List<CommunityBeanDetailItem> community_detail) {
        this.community_detail = community_detail;
    }

    public List<CommunityBeanReplyItem> getCommunity_reply() {
        return community_reply;
    }

    public void setCommunity_reply(List<CommunityBeanReplyItem> community_reply) {
        this.community_reply = community_reply;
    }
}
