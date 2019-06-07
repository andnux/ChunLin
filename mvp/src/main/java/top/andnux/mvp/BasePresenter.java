package top.andnux.mvp;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基本的控制器
 *
 * @param <V> 视图泛型
 */
public class BasePresenter< V extends BaseView> {

    private V mView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.mView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 获得View
     *
     * @return V
     */
    public V getView() {
        return mView;
    }
}
