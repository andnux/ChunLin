package top.andnux.http.cache;

import android.util.LruCache;

public class MemoryCache implements Cache<String, String> {

    private LruCache<String, String> mCache;

    public MemoryCache() {
        int size = (int) (Runtime.getRuntime().freeMemory() / 8);
        mCache = new LruCache<>(size);
    }

    @Override
    public void put(String key, String value) {
        mCache.put(key, value);
    }

    @Override
    public String get(String key) {
        return mCache.get(key);
    }

    @Override
    public void clean() {
        mCache.evictAll();
    }
}
