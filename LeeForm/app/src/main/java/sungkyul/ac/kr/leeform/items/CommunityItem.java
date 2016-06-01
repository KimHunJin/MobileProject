package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-05-16.
 */
public class CommunityItem {
    private int cNumber;
    private String cName;
    private String cCount;
    private String cContent;
    private int cImg;
    public int getcNumber() {
        return cNumber;
    }

    public void setcNumber(int cNumber) {
        this.cNumber = cNumber;
    }

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

    public CommunityItem(int number ,String name, String count, String content, int img) {
        cNumber=number;
        cName = name;
        cCount = count;
        cContent = content;
        cImg = img;
    }
}
