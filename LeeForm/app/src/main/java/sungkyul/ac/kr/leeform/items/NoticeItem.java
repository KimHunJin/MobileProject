package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-06-13.
 */
public class NoticeItem {
    private String name;
    private int nNoticeUniqueKey;
    private String date;
    private int number;

    public String getName() {
        return name;
    }

    public int getnNoticeUniqueKey() {
        return nNoticeUniqueKey;
    }

    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public NoticeItem(int noticeUniqueKey, String name, String date, int number) {
        this.name = name;
        this.nNoticeUniqueKey = noticeUniqueKey;
        this.date = date;
        this.number = number;
    }
}
