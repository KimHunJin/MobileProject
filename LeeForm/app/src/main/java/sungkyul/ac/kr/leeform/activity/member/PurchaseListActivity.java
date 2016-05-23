package sungkyul.ac.kr.leeform.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.knowhow.KnowhowDetailActivity;
import sungkyul.ac.kr.leeform.adapter.MainListAdapter;
import sungkyul.ac.kr.leeform.items.MainListItem;

/**
 * Created by Kim on 2016-05-22.
 */
public class PurchaseListActivity extends AppCompatActivity{

    ListView lstPurchaseList;
    private MainListAdapter adapter;
    ArrayList<MainListItem> listItem = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initializeLayout();
        setListener();
        addItem();

    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout(){
        adapter = new MainListAdapter(getApplicationContext(), R.layout.item_list_main, listItem);

        lstPurchaseList = (ListView)findViewById(R.id.lstPurchaseList);
        // 리스트와 어댑터 연결
        lstPurchaseList.setAdapter(adapter);
    }

    /**
     * 컴포넌트에 Listener 설정
     */
    private void setListener(){
        lstPurchaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itKnowhowDetail = new Intent(getApplicationContext(), KnowhowDetailActivity.class);
                startActivity(itKnowhowDetail);
            }
        });
    }

    private void addItem(){
        for (int i = 0; i < 10; i++) {
            listItem.add(new MainListItem(i, "23,000", "4", "2000",R.drawable.tables2)); //리스트에 추가
        }

        adapter.notifyDataSetChanged();
    }
}
