package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-05-18.
 */
public class ReplyItem {
    private int rNumber;
    private String rName;
    private String rContent;
    private int rImg;

    public String getrName() {
        return rName;
    }

    public String getrContent() {
        return rContent;
    }

    public int getrImg() {
        return rImg;
    }

    public ReplyItem(String name, String content, int img){
        rName=name;
        rContent=content;
        rImg=img;
    }

}
