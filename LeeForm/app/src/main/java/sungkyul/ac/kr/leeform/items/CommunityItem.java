package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-05-16.
 */
public class CommunityItem {
    private int cNumber;
    private String cName;
    private String cCount;
    private String cContent;
    private String cImageURL;
    //private int cImg;

    public int getcNumber() {
        return cNumber;
    }

    public void setcNumber(int cNumber) {
        this.cNumber = cNumber;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCount() {
        return cCount;
    }

    public void setcCount(String cCount) {
        this.cCount = cCount;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public String getcImageURL() {
        return cImageURL;
    }

    public void setcImageURL(String cImageURL) {
        this.cImageURL = cImageURL;
    }

    public CommunityItem(String name, String count, String content, String img) {

        cName = name;
        cCount = count;
        cContent = content;
        cImageURL = img;
    }
}
