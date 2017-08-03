package mvpArt.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public class BasePresenter implements Ipresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {

    }


    @Override
    public void onDestroy() {
        unSubscribe();
        this.mCompositeDisposable.clear();
    }


    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
