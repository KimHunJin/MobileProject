package sungkyul.ac.kr.leeform.dao;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import sungkyul.ac.kr.leeform.dto.CommunityBean;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.dto.WritingBean;

/**
 * Created by HunJin on 2016-05-30.
 */
public interface ConnectService {
    @GET("community_list.php")
    Call<CommunityBean> getCommunityList(
    );

    @GET("writing_list.php")
    Call<WritingBean> getWritingList(
    );

    @GET("know_how_writing.php")
    Call<KnowHowWritingBean> setKnowHow(
            @QueryMap Map<String, String> options
    );


}
