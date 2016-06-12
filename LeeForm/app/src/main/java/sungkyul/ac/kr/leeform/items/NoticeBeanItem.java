package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-06-13.
 */
public class NoticeBeanItem {
    int notice_unique_key;
    String notice_writing_contents;
    String notice_date;
    int notice_number;

    public int getNotice_unique_key() {
        return notice_unique_key;
    }

    public void setNotice_unique_key(int notice_unique_key) {
        this.notice_unique_key = notice_unique_key;
    }

    public String getNotice_writing_contents() {
        return notice_writing_contents;
    }

    public void setNotice_writing_contents(String notice_writing_contents) {
        this.notice_writing_contents = notice_writing_contents;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }

    public int getNotice_number() {
        return notice_number;
    }

    public void setNotice_number(int notice_number) {
        this.notice_number = notice_number;
    }
}
