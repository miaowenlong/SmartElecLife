package mvpArt.RxUtil;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public class RxBus {
    private static RxBus defaultInstance = new RxBus();

    private final Subject<Object> bus;
    //PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus(){
        bus = PublishSubject.create().toSerialized();
    }

    //单例rxbus
    public static RxBus getInstance(){
        return defaultInstance;
    }

    //发送一个新事件
    public void post(Object o) {
        bus.onNext(o);
    }

    //根据传递事件的eventType类型返回特点类型(eventType)的被观察者
    public <T> Observable<T> tObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
