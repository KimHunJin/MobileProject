package sungkyul.ac.kr.leeform.dto;

import java.util.List;

import sungkyul.ac.kr.leeform.items.LicenseBeanItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class LicenseBean {
    int err;
    List<LicenseBeanItem> license_list;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<LicenseBeanItem> getLicense_list() {
        return license_list;
    }

    public void setLicense_list(List<LicenseBeanItem> license_list) {
        this.license_list = license_list;
    }
}
