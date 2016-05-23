package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.CommunityItem;

/**
 * Created by user on 2016-05-16.
 */

public class CommunityListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<CommunityItem> item;
    private int layout;

    public CommunityListAdapter(Context context, int layout, ArrayList<CommunityItem> item) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = item;
        this.layout = layout;
    }

    /** Getter **/
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
            viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.content = (TextView) convertView.findViewById(R.id.contentCommunity);
            viewHolder.replyCount = (TextView) convertView.findViewById(R.id.replyCount);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommunityItem listItem = item.get(position);
        viewHolder.userName.setText(listItem.getcName());
        viewHolder.content.setText(listItem.getcContent());
        viewHolder.replyCount.setText(listItem.getcCount());
        viewHolder.img.setImageResource(listItem.getcImg());

        return convertView;
    }

    class ViewHolder {
        private TextView userName;
        private TextView content;
        private TextView replyCount;
        private ImageView img;
    }
}
