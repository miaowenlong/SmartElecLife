package mvpArt.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sgcc.smarteleclife.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import mvpArt.RxUtil.RxBus;
import mvpArt.mvp.BasePresenter;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected P mPresenter;
    protected CompositeDisposable mCompositeDisposable;
    private Disposable mRxSubscribe;

    public ProgressBar mProgressBar;
    private TextView mTitle;
    private TextView mRightBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mPresenter = getPresenter();
        setContentView(initView());
        //设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTitle = (TextView) findViewById(R.id.header_title);
        mRightBtn = (TextView) findViewById(R.id.header_right_tv);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //设置返回键可用
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设置标题文字不可显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);

        initData();

        mRxSubscribe = RxBus.getInstance().tObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.equals("关闭app"))
                            finish();
                    }
                });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if(mCompositeDisposable!=null) mCompositeDisposable.clear();
        mRxSubscribe.dispose();
        this.mPresenter = null;
        this.mUnbinder = null;
    }

    protected abstract void initData();

    protected abstract
    @LayoutRes
    int initView();

    protected abstract P getPresenter();

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    //显示toast
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }


    public void showLoading() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }
    public<T extends Activity> void gotoNextActivity(Class<T> klass){
        startActivity(new Intent(this,klass));
    }
    public Activity getThis() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }


}
