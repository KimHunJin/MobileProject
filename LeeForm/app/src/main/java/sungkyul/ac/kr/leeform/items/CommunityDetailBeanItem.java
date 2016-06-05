package sungkyul.ac.kr.leeform.items;

/**
 * Created by MiSeon on 2016-05-30.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityDetailBeanItem {
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

}
