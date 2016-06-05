package sungkyul.ac.kr.leeform.utils;

import android.content.Context;
import android.widget.TextView;

import com.hkm.slider.Indicators.NumContainer;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by YongHoon on 2016-05-21.
 */
public class NumZeroForm extends NumContainer<TextView> {
    public NumZeroForm(Context c) {
        super(c, R.layout.numfield);
    }
}
