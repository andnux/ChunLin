package top.andnux.chunlin.login;

import top.andnux.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter
        <LoginView> {

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        LoginView view = getView();
        String userName = view.getUserName();
        String password = view.getPassword();
    }
}
