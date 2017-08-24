package com.sgcc.smarteleclife.Activities;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.smarteleclife.Http.models.ReturnDto;
import com.sgcc.smarteleclife.MainActivity;
import com.sgcc.smarteleclife.Presenter.LoginPresenter;
import com.sgcc.smarteleclife.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import mvpArt.Base.BaseActivity;
import mvpArt.Http.ServiceManager;
import mvpArt.RxUtil.RxBus;
import mvpArt.mvp.IView;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/9.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements IView {


    @BindView(R.id.edit_name)
    AutoCompleteTextView mEditName;
    @BindView(R.id.edit_pwd)
    EditText mEditPwd;

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                mEditName.setText(message.str);
                break;
            case 1:
                mEditPwd.setText(message.str);
        }
    }

    @Override
    protected void initData() {

        mEditPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //inputMethodManager.showSoftInput(getWindow().getDecorView(),InputMethodManager.SHOW_FORCED);//显示
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    //attemptLogin();
                    showToast("登录");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int initView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }


    @OnClick({R.id.header_right_tv, R.id.btn_login, R.id.register, R.id.forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_right_tv:
                mPresenter.request(Message.obtain(this, "miao"));
                break;
            case R.id.btn_login:
                /*showLoading();
                Observable.timer(2000, TimeUnit.MILLISECONDS).compose(this.<Long>bindToLifecycle()).
                        flatMap(new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(@NonNull Long aLong) throws Exception {
                        UserDao userDao = MyApp.daoSession.getUserDao();
                        User user0 = userDao.queryBuilder()
                                .where(UserDao.Properties.PhoneNum.eq(mEditName.getText().toString()))
                                .build().unique();
                        if (user0 == null) {
                            User user = new User();
                            user.setId(null);
                            user.setPhoneNum(mEditName.getText().toString());
                            user.setPwdSecret(CommonUtil.md5(mEditPwd.getText().toString()));
                            userDao.insert(user);
                        }else{
                            user0.setPwdSecret(CommonUtil.md5(mEditPwd.getText().toString()));
                            userDao.update(user0);
                        }

                        return null;
                    }
                }).observeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        hideLoading();
                        Intent intent = new Intent(getThis(), MainActivity.class);
                        startActivity(intent);
                    }
                });*/
                break;
            case R.id.register:
                break;
            case R.id.forget:
                break;
        }
    }
    private void attemptLogin(String name,String pwd) {
        showLoading();
        Map<String,String> map = new HashMap();
        map.put("userName", name);
        map.put("loginType", "pt");
        map.put("password", pwd);
        map.put("phoneType", "1");
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        map.put("imei", mTelephonyMgr.getDeviceId());
        // 系统版本:Version
        map.put("version", android.os.Build.VERSION.RELEASE);
        ServiceManager.getInstance().request(1003, map)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ReturnDto>() {
            @Override
            public void accept(ReturnDto returnDto) throws Exception {
                hideLoading();
                Intent intent = new Intent();

                if (returnDto.returnFlag.equals("0")) {
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    intent.setClass(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        RxBus.getInstance().post("关闭app");
    }
}
