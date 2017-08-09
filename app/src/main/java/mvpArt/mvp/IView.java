package mvpArt.mvp;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public interface IView {

    //显示信息
    void showMessage(String message);
    //处理消息
    void handleMessage(Message message);
}
