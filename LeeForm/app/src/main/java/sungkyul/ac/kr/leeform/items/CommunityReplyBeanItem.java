package sungkyul.ac.kr.leeform.items;

/**
 * Created by MiSeon on 2016-05-29.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityReplyBeanItem {
    String name;
    String img;
    String reply_community_contents;
    String reply_date;

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    public String getReply_community_contents() {
        return reply_community_contents;
    }

    public void setReply_community_contents(String reply_community_contents) {
        this.reply_community_contents = reply_community_contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
