package top.andnux.chunlin.login;


import top.andnux.http.HttpCallback;
import top.andnux.http.HttpManager;
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
        HttpManager.getInstance().with("https://www.baidu.com")
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        view.toast(data);
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                })
                .execute();
    }
}
