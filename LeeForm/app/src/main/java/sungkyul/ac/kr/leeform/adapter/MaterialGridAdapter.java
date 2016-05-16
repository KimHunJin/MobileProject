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
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

/**
 * Created by HunJin on 2016-05-11.
 */
public class MaterialGridAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<MaterialGridItem> item;
    private int layout;

    public MaterialGridAdapter(Context context, int layout, ArrayList<MaterialGridItem> item) {
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

        if(convertView == null) {
            gridViewHolder = new GridViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            gridViewHolder.txtGridMaterial = (TextView)convertView.findViewById(R.id.txtGridMaterialName);
            gridViewHolder.imgGridMaterial = (ImageView)convertView.findViewById(R.id.imgGridMaterial);
            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder)convertView.getTag();
        }

        MaterialGridItem gridItem = item.get(position);
        gridViewHolder.imgGridMaterial.setImageResource(gridItem.getmImg());
        gridViewHolder.txtGridMaterial.setText(gridItem.getmName());

        return convertView;
    }

    class GridViewHolder {
        private ImageView imgGridMaterial;
        private TextView txtGridMaterial;
    }
}
