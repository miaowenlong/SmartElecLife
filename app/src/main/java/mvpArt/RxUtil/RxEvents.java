package mvpArt.RxUtil;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public class RxEvents<T> {
    //所有事件的code
    public static final int TAP = 1;//点击事件
    public static final int Other = 21;//其他事件

    //枚举
    @IntDef({TAP,Other})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode{}

    public @RxEvents.EventCode int code;
    public T content;

    public static <O> RxEvents<O> setContent(O content) {
        RxEvents<O> events = new RxEvents<>();
        events.content = content;
        return events;
    }


    public T getContent() {
        return content;
        
    }
}
