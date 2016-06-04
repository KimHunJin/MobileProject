package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import sungkyul.ac.kr.leeform.dto.CommunityBeanList;
import sungkyul.ac.kr.leeform.items.CommunityItem;

/**
 * Created by HunJin on 2016-05-01.
 * 커뮤니티 리스트가 뜬다.
 */
public class CommunityFragment extends Fragment {
    private int check=0;
    private View cView;
    private CommunityListAdapter adapter;
    private static String URL = "http://14.63.196.255/api/";
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
        intent=new Intent(getContext(),CommunityDetailActivity.class);
        //lst에 adqpter를 등록한다.
        lst.setAdapter(adapter);
        //커뮤니티 목록 중 선택한 넘버 보내기
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*intent.putExtra("Number",listItem.get(position).getcNumber());*/
                intent.putExtra("Number",position);
                startActivity(intent);
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
    public void layoutSetting(){
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
        // 다른 프래그먼트에 갔다가 오면
        if(check == 1){
            check = 0;
        }else{
            check = 1;
        }
    }
   /* @Override
    public void onResume() {
        Log.e("resume","resume");
        super.onResume();
    }*/

    public void communityDetailList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        // http://14.63.196.255/api/community_list.php
        Call<CommunityBeanList> call = connectService.getCommunityList();
        call.enqueue(new Callback<CommunityBeanList>() {
            @Override
            public void onResponse(Call<CommunityBeanList> call, Response<CommunityBeanList> response) {
                Log.e("resonpse", response.code() + "");
                CommunityBeanList decode = response.body(); //CommunityBeanList 형식으로 디코딩
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getCommunity_list().size() + "");
                listItem.clear();
                //커뮤니티 목록 개수만큼 list에 CommunityItem(작성자이름, 댓글개수, 커뮤니티 내용, 작성자이미지) 추가
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {

                    listItem.add(new CommunityItem(decode.getCommunity_list().get(i).getName(), "5", decode.getCommunity_list().get(i).getCommunity_writing_contents(), decode.getCommunity_list().get(i).getImg()));
                 //   decode.getCommunity_list().get(i).get
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommunityBeanList> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }



}