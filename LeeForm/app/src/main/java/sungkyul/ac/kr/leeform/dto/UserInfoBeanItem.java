package sungkyul.ac.kr.leeform.dto;

/**
 * Created by HunJin on 2016-06-01.
 */
public class UserInfoBeanItem {

    String user_unique_key;
    String kakao_unique_key;
    String img;
    String name;

    public String getUser_unique_key() {
        return user_unique_key;
    }

    public void setUser_unique_key(String user_unique_key) {
        this.user_unique_key = user_unique_key;
    }

    public String getKakao_unique_key() {
        return kakao_unique_key;
    }

    public void setKakao_unique_key(String kakao_unique_key) {
        this.kakao_unique_key = kakao_unique_key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
