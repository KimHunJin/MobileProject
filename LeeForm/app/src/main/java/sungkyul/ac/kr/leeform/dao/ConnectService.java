package sungkyul.ac.kr.leeform.dao;

import retrofit2.Call;
import retrofit2.http.GET;
import sungkyul.ac.kr.leeform.dto.CommunityBean;

/**
 * Created by HunJin on 2016-05-30.
 */
public interface ConnectService {
    @GET("community_list.php")
    Call<CommunityBean> get(
    );
}
