package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-05-11.
 */
public class MaterialGridItem {

    private int mNumber;
    private String mName;
    private int mImg;
    private String mUrl;

    public MaterialGridItem(int number, String name, int img) {
        mNumber = number;
        mName = name;
        mImg = img;
    }

    public int getmNumber() {
        return mNumber;
    }

    public String getmName() {
        return mName;
    }

    public int getmImg() {
        return mImg;
    }

    public String getmUrl() {
        return mUrl;
    }
}
