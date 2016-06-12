package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.MaterialGridItem;

/**
 * Created by user on 2016-06-11.
 */
public class MaterialListBean {
    private String err;
    private String count;
    private List<MaterialGridItem> material_list;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MaterialGridItem> getMaterial_list() {
        return material_list;
    }

    public void setMaterial_list(List<MaterialGridItem> material_list) {
        this.material_list = material_list;
    }
}
