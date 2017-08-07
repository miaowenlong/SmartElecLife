package com.sgcc.smarteleclife.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgcc.smarteleclife.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by miao_wenlong on 2017/8/7.
 */

public class FragmentCommon extends RxFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*TextView textView = new TextView(this.getContext());
        textView.setText("普通fragmentRx");*/
        return inflater.inflate(R.layout.fragment_tab_home,container,false);
    }
}
