package sungkyul.ac.kr.leeform.dto;

/**
 * Created by user on 2016-06-11.
 */
public class UserModify {
    String err;
    String user_unique_key;
    String name;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getUser_unique_key() {
        return user_unique_key;
    }

    public void setUser_unique_key(String user_unique_key) {
        this.user_unique_key = user_unique_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
