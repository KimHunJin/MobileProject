package sungkyul.ac.kr.leeform.dao;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sungkyul.ac.kr.leeform.dto.CommunityBeanDetail;
import sungkyul.ac.kr.leeform.dto.CommunityBeanList;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.dto.WritingBean;

/**
 * Created by HunJin on 2016-05-30.
 */
public interface ConnectService {
    //쿼리가 하나인 경우
    @GET("community_list.php")
    Call<CommunityBeanList> getCommunityList(
    );

    @GET("writing_list.php")
    Call<WritingBean> getWritingList(
    );

    @GET("know_how_writing.php")
    Call<KnowHowWritingBean> setKnowHow(
            @QueryMap Map<String, String> options
    );

    @GET("community_detail.php")
    Call<CommunityBeanDetail> get(
            @Query("community_unique_key") String  community_unique_key
    );

    @GET("write_community.php")
    Call<CommunityBeanDetail> get(
            @QueryMap Map<String , String> options
    );
}
