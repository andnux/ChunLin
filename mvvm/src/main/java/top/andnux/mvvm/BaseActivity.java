package top.andnux.mvvm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.andnux.base.ContentViewInject;
import top.andnux.ui.statelayout.StateLayout;

/**
 * 泛型参数结束
 */
public abstract class BaseActivity extends AppCompatActivity implements StateLayout.OnViewRefreshListener {

    protected StateLayout mStateLayout;

    protected Object getLayoutView(LayoutInflater inflater) {
        return null;
    }

    protected abstract void onCreated(@Nullable Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        Object view = getLayoutView(inflater);
        if (view != null) {
            if (view instanceof View) {
                setContentView((View) view);
            } else if (view instanceof Integer) {
                setContentView((Integer) view);
            }
        } else {
            ContentViewInject.inject(this);
        }
        mStateLayout = findViewById(R.id.stateLayout);
        if (mStateLayout != null) {
            mStateLayout.setRefreshListener(this);
        }
        onCreated(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void showNoNetworkView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showNoNetworkView();
        });
    }

    public void showTimeoutView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showTimeoutView();
        });
    }

    public void showEmptyView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showEmptyView();
        });
    }

    public void showErrorView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showErrorView();
        });
    }

    public void showLoginView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showLoginView();
        });
    }

    public void showContentView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showContentView();
        });
    }

    public void toast(String msg) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        });
    }

    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        startActivityForResult(clazz, null, requestCode);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }
}
