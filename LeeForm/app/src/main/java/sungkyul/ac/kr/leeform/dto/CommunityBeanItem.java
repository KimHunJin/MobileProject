package sungkyul.ac.kr.leeform.dto;

/**
 * Created by HunJin on 2016-05-30.
 */
public class CommunityBeanItem {
    String community_unique_key;
    String community_writing_name;
    String community_writing_contents;
    String user_unique_key;
    String community_writing_date;
    String community_picture_url;

    public String getCommunity_unique_key() {
        return community_unique_key;
    }

    public void setCommunity_unique_key(String community_unique_key) {
        this.community_unique_key = community_unique_key;
    }

    public String getCommunity_writing_name() {
        return community_writing_name;
    }

    public void setCommunity_writing_name(String community_writing_name) {
        this.community_writing_name = community_writing_name;
    }

    public String getCommunity_writing_contents() {
        return community_writing_contents;
    }

    public void setCommunity_writing_contents(String community_writing_contents) {
        this.community_writing_contents = community_writing_contents;
    }

    public String getUser_unique_key() {
        return user_unique_key;
    }

    public void setUser_unique_key(String user_unique_key) {
        this.user_unique_key = user_unique_key;
    }

    public String getCommunity_writing_date() {
        return community_writing_date;
    }

    public void setCommunity_writing_date(String community_writing_date) {
        this.community_writing_date = community_writing_date;
    }

    public String getCommunity_picture_url() {
        return community_picture_url;
    }

    public void setCommunity_picture_url(String community_picture_url) {
        this.community_picture_url = community_picture_url;
    }
}
