package sungkyul.ac.kr.leeform.dao;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sungkyul.ac.kr.leeform.dto.CommunityBean;

/**
 * Created by HunJin on 2016-05-30.
 */
public interface ConnectService {
    @GET("community_list.php")
    Call<CommunityBean> get(
    );

    @GET("community_detail.php")
    Call<CommunityBean> get(
            @Query("community_unique_key") String  community_unique_key
    );

    @GET("api_explanation.php")
    Call<CommunityBean> get(
            @QueryMap Map<String , String> options
    );
}
