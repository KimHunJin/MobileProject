package sungkyul.ac.kr.leeform.items;

/**
 * Created by KyungHee on 2016-05-12.
 */
public class MaterialGridItem {
    private int mKey;
    private String mName;
    private String mUrl;
    private String mPrice;


    public MaterialGridItem(int material_unique_key,String material_picture_url, String material_name, String material_price) {
        mKey = material_unique_key;
        mUrl = material_picture_url;
        mName = material_name;
        mPrice = material_price;
    }

    public int getmKey() {
        return mKey;
    }

    public void setmKey(int mKey) {
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
