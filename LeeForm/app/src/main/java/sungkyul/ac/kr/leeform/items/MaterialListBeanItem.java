package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-06-12.
 */
public class MaterialListBeanItem {
    int material_unique_key;
    String material_name;
    String material_picture_url;
    String material_price;

    public int getMaterial_unique_key() {
        return material_unique_key;
    }

    public void setMaterial_unique_key(int material_unique_key) {
        this.material_unique_key = material_unique_key;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_picture_url() {
        return material_picture_url;
    }

    public void setMaterial_picture_url(String material_picture_url) {
        this.material_picture_url = material_picture_url;
    }

    public String getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(String material_price) {
        this.material_price = material_price;
    }
}
