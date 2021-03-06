package sungkyul.ac.kr.leeform.items;

/**
 * Created by MiSeon on 2016-05-26.
 */
public class ReplyItem {
    private int rNumber;
    private String rName;
    private String rContent;
    private String rImg;
    private String rTime;

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }

    public int getrNumber() {
        return rNumber;
    }

    public void setrNumber(int rNumber) {
        this.rNumber = rNumber;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getrImg() {
        return rImg;
    }

    public void setrImg(String rImg) {
        this.rImg = rImg;
    }

    public ReplyItem(String name, String content, String img, String time) {
        rName = name;
        rContent = content;
        rImg = img;
        rTime = time;
    }

}
