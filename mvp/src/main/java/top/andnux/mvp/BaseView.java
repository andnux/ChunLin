package top.andnux.mvp;

import android.app.Activity;
import android.os.Bundle;

public interface BaseView {

    void showNoNetworkView();

    void showTimeoutView();

    void showEmptyView();

    void showErrorView();

    void showLoginView();

    void showContentView();

    void toast(String msg);

    void startActivity(Class<? extends Activity> clazz);

    void startActivity(Class<? extends Activity> clazz, Bundle extras);

    void startActivityForResult(Class<? extends Activity> clazz, int requestCode);

    void startActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode);
}
