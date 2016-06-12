package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.LicenseItem;
import sungkyul.ac.kr.leeform.items.NoticeItem;

/**
 * Created by HunJin on 2016-06-13.
 */
public class LicenseListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<LicenseItem> item;
    private int layout;

    public LicenseListAdapter(Context context, int layout, ArrayList<LicenseItem> item) {
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
            viewHolder.txtLiccenseName = (TextView) convertView.findViewById(R.id.txtLicenseName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LicenseItem listItem = item.get(position);
        viewHolder.txtLiccenseName.setText(listItem.getLicenseName().toString());
        return convertView;
    }

    class ViewHolder {
        private TextView txtLiccenseName;
    }
}
