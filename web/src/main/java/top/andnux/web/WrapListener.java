package top.andnux.web;

import android.content.Intent;

public interface WrapListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
