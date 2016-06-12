package sungkyul.ac.kr.leeform.utils;

/**
 * Created by HunJin on 2016-06-13.
 */
public class EndString {
    public static String endString(String contents, int n) {
        if(contents.length()>n) {
            return contents.substring(0,n) + "...";
        } else {
            return contents;
        }
    }
}
