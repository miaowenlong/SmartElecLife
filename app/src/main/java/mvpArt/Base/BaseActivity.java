package mvpArt.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mvpArt.mvp.Ipresenter;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public abstract class BaseActivity<P extends Ipresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected P mPresenter;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        setContentView(initView());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        initData();
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



}
