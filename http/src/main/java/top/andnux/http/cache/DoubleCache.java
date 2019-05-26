package top.andnux.http.cache;

import android.text.TextUtils;

public class DoubleCache implements Cache<String, String> {

    private Cache<String, String> mMemoryCache;
    private Cache<String, String> mSQLiteCache;

    public DoubleCache() {
        mMemoryCache = new MemoryCache();
        mSQLiteCache = new SQLiteCache();
    }

    @Override
    public void put(String key, String value) {
        mMemoryCache.put(key, value);
        mSQLiteCache.put(key, value);
    }

    @Override
    public String get(String key) {
        String s = mMemoryCache.get(key);
        if (TextUtils.isEmpty(s)) {
            s = mSQLiteCache.get(key);
        }
        return s;
    }

    @Override
    public void clean() {
        mMemoryCache.clean();
        mSQLiteCache.clean();
    }
}