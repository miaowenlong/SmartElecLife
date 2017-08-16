package com.sgcc.smarteleclife.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.smarteleclife.MainActivity;
import com.sgcc.smarteleclife.MyApp;
import com.sgcc.smarteleclife.Presenter.LoginPresenter;
import com.sgcc.smarteleclife.R;
import com.sgcc.smarteleclife.models.User;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mvpArt.Base.BaseActivity;
import mvpArt.Utils.CommonUtil;
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
                showLoading();
                Observable.timer(2000, TimeUnit.MILLISECONDS).compose(this.<Long>bindToLifecycle()).
                        flatMap(new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(@NonNull Long aLong) throws Exception {
                        User user = new User();
                        user.setPhoneNum(mEditName.getText().toString());
                        user.setPwdSecret(CommonUtil.md5(mEditPwd.getText().toString()));
                        MyApp.daoSession.getUserDao().insert(user);
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
                });
                break;
            case R.id.register:
                break;
            case R.id.forget:
                break;
        }
    }
}
