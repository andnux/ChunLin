package top.andnux.http.cache;

import android.util.Log;

import java.util.List;

import top.andnux.http.db.DaoSupportFactory;
import top.andnux.http.db.IDaoSupport;

public class SQLiteCache implements Cache<String, String> {

    private IDaoSupport<HttpCache> mDaoSupport;

    public SQLiteCache() {
        mDaoSupport = DaoSupportFactory.getFactory().getDao(HttpCache.class);
    }

    @Override
    public void put(String key, String value) {
        HttpCache cache = new HttpCache();
        cache.setUrl(key);
        cache.setData(value);
        long id = mDaoSupport.insert(cache);
        Log.e("TAG", "ID = " + id);
    }

    @Override
    public String get(String key) {
        HttpCache where = new HttpCache();
        where.setUrl(key);
        List<HttpCache> list = mDaoSupport.query(where);
        if (list.size() > 0) {
            return list.get(0).getData();
        }
        return null;
    }

    @Override
    public void clean() {
        mDaoSupport.delete(null);
    }
}