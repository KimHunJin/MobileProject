package sungkyul.ac.kr.leeform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CommunityListAdapter;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.items.MainListItem;

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
<<<<<<< HEAD
        mView = inflater.inflate(R.layout.fragment_community, container, false);
        return mView;
=======

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

        return cView;

    }
    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new CommunityItem("박현경","5","ㅏ하하하하ㅏㅎ",R.drawable.circle_img)); //리스트에 추가
        }
>>>>>>> ccd7ace25051646f65d19494cc1974ac83860dc0
    }
}
