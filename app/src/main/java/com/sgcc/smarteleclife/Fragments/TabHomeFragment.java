package com.sgcc.smarteleclife.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgcc.smarteleclife.R;

import mvpArt.Base.BaseFragment;
import mvpArt.mvp.IView;
import mvpArt.mvp.Ipresenter;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/7.
 */

public class TabHomeFragment extends BaseFragment implements IView{

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tab_home,container,false);
    }

    @Override
    protected Ipresenter getPresenter() {
        return null;
    }
}
