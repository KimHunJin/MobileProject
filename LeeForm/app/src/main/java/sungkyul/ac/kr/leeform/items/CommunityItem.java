package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-05-16.
 */
public class CommunityItem {
    private String cName;
    private String cCount;
    private String cContent;
    private int cImg;

    public String getcName() {
        return cName;
    }

    public String getcCount() {
        return cCount;
    }

    public String getcContent() {
        return cContent;
    }

    public int getcImg() {
        return cImg;
    }

    public CommunityItem(String name, String count, String content, int img) {
        cName = name;
        cCount = count;
        cContent = content;
        cImg = img;
    }
}
