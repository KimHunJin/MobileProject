package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-06-13.
 */
public class LicenseItem {
    private String licenseName;
    private int licenseUniqueKey;
    private String licenseContents;

    public String getLicenseName() {
        return licenseName;
    }

    public int getLicenseUniqueKey() {
        return licenseUniqueKey;
    }

    public String getLicenseContents() {
        return licenseContents;
    }

    public LicenseItem(int key, String name, String contents) {
        licenseUniqueKey = key;
        licenseName = name;
        licenseContents = contents;
    }
}
