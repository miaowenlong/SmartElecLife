package com.sgcc.smarteleclife.Presenter;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import mvpArt.mvp.BasePresenter;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/9.
 */

public class LoginPresenter extends BasePresenter {
    public void request(Message message) {
        addSubscribe(Observable.just(message).
        filter(new Predicate<Message>() {
            @Override
            public boolean test(@NonNull Message message) throws Exception {
                return message != null;
            }
        }).
                subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        message.what = 0;
                        message.str = "18451038987";
                        message.HandleMessageToTargetUnrecycle();
                        message.what = 1;
                        message.str = "miaowenlon";
                        message.HandleMessageToTarget();

                    }
                })
        );

    }
}
