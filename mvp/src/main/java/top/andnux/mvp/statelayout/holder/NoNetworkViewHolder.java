package top.andnux.mvp.statelayout.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.andnux.mvp.R;


public class NoNetworkViewHolder extends BaseHolder {

    public ImageView ivImg;

    public NoNetworkViewHolder(View view) {
        tvTip = (TextView) view.findViewById(R.id.tv_message);
        ivImg = (ImageView) view.findViewById(R.id.iv_img);
    }


}
