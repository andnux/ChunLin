package top.andnux.mvp;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基本的控制器
 *
 * @param <T> 数据泛型
 * @param <V> 视图泛型
 * @param <M> 模型泛型
 */
public class BasePresenter<T, V extends BaseView,
        M extends BaseModel<T, V>> {

    protected M mModel;
    private V mView;

    /**
     * 可以重写返回数据模型
     *
     * @param view
     * @return M
     */
    protected M instanceModel(V view) {
        try {
            ParameterizedType pType = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            if (pType != null) {
                Type[] types = pType.getActualTypeArguments();
                if (types.length == 3) {
                    Class<M> pClass = (Class<M>) types[types.length - 1];
                    Class<V> vClass = (Class<V>) types[types.length - 2];
                    Constructor<M> constructor = pClass.getConstructor(vClass);
                    return constructor.newInstance(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V view) {
        this.mView = view;
        mModel = instanceModel(view);
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
