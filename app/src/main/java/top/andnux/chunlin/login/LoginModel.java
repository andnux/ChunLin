package top.andnux.chunlin.login;

import top.andnux.mvp.BaseModel;
import top.andnux.mvp.Callback;

public class LoginModel extends BaseModel<String, LoginView> {

    public LoginModel(LoginView view) {
        super(view);
    }

    public void login(Callback<String> callback) {
        LoginView view = getView();
        String userName = view.getUserName();
        String password = view.getPassword();
        if (userName.equals("1234") && password.equals("1234")) {
            callback.onSuccess(userName + " : 登录成功");
        } else {
            callback.onFailure("用户名或密码错误");
        }
    }
}
