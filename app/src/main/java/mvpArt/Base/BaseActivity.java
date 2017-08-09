package mvpArt.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sgcc.smarteleclife.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import mvpArt.RxUtil.RxBus;
import mvpArt.mvp.Ipresenter;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public abstract class BaseActivity<P extends Ipresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected P mPresenter;
    protected CompositeDisposable mCompositeDisposable;
    private Disposable mRxSubscribe;

    ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        setContentView(initView());
        //设置toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
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

        mCompositeDisposable = new CompositeDisposable();
        mRxSubscribe = RxBus.getInstance().tObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.equals("关闭app"))
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
        mRxSubscribe.dispose();
        this.mPresenter = null;
        this.mUnbinder = null;
    }

    protected abstract void initData();

    protected abstract @LayoutRes int initView();

    protected abstract P getPresenter();

    protected void addDisposable(Disposable disposable){
        mCompositeDisposable.add(disposable);
    }

    //显示toast
    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int res){
        Toast.makeText(this,res,Toast.LENGTH_SHORT).show();
    }


    public void showLoading() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }
    public void hideLoading() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
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
