package top.andnux.chunlin;

import top.andnux.mvp.BaseActivity;
import top.andnux.mvp.BaseModel;
import top.andnux.mvp.BasePresenter;
import top.andnux.mvp.BaseView;

public abstract class CLActivity<T, V extends BaseView, M extends BaseModel<T, V>,
        P extends BasePresenter<T, V, M>> extends BaseActivity<T, V, M, P> {

    @Override
    public void refreshClick() {

    }

    @Override
    public void loginClick() {

    }
}
