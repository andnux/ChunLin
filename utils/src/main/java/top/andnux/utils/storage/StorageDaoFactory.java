package top.andnux.utils.storage;

import android.util.ArrayMap;

import java.util.Map;

public class StorageDaoFactory {

    private static final Map<String, StorageDao<?>> STRING_DAO_ARRAY_MAP = new ArrayMap<>();

    public static <T> StorageDao<T> getPreDao(Class<T> clazz) {
        StorageDao<?> dao = STRING_DAO_ARRAY_MAP.get(clazz.getSimpleName());
        if (dao == null) {
            dao = new PreStorageDao<T>(clazz);
            STRING_DAO_ARRAY_MAP.put(clazz.getSimpleName(), dao);
        }
        return (StorageDao<T>) dao;
    }

    public static <T> StorageDao<T> getMemDao(Class<T> clazz) {
        StorageDao<?> dao = STRING_DAO_ARRAY_MAP.get(clazz.getSimpleName());
        if (dao == null) {
            dao = new MemStorageDao<T>(clazz);
            STRING_DAO_ARRAY_MAP.put(clazz.getSimpleName(), dao);
        }
        return (StorageDao<T>) dao;
    }
}
