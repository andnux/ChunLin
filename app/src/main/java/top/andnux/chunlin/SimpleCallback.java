package top.andnux.chunlin;

import top.andnux.mvp.Callback;

public abstract class SimpleCallback<T> implements Callback<T> {

    @Override
    public void onSuccess(T data) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onComplete() {

    }
}
