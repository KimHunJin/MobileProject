package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.CreateKnowHowItem;

/**
 * Created by HunJin on 2016-05-21.
 * 노하우 작성 상세 어댑터
 */
public class CreateKnowHowGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<CreateKnowHowItem> item;
    private int layout;

    public CreateKnowHowGridAdapter(Context context, int layout, ArrayList<CreateKnowHowItem> item) {
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

        GridViewHolder gridViewHolder;

        if (convertView == null) {
            gridViewHolder = new GridViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            gridViewHolder.imgGridCreate = (ImageView) convertView.findViewById(R.id.imgCreate);
            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        CreateKnowHowItem gridItem = item.get(position);

        if (gridItem.getmImg() != 0) {
            Picasso.with(inflater.getContext()).load(gridItem.getmImg()).resize(480, 480).centerCrop().into(gridViewHolder.imgGridCreate);
        }
        if (gridItem.getImgUrl() != null) {
            Picasso.with(inflater.getContext()).load("file://" + gridItem.getImgUrl().trim().toString()).resize(480, 480).centerCrop().error(R.drawable.panza).into(gridViewHolder.imgGridCreate);
        }
        return convertView;
    }

    class GridViewHolder {
        private ImageView imgGridCreate;
    }
}
