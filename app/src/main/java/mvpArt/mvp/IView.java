package mvpArt.mvp;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public interface IView {
    //显示加载
    void showLoading();
    //隐藏加载
    void hideLoading();
    //显示信息
    void showMessage();
    //处理消息
    void handleMessage();
}
