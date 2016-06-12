package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.MaterialListBeanItem;

/**
 * Created by user on 2016-06-11.
 */
public class MaterialListBean {
    private String err;
    private String count;
    private List<MaterialListBeanItem> material_list;
    private List<MaterialListBeanItem> search_data;

    public List<MaterialListBeanItem> getSearch_data() {
        return search_data;
    }

    public void setSearch_data(List<MaterialListBeanItem> search_data) {
        this.search_data = search_data;
    }

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

    public List<MaterialListBeanItem> getMaterial_list() {
        return material_list;
    }

    public void setMaterial_list(List<MaterialListBeanItem> material_list) {
        this.material_list = material_list;
    }
}
