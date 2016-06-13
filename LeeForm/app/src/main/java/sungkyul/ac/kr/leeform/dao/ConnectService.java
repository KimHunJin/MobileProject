package sungkyul.ac.kr.leeform.dao;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sungkyul.ac.kr.leeform.dto.AlarmCheckBean;
import sungkyul.ac.kr.leeform.dto.CommunityDetailBean;
import sungkyul.ac.kr.leeform.dto.CommunityListBean;
import sungkyul.ac.kr.leeform.dto.CommunityWritingBean;
import sungkyul.ac.kr.leeform.dto.KnowHowBean;
import sungkyul.ac.kr.leeform.dto.KnowHowDetailBean;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.dto.LicenseBean;
import sungkyul.ac.kr.leeform.dto.MaterialDetailBean;
import sungkyul.ac.kr.leeform.dto.MaterialListBean;
import sungkyul.ac.kr.leeform.dto.NoticeBean;
import sungkyul.ac.kr.leeform.dto.OnlyErrBean;
import sungkyul.ac.kr.leeform.dto.RegistBean;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.dto.UserInfoBean;
import sungkyul.ac.kr.leeform.dto.UserModifyBean;
import sungkyul.ac.kr.leeform.dto.getAlarmStateBean;

//<<<<<<< HEAD
//=======

/**
 * Created by HunJin on 2016-06-10.
 */
public interface ConnectService {

    @GET("community_list.php")
    Call<CommunityListBean> getCommunityList(
            @Query("offset") int offset
    );

    @GET("know_how_writing.php")
    Call<KnowHowWritingBean> setKnowHow(
            @QueryMap Map<String, String> options
    );

    @GET("writing_list.php")
    Call<KnowHowBean> getWritingList(
            @Query("offset") int offset
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

    @GET("reply_community.php")
    Call<CommunityListBean> setCommunityReply(
            @QueryMap Map<String, String> options
    );

    @GET("check_scrap.php")
    Call<OnlyErrBean> getCheckScrap(
            @QueryMap Map<String, String> options
    );

    @GET("scrap.php")
    Call<OnlyErrBean> setScrap(
            @QueryMap Map<String, String> options
    );

    @GET("cancel_scrap.php")
    Call<OnlyErrBean> unScrap(
            @QueryMap Map<String, String> options
    );

    @GET("writing_detail.php")
    Call<KnowHowDetailBean> getKnowHowDetail(
            @Query("writing_unique_key") String writing_unique_key
    );

    @GET("writing_list_latest.php")
    Call<KnowHowBean> getWritingListLatest(
            @Query("offset") int offset
    );

    @GET("written_by_user_list.php")
    Call<KnowHowBean> getMyWriteKnowHow(
            @Query("user_unique_key") String user_unique_key
    );

    @GET("scrap_list.php")
    Call<KnowHowBean> getScrapKnowHow(
            @Query("user_unique_key") String user_unique_key
    );

    @GET("set_gcm.php")
    Call<OnlyErrBean> setGCMToken(
            @QueryMap Map<String, String> options
    );

    @GET("myinfo_detail.php")
    Call<UserBean> getUserDetail(
            @Query("user_unique_key") String user_unique_key
    );

    @GET("modify_myinfo.php")
    Call<UserModifyBean> setUserDetail(
            @QueryMap Map<String, String> options
    );

    @GET("set_sales_authority.php")
    Call<RegistBean> setResigster(
            @QueryMap Map<String, String> options
    );

    @GET("material_list.php")
    Call<MaterialListBean> getMaterialList(
            @Query("offset") int offset
    );

    @GET("material_detail.php")
    Call<MaterialDetailBean> getMaterialDetail(
            @Query("material_unique_key") String material_unique_key
    );

    @GET("search.php")
    Call<KnowHowBean> getKnowHowSearch(
            @Query("data") String data
    );

    @GET("search_material.php")
    Call<MaterialListBean> getMaterialSearch(
            @Query("data") String data
    );

    @GET("notice_list.php")
    Call<NoticeBean> getNotice();

    @GET("license_list.php")
    Call<LicenseBean> getLicense();

    @GET("PushAlarm.php")
    Call<OnlyErrBean> push(
            @Query("writing_unique_key") String writing_unique_key
    );

    @GET("set_push_alarm.php")
    Call<AlarmCheckBean> change(
            @Query("user_unique_key") String user_unique_key
    );

    @GET("check_push_alarm.php")
    Call<getAlarmStateBean> checkAlarmState(
            @Query("user_unique_key") String user_unique_key
    );
}

