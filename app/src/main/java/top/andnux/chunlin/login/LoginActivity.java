package top.andnux.chunlin.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import top.andnux.chunlin.R;
import top.andnux.mvp.BaseActivity;
import top.andnux.base.annotation.ContentView;

@ContentView(R.layout.a_login)
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter>
        implements LoginView {

    private EditText mName;
    private EditText mPwd;

    @Override
    protected void onCreated(@Nullable Bundle savedInstanceState) {
        mName = findViewById(R.id.mName);
        mPwd = findViewById(R.id.mPwd);
    }

    @Override
    public String getUserName() {
        return mName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPwd.getText().toString();
    }

    @Override
    public void setUserNameError(String error) {
        mName.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        mPwd.setError(error);
    }

    public void login(View view) {
        mPresenter.login();
    }

    @Override
    public void refreshClick() {

    }

    @Override
    public void loginClick() {

    }
}
