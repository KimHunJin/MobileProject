package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.community.CommunityCreateActivity;
import sungkyul.ac.kr.leeform.activity.knowhow.CreateKnowHowActivity;
import sungkyul.ac.kr.leeform.adapter.CommunityListAdapter;
import sungkyul.ac.kr.leeform.items.CommunityItem;

/**
 * Created by HunJin on 2016-05-01.
 */
public class CommunityFragment extends Fragment {

    private View cView;
    private CommunityListAdapter adapter;

    ArrayList<CommunityItem> listItem = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        cView= inflater.inflate(R.layout.fragment_community,container,false);
        adapter = new CommunityListAdapter(getContext(), R.layout.item_list_community, listItem);


        ListView lst = (ListView) cView.findViewById(R.id.listCommunity);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), (position + 1) + "선택", Toast.LENGTH_SHORT).show();
            }
        });
        init();

        FloatingActionButton fab1 = (FloatingActionButton) cView.findViewById(R.id.fab1); //작성하기 버튼
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommunityCreateActivity.class));
            }
        });
        return cView;


    }
    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new CommunityItem("박현경","5","ㅏ하하하하ㅏㅎ",R.drawable.circle_img)); //리스트에 추가
        }
    }
}
