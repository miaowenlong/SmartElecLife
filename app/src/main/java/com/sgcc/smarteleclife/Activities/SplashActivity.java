package com.sgcc.smarteleclife.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.greendao.gen.UserDao;
import com.sgcc.smarteleclife.Constants;
import com.sgcc.smarteleclife.Http.models.ReturnDto;
import com.sgcc.smarteleclife.MainActivity;
import com.sgcc.smarteleclife.MyApp;
import com.sgcc.smarteleclife.R;
import com.sgcc.smarteleclife.models.User;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import mvpArt.Http.ServiceManager;
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
    private RxPermissions mRxPermissions;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
        mSplashIv.setAlpha(0.2f);
        mSplashIv.animate().alpha(1).setDuration(1500).start();


        mCompositeDisposable = new CompositeDisposable();
        //countTime();

        mRxSubscribe = RxBus.getInstance().tObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.equals("关闭app"))
                        finish();
                    }
                });
        mCompositeDisposable.add(mRxSubscribe);

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted){
                            User user = readDataFromDb();
                            if (user == null) countTime();
                                else attemptLogin(user);
                        }else{
                            countTime();
                        }
                    }
                });

    }


    private void attemptLogin(User user) {
        Map<String,String> map = new HashMap();
        map.put("userName", user.getPhoneNum());
        map.put("loginType", "pt");
        map.put("password", user.getPwdSecret());
        map.put("phoneType", "1");
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        map.put("imei", mTelephonyMgr.getDeviceId());
        // 系统版本:Version
        map.put("version", android.os.Build.VERSION.RELEASE);
        ServiceManager.getInstance().request(1003, map)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ReturnDto>() {
            @Override
            public void accept(ReturnDto returnDto) throws Exception {
                Intent intent = new Intent();

                if (returnDto.returnFlag.equals("0")) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private User readDataFromDb() {
        UserDao userDao = MyApp.daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        if (users != null && users.size() > 0) {
            return users.get(users.size() - 1);
        }
        return null;
    }

    private void countTime() {
        mSubscribe = Observable.timer(Constants.SPLASH_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        mCompositeDisposable.add(mSubscribe);

    }

    @OnClick(R.id.splash_timer_tv)
    public void onViewClicked() {
        mCompositeDisposable.clear();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

}
