package com.sgcc.smarteleclife.Fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sgcc.smarteleclife.Activities.VideoActivity;
import com.sgcc.smarteleclife.R;

import butterknife.BindView;
import mvpArt.Base.BaseFragment;
import mvpArt.mvp.IView;
import mvpArt.mvp.Ipresenter;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/7.
 */

public class TabHomeFragment extends BaseFragment implements IView {
    @BindView(R.id.btn_detail)
    Button mBtnDetail;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager mLinearLayoutManager;
    /**上一次播放的下表*/
    private int mPrePlayPosition = -1;
    private final String VIDEO_URL = "http://baobab.wdjcdn.com/14564977406580.mp4";
    @Override
    public void handleMessage(Message message) {

    }

    @Override
    protected void initData() {
        mBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(VideoActivity.class);
            }
        });
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tab_home, container, false);
    }

    @Override
    protected Ipresenter getPresenter() {
        return null;
    }

}
