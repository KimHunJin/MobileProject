package sungkyul.ac.kr.leeform.dto;

/**
 * Created by user on 2016-05-31.
 *  json을 받아줄 클래스를 생성
 *  json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityBeanReplyItem {
    String name;
    String img;
    String reply_writing_contents;

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

    public String getReply_writing_contents() {
        return reply_writing_contents;
    }

    public void setReply_writing_contents(String reply_writing_contents) {
        this.reply_writing_contents = reply_writing_contents;
    }
}
