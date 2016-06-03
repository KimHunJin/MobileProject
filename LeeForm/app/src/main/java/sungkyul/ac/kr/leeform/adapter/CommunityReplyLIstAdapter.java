package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.CommunityItem;
import sungkyul.ac.kr.leeform.items.ReplyItem;

/**
 * Created by user on 2016-05-18.
 */
public class CommunityReplyLIstAdapter extends BaseAdapter{
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
            viewHolder.img=(ImageView)convertView.findViewById(R.id.rImg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReplyItem listItem = item.get(position);
        viewHolder.userName.setText(listItem.getrName());
        viewHolder.content.setText(listItem.getrContent());
        //viewHolder.img.setImageResource(listItem.getrImg());
        new DownloadImageTask(viewHolder.img).execute(listItem.getrImg());

        return convertView;
    }
    // 비동기식으로 이미지를 가지고 온다.
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    class ViewHolder {
        private TextView userName;
        private TextView content;
        private ImageView img;
    }
}
