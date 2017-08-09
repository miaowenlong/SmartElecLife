package com.sgcc.smarteleclife.Activities;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sgcc.smarteleclife.Presenter.LoginPresenter;
import com.sgcc.smarteleclife.R;

import butterknife.BindView;
import butterknife.OnClick;
import mvpArt.Base.BaseActivity;
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
                    InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    //inputMethodManager.showSoftInput(getWindow().getDecorView(),InputMethodManager.SHOW_FORCED);//显示
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    //attemptLogin();
                    Toast.makeText(LoginActivity.this, "登录", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.register:
                break;
            case R.id.forget:
                break;
        }
    }
}
