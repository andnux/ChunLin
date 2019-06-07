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

import top.andnux.mvp.annotation.ContentView;
import top.andnux.ui.statelayout.StateLayout;

/**
 * 泛型参数结束
 * @param <T> 数据类型
 * @param <V> 视图类型
 * @param <M> 模型类型
 * @param <P> 控制器类型
 */
public abstract class BaseActivity<T, V extends BaseView, M extends BaseModel<T, V>,
        P extends BasePresenter<T, V, M>> extends AppCompatActivity
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
            ContentView injection = this.getClass().getAnnotation(ContentView.class);
            if (injection != null) {
                int value = injection.value();
                setContentView(value);
            }
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
                if (types.length == 4) {
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
        if (mStateLayout == null) return;
        mStateLayout.showNoNetworkView();
    }

    @Override
    public void showTimeoutView() {
        if (mStateLayout == null) return;
        mStateLayout.showTimeoutView();
    }

    @Override
    public void showEmptyView() {
        if (mStateLayout == null) return;
        mStateLayout.showEmptyView();
    }

    @Override
    public void showErrorView() {
        if (mStateLayout == null) return;
        mStateLayout.showErrorView();
    }

    @Override
    public void showLoginView() {
        if (mStateLayout == null) return;
        mStateLayout.showLoginView();
    }

    @Override
    public void showContentView() {
        if (mStateLayout == null) return;
        mStateLayout.showContentView();
    }

    @Override
    public void toast(String msg) {
        Toast toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
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
