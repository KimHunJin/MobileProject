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
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by user on 2016-06-10.
 */
public class MypageWriteFragment extends Fragment {
    private int check = 0;
    private View mView;
    private MainListAdapter adapter;

    private static String URL = StaticURL.BASE_URL;

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
        if (check == 1) {
            check = 0;
        } else {
            check = 1;
        }
        leeformParsing();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_mypage, container, false);

        adapter = new MainListAdapter(getContext(), R.layout.item_list_main, listItem);

        ListView lst = (ListView) mView.findViewById(R.id.listMain);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //리스트의 아이템 선택했을 때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itKnowhowDetail = new Intent(getActivity(), KnowHowDetailActivity.class);
                Log.e("img",listItem.get(position).getmUrl());
                itKnowhowDetail.putExtra("image",listItem.get(position).getmUrl()+"");
                itKnowhowDetail.putExtra("knowhowkey",listItem.get(position).getmNumber()+"");
                startActivity(itKnowhowDetail);
//                Toast.makeText(getActivity(),(position+1) + "선택",Toast.LENGTH_SHORT).show();
            }
        });


        leeformParsing();

        return mView;
    }

    //내가 쓴 노하우 가져오기
    private void leeformParsing() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String user_key = SaveDataMemberInfo.getAppPreferences(getContext(), "user_key");
        Call<KnowHowBean> call = connectService.getMyWriteKnowHow(user_key);
        call.enqueue(new Callback<KnowHowBean>() {
            @Override
            public void onResponse(Call<KnowHowBean> call, Response<KnowHowBean> response) {
                Log.e("resonpse", response.code() + "");
                KnowHowBean decode = response.body();
                Log.e("err", decode.getErr());
                Log.e("count", decode.getCount());
              //  Log.e("list size", decode.getWriting_list().size() + "");
                listItem.clear();
                for (int i = 0; i < Integer.parseInt(decode.getCount()); i++) {
                    Log.e("imgUrl", decode.getWriting_list().get(i).getPicture_url());
                    listItem.add(new MainListItem(Integer.parseInt(decode.getWriting_list().get(i).getWriting_unique_key()), decode.getWriting_list().get(i).getPrice(),
                            decode.getWriting_list().get(i).getMaking_time(),
                            decode.getWriting_list().get(i).getScrap_amount(),
                            decode.getWriting_list().get(i).getPicture_url(),
                            decode.getWriting_list().get(i).getWriting_name(),
                            decode.getWriting_list().get(i).getExplanation()
                    ));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<KnowHowBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}
