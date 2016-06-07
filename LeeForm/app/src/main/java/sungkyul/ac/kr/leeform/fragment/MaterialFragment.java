package sungkyul.ac.kr.leeform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.activity.material.MaterialDetailActivity;
import sungkyul.ac.kr.leeform.adapter.MaterialGridAdapter;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

/**
 * Created by KyungHee on 2016-05-12.
 */
public class MaterialFragment extends Fragment {

    private View mView;
    private GridView grvMaterial;
    private MaterialGridAdapter mAdapter;

    ArrayList<MaterialGridItem> gridItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_material, container, false);

        grvMaterial = (GridView) mView.findViewById(R.id.grvMaterial);

        mAdapter = new MaterialGridAdapter(getContext(), R.layout.item_grid_material, gridItems);

        grvMaterial.setAdapter(mAdapter);

        init();

        grvMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itMaterialDetail = new Intent(getActivity().getApplicationContext(), MaterialDetailActivity.class);
                startActivity(itMaterialDetail);
            }
        });

        return mView;
    }

    void init() {
        for (int i = 0; i < 10; i++) {
            gridItems.add(new MaterialGridItem(i, "판자", R.drawable.panza));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
