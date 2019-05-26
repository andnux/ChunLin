package top.andnux.chunlin;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import top.andnux.http.HttpCallback;
import top.andnux.http.ParameterizedTypeImpl;

public abstract class JsonCallback2<T> implements HttpCallback {

    private static final String TAG = "JsonCallback";
    private Gson mGson = new Gson();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public abstract void onSuccess(Result<T> data);

    @Override
    public void onSuccess(String data) {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                ParameterizedTypeImpl tp = new ParameterizedTypeImpl(Result.class, new Type[]{types[0]});
                Result<T> result = mGson.fromJson(data, tp);
                if (result.isSuccess()) {
                    mHandler.post(() -> onSuccess(result));
                } else {
                    mHandler.post(() -> onFail(new RuntimeException(result.getReason())));
                }
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