package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.MaterialDetailBeanItem;
import sungkyul.ac.kr.leeform.items.MaterialDetailBeanPictureItem;

/**
 * Created by user on 2016-06-12.
 */
public class MaterialDetailBean {
    private String err;
    private String count;
    private List<MaterialDetailBeanItem> material_detail1;
    private List<MaterialDetailBeanPictureItem> material_detail2;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<MaterialDetailBeanItem> getMaterial_detail1() {
        return material_detail1;
    }

    public void setMaterial_detail1(List<MaterialDetailBeanItem> material_detail1) {
        this.material_detail1 = material_detail1;
    }

    public List<MaterialDetailBeanPictureItem> getMaterial_detail2() {
        return material_detail2;
    }

    public void setMaterial_detail2(List<MaterialDetailBeanPictureItem> material_detail2) {
        this.material_detail2 = material_detail2;
    }
}
