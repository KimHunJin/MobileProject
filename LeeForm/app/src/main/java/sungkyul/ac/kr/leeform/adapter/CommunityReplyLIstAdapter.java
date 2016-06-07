package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.ReplyItem;

/**
 * Created by misun on 2016-05-18.
 * 커뮤니티 댓글 어댑터
 */
public class CommunityReplyLIstAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ReplyItem> item;
    private int layout;

    public CommunityReplyLIstAdapter(Context context, int layout, ArrayList<ReplyItem> item) {
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
            viewHolder.userName = (TextView) convertView.findViewById(R.id.rUserName);
            viewHolder.content = (TextView) convertView.findViewById(R.id.rContent);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.rImg);
            viewHolder.txtReplyTime = (TextView) convertView.findViewById(R.id.txtReplyTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReplyItem listItem = item.get(position);
        viewHolder.userName.setText(listItem.getrName());
        viewHolder.content.setText(listItem.getrContent());
        Picasso.with(inflater.getContext()).load(listItem.getrImg()).into(viewHolder.img);
        viewHolder.txtReplyTime.setText(listItem.getrTime());

        return convertView;
    }

    class ViewHolder {
        private TextView userName;
        private TextView content;
        private ImageView img;
        private TextView txtReplyTime;
    }
}
