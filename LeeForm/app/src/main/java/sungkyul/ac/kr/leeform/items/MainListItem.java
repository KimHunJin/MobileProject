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

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmCost() {
        return mCost;
    }

    public void setmCost(String mCost) {
        this.mCost = mCost;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmLike() {
        return mLike;
    }

    public void setmLike(String mLike) {
        this.mLike = mLike;
    }

    public int getmImg() {
        return mImg;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public MainListItem(int number, String cost, String time, String like, String url) {
        mNumber = number;
        mCost = cost;
        mTime = time;
        mLike = like;
        mUrl = url;
    }
}
