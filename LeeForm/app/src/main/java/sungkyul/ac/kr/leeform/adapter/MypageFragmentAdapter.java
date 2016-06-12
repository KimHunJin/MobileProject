package sungkyul.ac.kr.leeform.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sungkyul.ac.kr.leeform.fragment.MypageScrapFragment;
import sungkyul.ac.kr.leeform.fragment.MypageWriteFragment;

/**
 * Created by user on 2016-06-09.
 */
public class MypageFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"내가 쓴 노하우", "스크랩한 노하우"};
    private Fragment[] fragments = new Fragment[]{new MypageWriteFragment(), new MypageScrapFragment()};
    private Context context;

    public MypageFragmentAdapter(FragmentManager fm, Context context) {
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
