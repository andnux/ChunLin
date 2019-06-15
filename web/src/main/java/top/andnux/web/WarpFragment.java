package top.andnux.web;

import android.content.Intent;

import androidx.fragment.app.Fragment;

public class WarpFragment extends Fragment {

    private WrapListener mListener;

    public WarpFragment(WrapListener listener) {
        mListener = listener;
    }

    public static WarpFragment getInstance(WrapListener listener){
        return new WarpFragment(listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mListener != null){
            mListener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
