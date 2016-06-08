package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.community.CommunityCreateActivity;
import sungkyul.ac.kr.leeform.activity.community.CommunityDetailActivity;
import sungkyul.ac.kr.leeform.adapter.CommunityListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityListBean;
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.TimeTransForm;

/**
 * Created by MiSeon on 2016-05-10.
 * 커뮤니티 리스트가 뜬다.
 */
public class CommunityFragment extends Fragment {
    private int check = 0;
    private View cView;
    private CommunityListAdapter adapter;
    private static String URL = StaticURL.BASE_URL;
    ListView lst;
    ArrayList<CommunityItem> listItem;
    FloatingActionButton fab1;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //inflater를 통해 xml을 가져온다.
        cView = inflater.inflate(R.layout.fragment_community, container, false);

        layoutSetting();

        //lst에 adqpter를 등록한다.
        lst.setAdapter(adapter);
        //커뮤니티 목록 중 선택한 넘버 보내기
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getContext(), CommunityDetailActivity.class);
                Log.e("Number",listItem.get(position).getcNumber()+"");
                /*intent.putExtra("Number",listItem.get(position).getcNumber());*/
                intent.putExtra("Number", listItem.get(position).getcNumber());
                intent.putExtra("image",listItem.get(position).getcImageURL());
                intent.putExtra("name",listItem.get(position).getcName());
                intent.putExtra("time",listItem.get(position).getcTime());
                intent.putExtra("contents",listItem.get(position).getcContent());

                startActivity(intent);
            }
        });

        lst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    fab1.setVisibility(View.VISIBLE);
                } else {
                    fab1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //작성버튼 클릭시 커뮤니티작성화면으로 이동
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommunityCreateActivity.class));
            }
        });

        communityDetailList();

        return cView;

    }

    /**
     * Layout Setting
     */
    public void layoutSetting() {
        listItem = new ArrayList<>();
        //adapter를 통해 xml을 ArrayList에 설정한다.
        adapter = new CommunityListAdapter(getContext(), R.layout.item_list_community, listItem);
        lst = (ListView) cView.findViewById(R.id.listCommunity);
        fab1 = (FloatingActionButton) cView.findViewById(R.id.fab1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 다른 프래그먼트 가면 초기화
        check = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        communityDetailList();

    }

    public void communityDetailList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        // http://14.63.196.255/api/community_list.php
        Call<CommunityListBean> call = connectService.getCommunityList();
        call.enqueue(new Callback<CommunityListBean>() {
            @Override
            public void onResponse(Call<CommunityListBean> call, Response<CommunityListBean> response) {
                Log.e("resonpse", response.code() + "");
                CommunityListBean decode = response.body(); //CommunityListBean 형식으로 디코딩
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getCommunity_list().size() + "");
                listItem.clear();
                //커뮤니티 목록 개수만큼 list에 CommunityItem(작성자이름, 댓글개수, 커뮤니티 내용, 작성자이미지) 추가
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {

                    String date = decode.getCommunity_list().get(i).getCommunity_writing_date();
                    try {
                        date = TimeTransForm.formatTimeString(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    listItem.add(new CommunityItem(decode.getCommunity_list().get(i).getCommunity_unique_key(),decode.getCommunity_list().get(i).getName(), decode.getCommunity_list().get(i).getReply_community_amount(), decode.getCommunity_list().get(i).getCommunity_writing_contents(), decode.getCommunity_list().get(i).getImg(),date));
                    //   decode.getCommunity_list().get(i).get
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommunityListBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }


}