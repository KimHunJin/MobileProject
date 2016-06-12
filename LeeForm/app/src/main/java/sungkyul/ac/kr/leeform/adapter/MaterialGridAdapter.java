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
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

/**
 * Created by KyungHee on 2016-05-12.
 * 재료 리스트 어댑터
 */
public class MaterialGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<MaterialGridItem> item;
    private int layout;
    MaterialGridItem gridItem;

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

        if (convertView == null) {
            gridViewHolder = new GridViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            gridViewHolder.txtGridMaterial = (TextView) convertView.findViewById(R.id.txtGridMaterialName);
            gridViewHolder.imgGridMaterial = (ImageView) convertView.findViewById(R.id.imgGridMaterial);
            gridViewHolder.txtGridMaterialCost = (TextView) convertView.findViewById(R.id.txtGridMaterialCost);
            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        gridItem = item.get(position);

        Picasso.with(inflater.getContext()).load(gridItem.getmUrl()).into(gridViewHolder.imgGridMaterial);
        gridViewHolder.txtGridMaterial.setText(gridItem.getmName());
        gridViewHolder.txtGridMaterialCost.setText(gridItem.getmPrice());

        return convertView;
    }

    class GridViewHolder {
        private ImageView imgGridMaterial;
        private TextView txtGridMaterial;
        private TextView txtGridMaterialCost;
    }
}
