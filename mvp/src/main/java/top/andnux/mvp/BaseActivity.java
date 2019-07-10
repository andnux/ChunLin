package top.andnux.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import top.andnux.base.ContentViewInject;
import top.andnux.base.annotation.ContentView;
import top.andnux.ui.statelayout.StateLayout;

/**
 * 泛型参数结束
 *
 * @param <V> 视图类型
 * @param <P> 控制器类型
 */
public abstract class BaseActivity<V extends BaseView,
        P extends BasePresenter<V>> extends AppCompatActivity
        implements BaseView, StateLayout.OnViewRefreshListener {

    protected P mPresenter;
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
        mPresenter = instancePresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        onCreated(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected P instancePresenter() {
        try {
            ParameterizedType pType = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            if (pType != null) {
                Type[] types = pType.getActualTypeArguments();
                if (types.length == 2) {
                    Class<P> pClass = (Class<P>) types[types.length - 1];
                    return pClass.newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showNoNetworkView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showNoNetworkView();
        });
    }

    @Override
    public void showTimeoutView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showTimeoutView();
        });
    }

    @Override
    public void showEmptyView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showEmptyView();
        });
    }

    @Override
    public void showErrorView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showErrorView();
        });
    }

    @Override
    public void showLoginView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showLoginView();
        });
    }

    @Override
    public void showContentView() {
        runOnUiThread(() -> {
            if (mStateLayout == null) return;
            mStateLayout.showContentView();
        });
    }

    @Override
    public void toast(String msg) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        });
    }

    @Override
    public void toastError(String msg) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        });
    }

    @Override
    public void toastSuccess(String msg) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        });
    }

    @Override
    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    @Override
    public void startActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        startActivityForResult(clazz, null, requestCode);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }
}
