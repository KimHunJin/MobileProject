package sungkyul.ac.kr.leeform.dto;

/**
 * Created by HunJin on 2016-05-30.
 */
public class CommunityBeanDetailItem {
    String community_unique_key;
    String user_nickname;
    String community_community_writing_contents;
    String user_unique_key;
    String community_community_writing_date;
    String user_img;
   //String community_picture_url;

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getCommunity_community_writing_contents() {
        return community_community_writing_contents;
    }

    public void setCommunity_community_writing_contents(String community_community_writing_contents) {
        this.community_community_writing_contents = community_community_writing_contents;
    }

    public String getCommunity_community_writing_date() {
        return community_community_writing_date;
    }

    public void setCommunity_community_writing_date(String community_community_writing_date) {
        this.community_community_writing_date = community_community_writing_date;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }



    /*public String getCommunity_unique_key() {
        return community_unique_key;
    }

    public void setCommunity_unique_key(String community_unique_key) {
        this.community_unique_key = community_unique_key;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
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
    }*/
}
