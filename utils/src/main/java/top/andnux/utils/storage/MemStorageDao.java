package top.andnux.utils.storage;

import android.util.LruCache;

public class MemStorageDao<T> extends BaseStorageDao<T> {

    private LruCache<String, T> mCache;

    public MemStorageDao(Class<T> clazz) {
        super( clazz);
        long size = Runtime.getRuntime().freeMemory() / 8;
        mCache = new LruCache<>((int) size);
    }

    @Override
    public void save(T data) throws Exception {
        mCache.put(data.getClass().getSimpleName(), data);
    }


    @Override
    public T load() throws Exception {
        return mCache.get(mClass.getSimpleName());
    }

    @Override
    public void clear() {
        mCache.remove(mClass.getSimpleName());
    }
}
