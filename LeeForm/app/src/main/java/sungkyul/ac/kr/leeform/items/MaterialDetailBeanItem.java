package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-06-12.
 */
public class MaterialDetailBeanItem {
    int material_unique_key;
    String material_name;
    String material_explanation;
    String material_price;
    String subcontractor_name;
    String subcontractor_image_url;

    public String getMaterial_explanation() {
        return material_explanation;
    }

    public void setMaterial_explanation(String material_explanation) {
        this.material_explanation = material_explanation;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(String material_price) {
        this.material_price = material_price;
    }

    public int getMaterial_unique_key() {
        return material_unique_key;
    }

    public void setMaterial_unique_key(int material_unique_key) {
        this.material_unique_key = material_unique_key;
    }

    public String getSubcontractor_image_url() {
        return subcontractor_image_url;
    }

    public void setSubcontractor_image_url(String subcontractor_image_url) {
        this.subcontractor_image_url = subcontractor_image_url;
    }

    public String getSubcontractor_name() {
        return subcontractor_name;
    }

    public void setSubcontractor_name(String subcontractor_name) {
        this.subcontractor_name = subcontractor_name;
    }
}
