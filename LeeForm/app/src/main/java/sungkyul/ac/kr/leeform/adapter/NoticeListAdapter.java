package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.NoticeItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class NoticeListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<NoticeItem> item;
    private int layout;

    public NoticeListAdapter(Context context, int layout, ArrayList<NoticeItem> item) {
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
            viewHolder.txtNoticeName = (TextView) convertView.findViewById(R.id.txtNoticeName);
            viewHolder.txtNoticeDate = (TextView) convertView.findViewById(R.id.txtNoticeDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NoticeItem listItem = item.get(position);
        viewHolder.txtNoticeName.setText(listItem.getName().toString());
        viewHolder.txtNoticeDate.setText(listItem.getDate().toString());
        return convertView;
    }

    class ViewHolder {
        private TextView txtNoticeName;
        private TextView txtNoticeDate;
    }
}
