package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-05-21.
 */
public class CreateKnowHowItem {
    private int number;
    private int mImg;
    private String explane;

    public int getNumber() {
        return number;
    }

    public int getmImg() {
        return mImg;
    }

    public String getExplane() {
        return explane;
    }

    public CreateKnowHowItem(int number, int mImg, String explane) {
        this.number = number;
        this.mImg = mImg;
        this.explane = explane;
    }
}
