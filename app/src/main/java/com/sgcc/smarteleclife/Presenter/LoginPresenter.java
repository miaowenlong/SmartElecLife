package com.sgcc.smarteleclife.Presenter;

import com.sgcc.greendao.gen.UserDao;
import com.sgcc.smarteleclife.Activities.LoginActivity;
import com.sgcc.smarteleclife.Http.models.ReturnDto;
import com.sgcc.smarteleclife.MainActivity;
import com.sgcc.smarteleclife.MyApp;
import com.sgcc.smarteleclife.models.User;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import mvpArt.Http.ServiceManager;
import mvpArt.Utils.CommonUtil;
import mvpArt.mvp.BasePresenter;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/9.
 */

public class LoginPresenter extends BasePresenter {
    public void request(Message message) {
        addSubscribe(getSubscribe(message).
                subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        message.what = 0;
                        message.str = "18451038987";
                        message.HandleMessageToTargetUnrecycle();
                        message.what = 1;
                        message.str = "123456";
                        message.HandleMessageToTarget();

                    }
                })
        );
    }

    public void login(Message message) {
        addSubscribe(getSubscribe(message).subscribe(new Consumer<Message>() {
            @Override
            public void accept(Message message) throws Exception {
                final LoginActivity loginActivity = (LoginActivity) message.getTarget();
                final Map<String, String> map = (Map) message.obj;
                ServiceManager.getInstance().request(1003, map)
                        .compose(loginActivity.<ReturnDto>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ReturnDto>() {
                            @Override
                            public void accept(ReturnDto returnDto) throws Exception {
                                loginActivity.hideLoading();
                                if (returnDto.returnFlag.equals("0")) {
                                    addUser(map.get("userName"), map.get("password"));
                                    loginActivity.gotoNextActivity(MainActivity.class);
                                } else {
                                    loginActivity.showToast(returnDto.returnMsg);
                                }
                            }
                        });


            }
        }));
    }

    private void addUser(String name, String pwd) {
        UserDao userDao = MyApp.daoSession.getUserDao();
        User user0 = userDao.queryBuilder()
                .where(UserDao.Properties.PhoneNum.eq(name))
                .build().unique();
        if (user0 == null) {
            User user = new User();
            user.setId(null);
            user.setPhoneNum(name);
            user.setPwdSecret(CommonUtil.md5(pwd));
            userDao.insert(user);
        } else {
            user0.setPwdSecret(pwd);
            userDao.update(user0);
        }
    }
}
