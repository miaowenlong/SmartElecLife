package com.sgcc.smarteleclife.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.smarteleclife.Constants;
import com.sgcc.smarteleclife.MainActivity;
import com.sgcc.smarteleclife.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import mvpArt.RxUtil.RxBus;

/**
 * Created by miao_wenlong on 2017/8/8.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.splash_iv)
    ImageView mSplashIv;
    @BindView(R.id.splash_timer_tv)
    TextView mSplashTimerTv;
    private Disposable mSubscribe,mRxSubscribe;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
        mSplashIv.setAlpha(0.2f);
        mSplashIv.animate().alpha(1).setDuration(1500).start();

        mCompositeDisposable = new CompositeDisposable();
        countTime();
        mRxSubscribe = RxBus.getInstance().tObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.equals("关闭app"))
                        finish();
                    }
                });
        mCompositeDisposable.add(mRxSubscribe);
    }

    private void countTime() {
        mSubscribe = Observable.timer(Constants.SPLASH_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        mCompositeDisposable.add(mSubscribe);

    }

    @OnClick(R.id.splash_timer_tv)
    public void onViewClicked() {
        mSubscribe.dispose();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
