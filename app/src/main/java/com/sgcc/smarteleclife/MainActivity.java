package com.sgcc.smarteleclife;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabWidget;
import android.widget.TextView;

import com.sgcc.smarteleclife.Fragments.TabCameraFragment;
import com.sgcc.smarteleclife.Fragments.TabDemandFragment;
import com.sgcc.smarteleclife.Fragments.TabEnergyFragment;
import com.sgcc.smarteleclife.Fragments.TabHomeFragment;
import com.sgcc.smarteleclife.Presenter.MainPresenter;

import butterknife.BindView;
import mvpArt.Base.BaseActivity;
import mvpArt.mvp.IView;
import mvpArt.mvp.Message;

public class MainActivity extends BaseActivity<MainPresenter> implements IView {


    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_right_tv)
    TextView mHeaderRightTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.pb_main)
    ProgressBar mPbMain;
    @BindView(R.id.tabs)
    TabWidget mTabs;
    @BindView(R.id.main_tabhost)
    FragmentTabHost mMainTabhost;
    @BindView(R.id.iv_headicon)
    ImageView mCivHeadicon;
    @BindView(R.id.lv_menu)
    ListView mLvMenu;
    @BindView(R.id.drawer_exit_tv)
    TextView mDrawerExitTv;

    ActionBarDrawerToggle mToggle;
    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerMain;

    @Override
    protected void initData() {
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置标题文字不可显示
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle = new ActionBarDrawerToggle(this,mDrawerMain,mToolbar,R.string.open,R.string.close);
        mDrawerMain.addDrawerListener(mToggle);
        mToggle.syncState();

        initBottomTab();

    }

    private void initBottomTab() {
        View indicator;
        mMainTabhost.setup(this, getSupportFragmentManager(), R.id.main_content);
        //添加tab名字和图标
        indicator = getIndicatorView("智能家居", 0);
        mMainTabhost.addTab(mMainTabhost.newTabSpec("0").setIndicator(indicator)
                , TabHomeFragment.class, null);
        indicator = getIndicatorView("家庭能效", 1);
        mMainTabhost.addTab(mMainTabhost.newTabSpec("1").setIndicator(indicator)
                , TabEnergyFragment.class, null);
        indicator = getIndicatorView("安防监控", 2);
        mMainTabhost.addTab(mMainTabhost.newTabSpec("2").setIndicator(indicator)
                , TabCameraFragment.class, null);
        indicator = getIndicatorView("需求响应", 3);
        mMainTabhost.addTab(mMainTabhost.newTabSpec("4").setIndicator(indicator)
                , TabDemandFragment.class, null);
    }

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter getPresenter() {
        return null;
    }

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

    //设置底部的tab内容
    private View getIndicatorView(String name, int id) {
        View v = getLayoutInflater().inflate(R.layout.tab_indicator, null);
        TextView textView = (TextView) v.findViewById(R.id.tabText);
        textView.setText(name);
        int[] drawables = {R.drawable.tab_home,
                R.drawable.tab_energy,
                R.drawable.tab_camera,
                R.drawable.tab_demand};
        ImageView imageView = (ImageView) v.findViewById(R.id.tabImg);
        imageView.setImageResource(drawables[id]);
        return v;
    }


}
