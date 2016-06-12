package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.knowhow.KnowHowDetailActivity;
import sungkyul.ac.kr.leeform.activity.knowhow.WriteKnowHowActivity;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.KnowHowBean;
import sungkyul.ac.kr.leeform.items.MainListItem;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by HunJin on 2016-05-10.
 * 노하우리스트화면
 */
public class HomeFragment extends Fragment {
    private int check = 0;
    private View mView;
    int offset = 0;
    int count = 0;
    boolean is_scroll = true;
    boolean is_refresh = true;
    boolean sort = true; // sort = true : 최신순, sort = false : 인기순
    private MainListAdapter adapter;
    private Spinner mSpinnerCategory, mSpinnerSort;
    private FloatingActionButton fab;
    private boolean isScrollingUp = false;
    private int mLastFirstVisibleItem = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListView lst;
    private static String URL = StaticURL.BASE_URL;
    ArrayList<MainListItem> listItem = new ArrayList<>();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 다른 프래그먼트 가면 초기화
        check = 1;
        init();
    }

    private void initializeLayout() {
        adapter = new MainListAdapter(getContext(), R.layout.item_list_main, listItem);

        mSpinnerCategory = (Spinner) mView.findViewById(R.id.spnKnowCategory);
        //(Spinner)findViewById(R.id.spnKnowCategory); 오류=>보여줄 View가 없기 때문
        mSpinnerSort = (Spinner) mView.findViewById(R.id.spnKnowSort);

        String[] mCategory = getResources().getStringArray(R.array.category); //카테고리의 내용들을 배열(mCategory)에 저장
        String[] mSort = getResources().getStringArray(R.array.sort); //정렬의 내용들을 배열(mSort)에 저장

        ArrayAdapter<String> mSpinnerCategoryAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, mCategory);
        //기본으로 제공하는 layout(simple_spinner_item), 리스트에 있는 내용을 mCategory의 내용으로 채우기 위해 ArrayAdapter 이용
        ArrayAdapter<String> mSpinnerSortAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, mSort);

        mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter를 통해 리스트 형태로 늘리는 메소드
        mSpinnerSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCategory.setAdapter(mSpinnerCategoryAdapter); //스피너에 adapter 설정
        mSpinnerSort.setAdapter(mSpinnerSortAdapter);

        lst = (ListView) mView.findViewById(R.id.listMain);
        lst.setAdapter(adapter);

        fab = (FloatingActionButton) mView.findViewById(R.id.fab); //작성하기 버튼
    }

    private void initializeList() {
        // 초기화하고 아이템가져오기
        if (sort == true) {
            latestParsing();
        } else {
            leeformParsing();
        }
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WriteKnowHowActivity.class));
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                if (sort == true) {
                    latestParsing();
                } else {
                    leeformParsing();
                }
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itKnowhowDetail = new Intent(getActivity(), KnowHowDetailActivity.class);
                Log.e("img", listItem.get(position).getmUrl());
                itKnowhowDetail.putExtra("image", listItem.get(position).getmUrl() + "");
                itKnowhowDetail.putExtra("knowhowkey", listItem.get(position).getmNumber() + "");
                startActivity(itKnowhowDetail);
            }
        });

        lst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final ListView lw = (ListView) view;
                if (view.getId() == lw.getId()) {
                    final int currentFirstVisibleItem = lw.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        isScrollingUp = false;
                        fab.hide();
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        isScrollingUp = true;
                        fab.show();
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
                            is_refresh = false;
                            if (sort == true) {
                                latestParsing();
                            } else {
                                leeformParsing();
                            }
                        }
                    }
                }
            }
        });

        mSpinnerCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                check++;
                return false;
            }
        });
        mSpinnerSort.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                check++;
                return false;
            }
        });
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //카테고리 아이템 선택했을 때
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 처음에는 실행 안되게
                if (check > 0) {
                    Toast.makeText(getActivity(), parent.getItemAtPosition(position) + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //정렬 아이템 선택했을 때
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 처음에는 실행 안되게
                if (check > 0) {
                    if (parent.getItemAtPosition(position).equals("인기순")) {
                        sort = false;
                        init();
                        initializeList();

                    } else {
                        sort = true;
                        init();
                        initializeList();

                    }
                    Log.e("sort", sort + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeLayout();
        init();
        initializeList();
        setListener();

        return mView;
    }

    void init() {
        offset = 0;
        count = 0;
        check = 0;
        is_scroll = true;
        is_refresh = true;
        listItem.clear();
    }

    private void leeformParsing() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Log.e("offset", offset + "");
        Call<KnowHowBean> call = connectService.getWritingList(offset);
        call.enqueue(new Callback<KnowHowBean>() {
            @Override
            public void onResponse(Call<KnowHowBean> call, Response<KnowHowBean> response) {
                Log.e("resonpse", response.code() + "");
                KnowHowBean decode = response.body();
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getWriting_list().size() + "");

                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {
                    Log.e("imgUrl", decode.getWriting_list().get(i).getPicture_url());
                    listItem.add(new MainListItem(Integer.parseInt(decode.getWriting_list().get(i).getWriting_unique_key()), decode.getWriting_list().get(i).getCost(),
                            decode.getWriting_list().get(i).getMaking_time(),
                            decode.getWriting_list().get(i).getScrap_amount(),
                            decode.getWriting_list().get(i).getPicture_url(),
                            decode.getWriting_list().get(i).getWriting_name(),
                            decode.getWriting_list().get(i).getExplanation()
                    ));
                }
                offset += Integer.parseInt(decode.getCount());
                count += Integer.parseInt(decode.getCount());
                swipeRefreshLayout.setRefreshing(false);
                is_scroll = true;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<KnowHowBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    private void latestParsing() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Log.e("offset", offset + "");
        Call<KnowHowBean> call = connectService.getWritingListLatest(offset);
        call.enqueue(new Callback<KnowHowBean>() {
            @Override
            public void onResponse(Call<KnowHowBean> call, Response<KnowHowBean> response) {
                Log.e("resonpse", response.code() + "");
                KnowHowBean decode = response.body();
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getWriting_list().size() + "");

                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {
                    Log.e("imgUrl", decode.getWriting_list().get(i).getPicture_url());
                    listItem.add(new MainListItem(Integer.parseInt(decode.getWriting_list().get(i).getWriting_unique_key()), decode.getWriting_list().get(i).getCost(),
                            decode.getWriting_list().get(i).getMaking_time(),
                            decode.getWriting_list().get(i).getScrap_amount(),
                            decode.getWriting_list().get(i).getPicture_url(),
                            decode.getWriting_list().get(i).getWriting_name(),
                            decode.getWriting_list().get(i).getExplanation()
                    ));
                }
                offset += Integer.parseInt(decode.getCount());
                count += Integer.parseInt(decode.getCount());
                swipeRefreshLayout.setRefreshing(false);
                is_scroll = true;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<KnowHowBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}
