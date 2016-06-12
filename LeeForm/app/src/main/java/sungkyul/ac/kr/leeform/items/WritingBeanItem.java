package sungkyul.ac.kr.leeform.items;

/**
 * Created by HunJin on 2016-05-25.
 * json을 받아줄 클래스를 생성
 * json의 키에 해당하는 값들을 변수 이름으로 지정
 */
public class WritingBeanItem {
    String writing_unique_key;
    String writing_name;
    String price;
    String making_time;
    String picture_url;
    String scrap_amount;
    String explanation;
    String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getScrap_amount() {
        return scrap_amount;
    }

    public void setScrap_amount(String scrap_amount) {
        this.scrap_amount = scrap_amount;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getWriting_unique_key() {
        return writing_unique_key;
    }

    public void setWriting_unique_key(String writing_unique_key) {
        this.writing_unique_key = writing_unique_key;
    }

    public String getWriting_name() {
        return writing_name;
    }

    public void setWriting_name(String writing_name) {
        this.writing_name = writing_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaking_time() {
        return making_time;
    }

    public void setMaking_time(String making_time) {
        this.making_time = making_time;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
}
