package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.items.CommunityItem;

/**
 * Created by user on 2016-05-18.
 */
public class CommunityReplyLIstAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<CommunityItem> item;
    private int layout;

    public CommunityReplyLIstAdapter(Context context, int layout, ArrayList<CommunityItem> item) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = item;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
