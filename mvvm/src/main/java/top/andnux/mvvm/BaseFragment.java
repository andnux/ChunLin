package top.andnux.mvvm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import top.andnux.base.ContentViewInject;
import top.andnux.ui.statelayout.StateLayout;

public abstract class BaseFragment extends Fragment implements StateLayout.OnViewRefreshListener {

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
            ContentViewInject.inject(this, inflater, container);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract void onCreated(@Nullable Bundle savedInstanceState);


    public void showEmptyView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showEmptyView();
            });
        }
    }

    public void showNoNetworkView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showNoNetworkView();
            });
        }
    }

    public void showTimeoutView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showTimeoutView();
            });
        }
    }


    public void showErrorView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showErrorView();
            });
        }
    }

    public void showLoginView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showLoginView();
            });
        }
    }

    public void showContentView() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (mStateLayout == null) return;
                mStateLayout.showContentView();
            });
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        startActivityForResult(clazz, null, requestCode);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }
}
