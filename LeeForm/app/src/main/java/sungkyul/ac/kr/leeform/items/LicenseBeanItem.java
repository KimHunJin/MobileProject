package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-06-13.
 */
public class LicenseBeanItem {
    int license_unique_key;
    String license_writing_contents;
    String license_name;

    public int getLicense_unique_key() {
        return license_unique_key;
    }

    public void setLicense_unique_key(int license_unique_key) {
        this.license_unique_key = license_unique_key;
    }

    public String getLicense_writing_contents() {
        return license_writing_contents;
    }

    public void setLicense_writing_contents(String license_writing_contents) {
        this.license_writing_contents = license_writing_contents;
    }

    public String getLicense_name() {
        return license_name;
    }

    public void setLicense_name(String license_name) {
        this.license_name = license_name;
    }
}
