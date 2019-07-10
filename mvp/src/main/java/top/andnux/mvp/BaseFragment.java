package top.andnux.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import top.andnux.base.ContentViewInject;
import top.andnux.ui.statelayout.StateLayout;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>>
        extends Fragment implements BaseView, StateLayout.OnViewRefreshListener {

    private StateLayout mStateLayout;
    protected P mPresenter;

    protected View getLayoutView(LayoutInflater inflater) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = getLayoutView(inflater);
        if (view != null) {
            return view;
        } else {
            ContentViewInject.inject(this, inflater, container);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStateLayout = view.findViewById(R.id.stateLayout);
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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    public abstract void onCreated(@Nullable Bundle savedInstanceState);


    @Override
    public void showEmptyView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showEmptyView();
            });
        }
    }

    @Override
    public void showNoNetworkView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showNoNetworkView();
            });
        }
    }

    @Override
    public void showTimeoutView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showTimeoutView();
            });
        }
    }


    @Override
    public void showErrorView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showErrorView();
            });
        }
    }

    @Override
    public void showLoginView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showLoginView();
            });
        }
    }

    @Override
    public void showContentView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showContentView();
            });
        }
    }

    @Override
    public void toast(String msg) {
        Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    @Override
    public void toastError(String msg) {
        Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    @Override
    public void toastSuccess(String msg) {
        Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    @Override
    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    @Override
    public void startActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(getContext(), clazz);
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
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }
}
