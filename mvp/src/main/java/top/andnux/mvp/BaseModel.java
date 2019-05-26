package top.andnux.mvp;

public abstract class BaseModel<T, V extends BaseView> {

    private V mView;

    public BaseModel(V view) {
        mView = view;
    }

    public V getView() {
        return mView;
    }
}
