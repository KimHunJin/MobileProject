package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-05-18.
 */
public class CreateKnowHowItem {
    private int number;
    private int mImg;
    private String imgUrl;
    private String explain;

    public int getNumber() {
        return number;
    }

    public int getmImg() {
        return mImg;
    }

    public String getExplain() {
        return explain;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public CreateKnowHowItem(int number, String mImg, String explain) {
        this.number = number;
        this.imgUrl = mImg;
        this.explain = explain;
    }

    public CreateKnowHowItem(int number, int mImg, String explain) {
        this.number = number;
        this.mImg = mImg;
        this.explain = explain;
    }
}
