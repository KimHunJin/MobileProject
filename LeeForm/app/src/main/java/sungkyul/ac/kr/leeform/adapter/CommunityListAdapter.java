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
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.utils.EndString;

/**
 * Created by MiSeon on 2016-05-16.
 * 커뮤니티 리스트 어댑터
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

    /**
     * Getter
     **/
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
            viewHolder.txtUserName = (TextView) convertView.findViewById(R.id.txtCommunityUserName);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.contentCommunity);
            viewHolder.txtReplyCount = (TextView) convertView.findViewById(R.id.txtReplyCount);
            viewHolder.txtImg = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.txtCommunityTime = (TextView) convertView.findViewById(R.id.txtCommunityTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommunityItem listItem = item.get(position);
        viewHolder.txtUserName.setText(listItem.getcName());
        String contents = listItem.getcContent();
        if (contents.length() > 70) {
            viewHolder.txtContent.setText(EndString.endString(contents, 70));
        } else {
            viewHolder.txtContent.setText(listItem.getcContent());
        }
        viewHolder.txtReplyCount.setText(listItem.getcCount());
        viewHolder.txtCommunityTime.setText(listItem.getcTime());
        Picasso.with(inflater.getContext()).load(listItem.getcImageURL()).into(viewHolder.txtImg);
        return convertView;
    }

    class ViewHolder {
        private TextView txtUserName;
        private TextView txtContent;
        private TextView txtReplyCount;
        private ImageView txtImg;
        private TextView txtCommunityTime;
    }
}
