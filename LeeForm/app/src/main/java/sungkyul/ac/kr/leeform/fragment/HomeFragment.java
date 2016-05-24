package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.knowhow.CreateKnowHowActivity;
import sungkyul.ac.kr.leeform.activity.knowhow.KnowhowDetailActivity;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by HunJin on 2016-05-01.
 */
public class HomeFragment extends Fragment {

    private View mView;
    private MainListAdapter adapter;
    private Spinner mSpinnerCategory, mSpinnerSort;

    ArrayList<MainListItem> listItem = new ArrayList<>();


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

        init(); //메소드호출

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
                Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //정렬 아이템 선택했을 때
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
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
}
