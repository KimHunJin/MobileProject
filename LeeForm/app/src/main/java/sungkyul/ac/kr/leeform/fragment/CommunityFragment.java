package sungkyul.ac.kr.leeform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by HunJin on 2016-05-01.
 */
public class CommunityFragment extends Fragment {

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_community, container, false);
        //fragment_community.xml을 가져와서 보여주겠다(VIew).
        return mView;
    }
}
