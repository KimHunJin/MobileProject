package sungkyul.ac.kr.leeform.utils;

import java.text.ParseException;

/**
 * Created by HunJin on 2016-06-06.
 */
public class TimeTransForm {

    public static String formatTimeString(String str) throws ParseException {

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        java.util.Date date = format.parse(str);

        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;
        if (diffTime < TimeMaximum.SEC) {
// sec
            msg = "방금 전";
        } else if ((diffTime /= TimeMaximum.SEC) < TimeMaximum.MIN) {
// min
            msg = diffTime + "분 전";
        } else if ((diffTime /= TimeMaximum.MIN) < TimeMaximum.HOUR) {
// hour
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TimeMaximum.HOUR) < TimeMaximum.DAY) {
// day
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TimeMaximum.DAY) < TimeMaximum.MONTH) {
// day
            msg = (diffTime) + "달 전";
        } else {
            msg = str;
        }
        return msg;
    }
}
