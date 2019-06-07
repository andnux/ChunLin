package top.andnux.utils.cache;

import android.content.Context;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.andnux.utils.Utils;

public abstract class BaseCacheDao<T> implements CacheDao<T> {

    protected final ExecutorService mService;
    protected final Class<T> mClass;
    protected final Context mContext;

    public BaseCacheDao(Class<T> clazz) {
        mClass = clazz;
        mContext = Utils.getApp();
        mService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void save(final T data, final CacheListener<T> listener) {
        mService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    save(data);
                    if (listener != null) listener.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) listener.onFail(e);
                }
                if (listener != null) listener.onComplete();
            }
        });

    }

    @Override
    public void load(final CacheListener<T> listener) {
        mService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    T data = load();
                    if (data != null) {
                        if (listener != null) listener.onSuccess(data);
                    } else {
                        if (listener != null) listener.onFail(new RuntimeException("读取到的对象为空"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) listener.onFail(e);
                }
                if (listener != null) listener.onComplete();
            }
        });
    }

    @Override
    public void shutdown() {
        if (mService != null && !mService.isShutdown()) {
            mService.shutdown();
        }
    }
}
