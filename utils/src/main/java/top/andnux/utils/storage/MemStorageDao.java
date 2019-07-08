package top.andnux.utils.storage;

import android.text.TextUtils;
import android.util.LruCache;

import top.andnux.utils.storage.annotation.FileName;

public class MemStorageDao<T> extends BaseStorageDao<T> {

    private LruCache<String, T> mCache;
    private String fileName;

    public MemStorageDao(Class<T> clazz) {
        super( clazz);
        long size = Runtime.getRuntime().freeMemory() / 8;
        mCache = new LruCache<>((int) size);
        FileName annotation = clazz.getAnnotation(FileName.class);
        String value = clazz.getCanonicalName();
        if (annotation != null && !TextUtils.isEmpty(annotation.value())){
            value = annotation.value();
        }
        fileName = value;
    }

    @Override
    public void save(T data) throws Exception {
        mCache.put(fileName, data);
    }

    @Override
    public T load() throws Exception {
        return mCache.get(fileName);
    }

    @Override
    public void clear() {
        mCache.remove(fileName);
    }
}
