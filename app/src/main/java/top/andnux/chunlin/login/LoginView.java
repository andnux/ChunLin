package top.andnux.chunlin.login;

import top.andnux.mvp.BaseView;

public interface LoginView extends BaseView {

    String getUserName();

    String getPassword();

    void setUserNameError(String error);

    void setPasswordError(String error);

}
