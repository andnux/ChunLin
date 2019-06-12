package top.andnux.utils.cache;

public interface CacheListener<T> {

    void onSuccess(T data);

    default void onFail(Exception e) {

    }

    default void onComplete() {

    }
}
