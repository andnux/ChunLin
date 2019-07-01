package top.andnux.http.netstate;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import top.andnux.http.netstate.annotation.NetWork;
import top.andnux.http.utils.Utils;

public class NetStateManager implements NetStateListener {

    private static final NetStateManager ourInstance = new NetStateManager();

    public static NetStateManager getInstance() {
        return ourInstance;
    }

    private Application mApplication;
    private NetStateListener mStateListener;
    private NetStateReceiver mNetBroadcastReceiver;
    private Map<Object, List<NetStateBean>> methodMap = new ArrayMap<>();

    private NetStateManager() {
        mNetBroadcastReceiver = new NetStateReceiver();
        mNetBroadcastReceiver.setNetListener(this);
        init(Utils.getApplicationByReflection());
    }

    public NetStateListener getStateListener() {
        return mStateListener;
    }

    public void setStateListener(NetStateListener stateListener) {
        mStateListener = stateListener;
    }

    private void init(Application context) {
        this.mApplication = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager service = (ConnectivityManager) NetStateManager.getInstance().getApplication().
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = null;
            builder = new NetworkRequest.Builder();
            service.requestNetwork(builder.build(),
                    new NetworkCallback(this));
        } else {
            IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(mNetBroadcastReceiver, filter);
        }
    }

    public Application getApplication() {
        return mApplication;
    }

    public void registerObserver(Object object) {
        List<NetStateBean> methodBeans = methodMap.get(object);
        if (methodBeans == null) {
            methodBeans = findAnnotationMethod(object);
            methodMap.put(object, methodBeans);
        }
    }

    private List<NetStateBean> findAnnotationMethod(Object object) {
        List<NetStateBean> list = new ArrayList<>();
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            NetWork annotation = method.getAnnotation(NetWork.class);
            if (annotation == null) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("NetWork 注解得方法只能有一个参数");
            }
            list.add(new NetStateBean(annotation.value(), parameterTypes[0], method));
        }
        return list;
    }


    public void unRegisterObserver(Object object) {
        List<NetStateBean> methodBeans = methodMap.get(object);
        if (methodBeans != null) {
            methodMap.remove(object);
        }
        Log.e("TAG", "注销成功");
    }

    public void unRegisterAllObservers() {
        if (!methodMap.isEmpty()) {
            methodMap.clear();
        }
        NetStateManager.getInstance().getApplication()
                .unregisterReceiver(mNetBroadcastReceiver);
        methodMap = null;
        Log.e("TAG", "全部注销成功");
    }

    @Override
    public void onConnect(NetType state) {
        postMessage(state);
        if (mStateListener != null) mStateListener.onConnect(state);
    }

    private void postMessage(NetType state) {
        try {
            Set<Object> set = methodMap.keySet();
            for (Object object : set) {
                List<NetStateBean> list = methodMap.get(object);
                if (list != null) {
                    for (NetStateBean bean : list) {
                        Method method = bean.getMethod();
                        switch (bean.getNetState()) {
                            case AUTO:
                                if (state == NetType.WIFI
                                        || state == NetType.FLOW
                                        || state == NetType.NONE) {
                                    invoke(method, object, state);
                                }
                                break;
                            case WIFI:
                                if (state == NetType.WIFI || state == NetType.NONE) {
                                    invoke(method, object, state);
                                }
                                break;
                            case FLOW:
                                if (state == NetType.FLOW || state == NetType.NONE) {
                                    invoke(method, object, state);
                                }
                                break;
                            case NONE:
                                if (state == NetType.NONE) {
                                    invoke(method, object, state);
                                }
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invoke(Method method, Object object, NetType state) {
        try {
            method.setAccessible(true);
            method.invoke(object, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisConnect() {
        postMessage(NetType.NONE);
        if (mStateListener != null) mStateListener.onDisConnect();
    }
}