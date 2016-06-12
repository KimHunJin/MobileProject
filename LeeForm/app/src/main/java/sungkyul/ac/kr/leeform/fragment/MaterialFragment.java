package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.material.MaterialDetailActivity;
import sungkyul.ac.kr.leeform.adapter.CommunityListAdapter;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityListBean;
import sungkyul.ac.kr.leeform.dto.MaterialListBean;
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.TimeTransForm;

/**
 * Created by KyungHee on 2016-05-12.
 */
public class MaterialFragment extends Fragment {
    private int check = 0;
    private View mView;
    int offset = 0;
    int count = 0;
    boolean is_scroll = true;
    private boolean isScrollingUp =false;
    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;
    GridView gridView;
    private int mLastFirstVisibleItem = 0;
    Intent intent;
    private static String URL = StaticURL.BASE_URL;

    ArrayList<MaterialGridItem> gridItems = new ArrayList<>();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 다른 프래그먼트 가면 초기화
        check = 1;
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_material, container, false);

        layoutSetting();
        setListener();
        /*
        grvMaterial = (GridView) mView.findViewById(R.id.grvMaterial);
        mAdapter = new MaterialGridAdapter(getContext(), R.layout.item_grid_material, gridItems);
        grvMaterial.setAdapter(mAdapter);

        grvMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itMaterialDetail = new Intent(getActivity().getApplicationContext(), MaterialDetailActivity.class);
                startActivity(itMaterialDetail);
            }
        });*/

        init();
        getMaterialDetailList();
        return mView;
    }

    public void layoutSetting() {
        gridItems = new ArrayList<>();
        //adapter를 통해 xml을 ArrayList에 설정한다.
        mAdapter = new MaterialGridAdapter(getContext(), R.layout.item_grid_material, gridItems);
        gridView = (GridView) mView.findViewById(R.id.grvMaterial);
        //swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.communitySwipeRefresh);
    }


    private void setListener() {
        //lst에 adqpter를 등록한다.
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intent = new Intent(getContext(), MaterialListBean.class);
                //Log.e("Number", gridItems.get(position).getmKey() + "");

                //intent.putExtra("Number", gridItems.get(position).getmKey());
                //intent.putExtra("image", gridItems.get(position).getmUrl());
                //intent.putExtra("name", gridItems.get(position).getmName());
                //intent.putExtra("price", gridItems.get(position).getmPrice());
                //intent.putExtra("contents", gridItems.get(position).getcContent());

                //startActivity(intent);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final GridView gv = (GridView) view;
                if (view.getId() == gv.getId()) {
                    final int currentFirstVisibleItem = gv.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        isScrollingUp = false;
                        //fab1.hide();
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        isScrollingUp = true;
                        //fab1.show();
                    }

                    mLastFirstVisibleItem = currentFirstVisibleItem;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (count != 0 && offset % 6 == 0) {
                        if (is_scroll) {
                            is_scroll = false;
                            //is_refresh = false;
                            getMaterialDetailList();
                        }

                    }
                }
            }
        });
    }

    public void getMaterialDetailList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        // http://14.63.196.255/api/community_list.php
        Call<MaterialListBean> call = connectService.getMaterialList(offset);
        call.enqueue(new Callback<MaterialListBean>() {
            @Override
            public void onResponse(Call<MaterialListBean> call, Response<MaterialListBean> response) {
                Log.e("resonpse", response.code() + "");
                MaterialListBean decode = response.body(); //CommunityListBean 형식으로 디코딩
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getMaterial_list().size() + "");
                //Log.e("getmKey", decode.getMaterial_list().get(1).getmKey()+"");
                //커뮤니티 목록 개수만큼 list에 CommunityItem(작성자이름, 댓글개수, 커뮤니티 내용, 작성자이미지) 추가
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {
                    gridItems.add(new MaterialGridItem(decode.getMaterial_list().get(i).getmKey(), decode.getMaterial_list().get(i).getmName(), decode.getMaterial_list().get(i).getmPrice(), decode.getMaterial_list().get(i).getmUrl()));
                    //   decode.getCommunity_list().get(i).get
                }
                offset += Integer.parseInt(decode.getCount());
                count += Integer.parseInt(decode.getCount());
                //swipeRefreshLayout.setRefreshing(false);
                is_scroll=true;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MaterialListBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

        //작성버튼 클릭시 커뮤니티작성화면으로 이동
        /*fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommunityCreateActivity.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                getCommunityDetailList();
            }
        });*/



    void init() {
        offset = 0;
        count = 0;
        check = 0;
        is_scroll = true;
        //is_refresh = true;
        gridItems.clear();
        //for (int i = 0; i < 10; i++) {
          //  gridItems.add(new MaterialGridItem(i, "판자", R.drawable.panza));
        //}
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
