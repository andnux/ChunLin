package top.andnux.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class JsonCallback<T> implements HttpCallback {

    private static final String TAG = "JsonCallback";
    private Gson mGson = new Gson();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public abstract void onSuccess(T data);

    @Override
    public void onSuccess(String data) {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                T result = mGson.fromJson(data, types[0]);
                mHandler.post(() -> onSuccess(result));
            } else {
                mHandler.post(() -> onFail(new RuntimeException("不支持的解析")));
            }
        } catch (Exception e) {
            mHandler.post(() -> onFail(e));
        }
    }

    @Override
    public void onFail(Exception e) {
        Log.e(TAG, "onFail() called with: e = [" + e + "]");
    }
}