package sungkyul.ac.kr.leeform.dao;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sungkyul.ac.kr.leeform.dto.CommunityDetailBean;
import sungkyul.ac.kr.leeform.dto.CommunityListBean;
import sungkyul.ac.kr.leeform.dto.CommunityWritingBean;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.dto.UserInfoBean;
import sungkyul.ac.kr.leeform.dto.KnowHowBean;

/**
 * Created by HunJin on 2016-05-22.
 */
public interface ConnectService {
    //쿼리가 하나인 경우
    @GET("community_list.php")
    Call<CommunityListBean> getCommunityList(
    );

    @GET("know_how_writing.php")
    Call<KnowHowWritingBean> setKnowHow(
            @QueryMap Map<String, String> options
    );

    @GET("writing_list.php")
    Call<KnowHowBean> getWritingList(
    );

    @GET("community_writing.php")
    Call<CommunityWritingBean> setCommunity(
            @QueryMap Map<String, String> options
    );

    @GET("community_detail.php")
    Call<CommunityDetailBean> get(
            @Query("community_unique_key") String community_unique_key
    );

    @GET("get.php")
    Call<UserInfoBean> getUserInfo(
            @Query("kakao_unique_key") String kakao_unique_key
    );

    @GET("set.php")
    Call<UserInfoBean> setUserInfo(
            @QueryMap Map<String, String> options
    );

    @GET("know_how_writing_get_key.php")
    Call<KnowHowWritingBean> setKnowGetKey(
            @QueryMap Map<String, String> options
    );
}
