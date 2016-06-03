package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sungkyul.ac.kr.leeform.activity.knowhow.CreateKnowHowActivity;
import sungkyul.ac.kr.leeform.activity.knowhow.KnowhowDetailActivity;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.WritingBean;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by HunJin on 2016-05-01.
 */
public class HomeFragment extends Fragment {
    private int check=0;
    private View mView;
    private MainListAdapter adapter;
    private Spinner mSpinnerCategory, mSpinnerSort;

    private static String URL = "http://14.63.196.255/api/";

    ArrayList<MainListItem> listItem = new ArrayList<>();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        adapter = new MainListAdapter(getContext(), R.layout.item_list_main, listItem);

        mSpinnerCategory = (Spinner)mView.findViewById(R.id.spnKnowCategory);
        //(Spinner)findViewById(R.id.spnKnowCategory); 오류=>보여줄 View가 없어
        mSpinnerSort = (Spinner)mView.findViewById(R.id.spnKnowSort);

        String[] mCategory = getResources().getStringArray(R.array.category); //카테고리의 내용들을 배열(mCategory)에 저장
        String[] mSort = getResources().getStringArray(R.array.sort); //정렬의 내용들을 배열(mSort)에 저장

        ArrayAdapter<String> mSpinnerCategoryAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mCategory);
         //기본으로 제공하는 layout(simple_spinner_item), 리스트에 있는 내용을 mCategory의 내용으로 채우기 위해 ArrayAdapter 이용
        ArrayAdapter<String> mSpinnerSortAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mSort);

        mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter를 통해 리스트 형태로 늘리는 메소드
        mSpinnerSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCategory.setAdapter(mSpinnerCategoryAdapter); //스피너에 adapter 설정
        mSpinnerSort.setAdapter(mSpinnerSortAdapter);


        ListView lst = (ListView) mView.findViewById(R.id.listMain);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itKnowhowDetail = new Intent(getActivity().getApplicationContext(), KnowhowDetailActivity.class);
                startActivity(itKnowhowDetail);
//                Toast.makeText(getActivity(),(position+1) + "선택",Toast.LENGTH_SHORT).show();
            }
        });

//        init(); //메소드호출
        leeformParsing();

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab); //작성하기 버튼
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateKnowHowActivity.class));
            }
        });

        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //카테고리 아이템 선택했을 때
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                check++;
                // 처음에는 실행 안되게
                if(check>2){
                    Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
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
                if(check>2){
                    Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return mView;
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new MainListItem(i, "23,000", "4", "2000",R.drawable.tables2)); //리스트에 추가
        }
    }

    private void leeformParsing() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<WritingBean> call = connectService.getWritingList();
        call.enqueue(new Callback<WritingBean>() {
            @Override
            public void onResponse(Call<WritingBean> call, Response<WritingBean> response) {
                Log.e("resonpse", response.code() + "");
                WritingBean decode = response.body();
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
                Log.e("list size", decode.getWriting_list().size() + "");
                listItem.clear();
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {

                    listItem.add(new MainListItem(i, decode.getWriting_list().get(i).getPrice(), decode.getWriting_list().get(i).getMaking_time(),decode.getWriting_list().get(i).getWriting_name(),R.drawable.tables2));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WritingBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}
