package sungkyul.ac.kr.leeform.items;

import android.content.res.Resources;
import android.content.res.TypedArray;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by HunJin on 2016-04-06.
 */
public class MainListItem {

    private int mNumber;
    private String mCost;
    private String mTime;
    private String mLike;
    private int mImg;
    private String mUrl;

    public int getmNumber() {
        return mNumber;
    }

    public String getmCost() {
        return mCost;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmLike() {
        return mLike;
    }

    public String getmUrl() {
        return mUrl;
    }

    public int getmImg() {
        return mImg;
    }

    public MainListItem(int number, String cost, String time, String like, int img) {
        mNumber = number;
        mCost = cost;
        mTime = time;
        mLike = like;
        mImg = img;
//        mUrl = url;
    }
}
