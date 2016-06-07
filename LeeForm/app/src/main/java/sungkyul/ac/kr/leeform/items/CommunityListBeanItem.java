package sungkyul.ac.kr.leeform.items;

/**
 * Created by MiSeon on 2016-05-26.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityListBeanItem {
    private int community_unique_key;
    private String img;
    private String name;
    private String community_writing_contents;
    private String community_writing_date;
    private String reply_community_amount;

    public String getReply_community_amount() {
        return reply_community_amount;
    }

    public void setReply_community_amount(String reply_community_amount) {
        this.reply_community_amount = reply_community_amount;
    }

    public int getCommunity_unique_key() {
        return community_unique_key;
    }

    public void setCommunity_unique_key(int community_unique_key) {
        this.community_unique_key = community_unique_key;
    }

    public String getCommunity_writing_date() {
        return community_writing_date;
    }

    public void setCommunity_writing_date(String community_writing_date) {
        this.community_writing_date = community_writing_date;
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

    public String getCommunity_writing_contents() {
        return community_writing_contents;
    }

    public void setCommunity_writing_contents(String community_writing_contents) {
        this.community_writing_contents = community_writing_contents;
    }
}
