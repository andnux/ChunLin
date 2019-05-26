package top.andnux.chunlin.login;


import top.andnux.mvp.BasePresenter;
import top.andnux.mvp.Callback;

public class LoginPresenter extends BasePresenter
        <String, LoginView, LoginModel> {

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        LoginView view = getView();
        mModel.login(new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                view.toast(data);
            }

            @Override
            public void onFailure(String msg) {
                view.toast(msg);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
