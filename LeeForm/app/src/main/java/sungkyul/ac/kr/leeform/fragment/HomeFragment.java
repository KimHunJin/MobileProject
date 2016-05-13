package sungkyul.ac.kr.leeform.fragment;

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
        mSpinnerSort = (Spinner)mView.findViewById(R.id.spnKnowSort);

        String[] mCategory = getResources().getStringArray(R.array.category);
        String[] mSort = getResources().getStringArray(R.array.sort);

        ArrayAdapter<String> mSpinnerCategoryAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mCategory);
        ArrayAdapter<String> mSpinnerSortAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mSort);

        mSpinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCategory.setAdapter(mSpinnerCategoryAdapter);
        mSpinnerSort.setAdapter(mSpinnerSortAdapter);


        ListView lst = (ListView) mView.findViewById(R.id.listMain);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),(position+1) + "선택",Toast.LENGTH_SHORT).show();
            }
        });

        init();

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "나와라", Toast.LENGTH_SHORT).show();
            }
        });

        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            listItem.add(new MainListItem(i, "23,000", "4", "2000",R.drawable.tables2));
        }
    }
}
