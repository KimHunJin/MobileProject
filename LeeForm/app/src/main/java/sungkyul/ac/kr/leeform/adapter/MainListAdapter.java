package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.MainListItem;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;

/**
 * Created by HunJin on 2016-05-12.
 * 노하우 리스트 어댑터
 */
public class MainListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<MainListItem> item;
    private int layout;
    Handler handler = new Handler();

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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainListItem listItem = item.get(position);
//        mairListImageSetting(viewHolder.imgMainList,listItem.getmUrl());
        Log.e("real url", listItem.getmUrl());
        new DownloadImageTask(viewHolder.imgMainList).execute(listItem.getmUrl());
        viewHolder.txtMainCost.setText(listItem.getmCost());
        viewHolder.txtMainLike.setText(listItem.getmLike());
        viewHolder.txtMainTime.setText(listItem.getmTime());
        viewHolder.txtMainName.setText(listItem.getmName());
        viewHolder.txtMainKeyWord.setText(listItem.getmKeyWord());

        return convertView;
    }

    class ViewHolder {
        private ImageView imgMainList;
        private TextView txtMainLike;
        private TextView txtMainTime;
        private TextView txtMainCost;
        private TextView txtMainName;
        private TextView txtMainKeyWord;
    }
}
