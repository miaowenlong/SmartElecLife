package mvpArt.mvp;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

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

    }

    protected Observable<Message> getSubscribe(Message message) {
       return Observable.just(message).
                filter(new Predicate<Message>() {
                    @Override
                    public boolean test(@NonNull Message message) throws Exception {
                        return message != null;
                    }
                });
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
