package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityWritingBean;
import sungkyul.ac.kr.leeform.dto.OnlyErrBean;
import sungkyul.ac.kr.leeform.items.MainListItem;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by HunJin on 2016-05-12.
 * 노하우 리스트 어댑터
 */
public class MainListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<MainListItem> item;
    private int layout;
    Handler handler = new Handler();
    String errCode = "0";

    public MainListAdapter(Context context, int layout, ArrayList<MainListItem> item) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = item;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder.txtMainCost = (TextView) convertView.findViewById(R.id.txtListCost);
            viewHolder.txtMainTime = (TextView) convertView.findViewById(R.id.txtListTime);
            viewHolder.txtMainLike = (TextView) convertView.findViewById(R.id.txtListLike);
            viewHolder.imgMainList = (ImageView) convertView.findViewById(R.id.imgListItem);
            viewHolder.txtMainName = (TextView)convertView.findViewById(R.id.txtListName);
            viewHolder.txtMainKeyWord = (TextView)convertView.findViewById(R.id.txtListKeyWord);
            viewHolder.imgMainLike = (ImageView)convertView.findViewById(R.id.imgMainListLike);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainListItem listItem = item.get(position);
        checkLike(listItem.getmNumber()+"",viewHolder.imgMainLike);
        Log.e("real number", listItem.getmNumber()+"");
//        mairListImageSetting(viewHolder.imgMainList,listItem.getmUrl());
        Log.e("real url", listItem.getmUrl());
        new DownloadImageTask(viewHolder.imgMainList).execute(listItem.getmUrl());
        viewHolder.txtMainCost.setText(listItem.getmCost());
        viewHolder.txtMainLike.setText(listItem.getmLike());
        viewHolder.txtMainTime.setText(listItem.getmTime());
        viewHolder.txtMainName.setText(listItem.getmName());
        viewHolder.txtMainKeyWord.setText(listItem.getmKeyWord());


//        Log.e("errMainCheck",errCode);
//        if(errCode.equals("3")) {
//            viewHolder.imgMainLike.setImageDrawable(convertView.getResources().getDrawable(R.drawable.main_list_like));
//        } else {
//            viewHolder.imgMainLike.setImageDrawable(convertView.getResources().getDrawable(R.drawable.main_list_unlike));
//        }


        return convertView;
    }

    class ViewHolder {
        private ImageView imgMainLike;
        private ImageView imgMainList;
        private TextView txtMainLike;
        private TextView txtMainTime;
        private TextView txtMainCost;
        private TextView txtMainName;
        private TextView txtMainKeyWord;
    }

    private void checkLike(String writingKey, final ImageView img) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Map<String, String> data = new HashMap<>();
        String key = SaveDataMemberInfo.getAppPreferences(inflater.getContext(), "user_key");
        Log.e("key", key);
        Log.e("writing_key",writingKey);
        data.put("user_unique_key", key); //user_unique_key 가져오기
        data.put("writing_unique_key", writingKey);

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<OnlyErrBean> call = connectService.getCheckScrap(data);
        call.enqueue(new Callback<OnlyErrBean>() {
            @Override
            public void onResponse(Call<OnlyErrBean> call, Response<OnlyErrBean> response) {
                OnlyErrBean decodedResponse = response.body();
                errCode = decodedResponse.getErr();
                Log.e("real err", decodedResponse.getErr());
                if(decodedResponse.getErr().equals("3")) {
                    img.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.main_list_like));
                } else {
                    img.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.main_list_unlike));
                }
            }

            @Override
            public void onFailure(Call<OnlyErrBean> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }
}
