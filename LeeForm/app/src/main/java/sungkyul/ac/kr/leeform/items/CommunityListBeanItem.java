package sungkyul.ac.kr.leeform.items;

/**
 * Created by MiSeon on 2016-05-26.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class CommunityListBeanItem {
    private String img;
    private String name;
    private String community_writing_contents;


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
