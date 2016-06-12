package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.material.MaterialDetailActivity;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.MaterialListBean;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by KyungHee on 2016-05-12.
 */
public class MaterialFragment extends Fragment {
    private int check = 0;
    private View mView;
    int offset = 0;
    int count = 0;
    boolean is_scroll = true;
    private boolean isScrollingUp = false;
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
        init();
        getMaterialDetailList();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), MaterialDetailActivity.class);
                Log.e("material_unique_key", gridItems.get(position).getmKey() + "");
                it.putExtra("material_unique_key", gridItems.get(position).getmKey() + "");
                startActivity(it);
            }
        });
        return mView;
    }

    public void layoutSetting() {
        gridItems = new ArrayList<>();
        //adapter를 통해 xml을 ArrayList에 설정한다.
        mAdapter = new MaterialGridAdapter(getContext(), R.layout.item_grid_material, gridItems);
        gridView = (GridView) mView.findViewById(R.id.grvMaterial);
    }

    private void setListener() {
        //lst에 adqpter를 등록한다.
        gridView.setAdapter(mAdapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final GridView gv = (GridView) view;
                if (view.getId() == gv.getId()) {
                    final int currentFirstVisibleItem = gv.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        isScrollingUp = false;
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        isScrollingUp = true;
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
        Call<MaterialListBean> call = connectService.getMaterialList(offset);
        call.enqueue(new Callback<MaterialListBean>() {
            @Override
            public void onResponse(Call<MaterialListBean> call, Response<MaterialListBean> response) {
                MaterialListBean decode = response.body(); //CommunityListBean 형식으로 디코딩
                Log.e("err", decode.getErr());
                //커뮤니티 목록 개수만큼 list에 CommunityItem(작성자이름, 댓글개수, 커뮤니티 내용, 작성자이미지) 추가
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {
                    gridItems.add(new MaterialGridItem(decode.getMaterial_list().get(i).getMaterial_unique_key(), decode.getMaterial_list().get(i).getMaterial_picture_url(), decode.getMaterial_list().get(i).getMaterial_name(), decode.getMaterial_list().get(i).getMaterial_price()));
                }
                offset += Integer.parseInt(decode.getCount());
                count += Integer.parseInt(decode.getCount());
                is_scroll = true;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MaterialListBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    void init() {
        offset = 0;
        count = 0;
        check = 0;
        is_scroll = true;
        gridItems.clear();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
