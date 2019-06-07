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

import top.andnux.mvp.annotation.ContentView;
import top.andnux.ui.statelayout.StateLayout;

public abstract class BaseFragment extends Fragment implements BaseView, StateLayout.OnViewRefreshListener {

    private StateLayout mStateLayout;

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
            ContentView injection = this.getClass().getAnnotation(ContentView.class);
            if (injection != null) {
                int value = injection.value();
                view = inflater.inflate(value, container, false);
                return view;
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStateLayout = view.findViewById(R.id.stateLayout);
        if (mStateLayout != null) {
            mStateLayout.setRefreshListener(this);
        }
        onCreated(savedInstanceState);
    }

    protected abstract void onCreated(@Nullable Bundle savedInstanceState);


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
        Toast toast = Toast.makeText(getContext(), null, Toast.LENGTH_SHORT);
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
