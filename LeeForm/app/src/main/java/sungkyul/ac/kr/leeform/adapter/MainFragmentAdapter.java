package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.fragment.CommunityFragment;
import sungkyul.ac.kr.leeform.fragment.HomeFragment;
import sungkyul.ac.kr.leeform.fragment.MaterialFragment;


/**
 * Created by HunJin on 2016-01-16.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"노하우", "재료", "커뮤니티"};
    private Fragment[] fragments = new Fragment[]{new HomeFragment(), new MaterialFragment(), new CommunityFragment()};
    private Context context;

    public MainFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
        TextView tv = (TextView) v.findViewById(R.id.txtTab);
        tv.setText(tabTitles[position]);
        return v;
    }
}
