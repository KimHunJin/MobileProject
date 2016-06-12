package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sungkyul.ac.kr.leeform.fragment.CommunityFragment;
import sungkyul.ac.kr.leeform.fragment.HomeFragment;
import sungkyul.ac.kr.leeform.fragment.MaterialFragment;


/**
 * Created by HunJin on 2016-05-10.
 * 가장 기본이 되는 메인 페이지 어댑터
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

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
