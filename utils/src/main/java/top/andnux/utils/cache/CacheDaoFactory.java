package top.andnux.utils.cache;

import android.content.Context;
import android.util.ArrayMap;

import java.util.Map;

public class CacheDaoFactory {

    private static final Map<String, CacheDao<?>> STRING_DAO_ARRAY_MAP = new ArrayMap<>();

    public static <T> CacheDao<T> getPreDao(Class<T> clazz) {
        CacheDao<?> dao = STRING_DAO_ARRAY_MAP.get(clazz.getSimpleName());
        if (dao == null) {
            dao = new PreCacheDao<T>(clazz);
            STRING_DAO_ARRAY_MAP.put(clazz.getSimpleName(), dao);
        }
        return (CacheDao<T>) dao;
    }

    public static <T> CacheDao<T> getMemDao( Class<T> clazz) {
        CacheDao<?> dao = STRING_DAO_ARRAY_MAP.get(clazz.getSimpleName());
        if (dao == null) {
            dao = new MemCacheDao<T>(clazz);
            STRING_DAO_ARRAY_MAP.put(clazz.getSimpleName(), dao);
        }
        return (CacheDao<T>) dao;
    }
}
