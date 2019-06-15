package top.andnux.web;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

import top.andnux.compat.UriCompat;
import top.andnux.ui.dialog.AlertDialog;
import top.andnux.ui.dialog.InputDialog;
import top.andnux.utils.common.FileUtil;

/**
 * Created by LinkToken on 2018/4/11.
 */

public class BaseWebChromeClient extends WebChromeClient implements WrapListener {

    private static final String TAG = "BaseWebChromeClient";
    private WarpFragment fragment;
    private ValueCallback<Uri[]> mUploadMessageAboveL;
    private Activity mContext;
    private ProgressBar mProgressBar;
    private String compressPath;
    private static final int REQ_CHOOSE = 0x88;

    public BaseWebChromeClient(Activity context, ProgressBar progress) {
        mContext = context;
        mProgressBar = progress;
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            fragment = WarpFragment.getInstance(this);
            transaction.add(android.R.id.content, fragment);
            transaction.commit();
        }
    }

    // Android 5.0以上
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        mUploadMessageAboveL = filePathCallback;
        selectImage();
        return true;
    }

    private void selectImage() {
        FileUtil.deleteFile(compressPath);
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        //REQ_CHOOSE是定义的一个常量
        Intent wrapperIntent = Intent.createChooser(intent, null);
        fragment.startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    /**
     * 当前 WebView 加载网页进度
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (mProgressBar == null) {
            return;
        }
        if (newProgress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
            mProgressBar.setProgress(newProgress);//设置进度值
        }
    }

    /**
     * 接收web页面的 Title
     *
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    /**
     * 接收web页面的icon
     *
     * @param view
     * @param icon
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    /**
     * Js 中调用 alert() 函数，产生的对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        new AlertDialog(mContext)
                .setTitle(url)
                .setMsg(message)
                .setPositiveButton("确定", v -> {
                    result.confirm();
                }).setNegativeButton("取消", v -> {
            result.cancel();
        }).show();
        return true;
    }

    /**
     * 处理 Js 中的 Confirm 对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        new AlertDialog(mContext)
                .setTitle(url)
                .setMsg(message)
                .setPositiveButton("确定", v -> {
                    result.confirm();
                }).setNegativeButton("取消", v -> {
            result.cancel();
        }).show();
        return true;
    }

    /**
     * 处理 JS 中的 Prompt对话框
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String
            defaultValue, JsPromptResult result) {
        new InputDialog(mContext)
                .setTitle(url)
                .setHint(message)
                .setText(defaultValue)
                .setNegativeButton("取消", v -> {
                    result.cancel();
                })
                .setPositiveButton("确定", (dialog, text) -> {
                    result.confirm(text);
                    dialog.dismiss();
                })
                .show();
        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == data) {
            mUploadMessageAboveL.onReceiveValue(null);
            return;
        }
        Uri uri = null;
        if (requestCode == REQ_CHOOSE) {
            uri = afterChosePic(data);
        }
        if (mUploadMessageAboveL != null) {
            mUploadMessageAboveL.onReceiveValue(new Uri[]{uri});
        }
        mUploadMessageAboveL = null;
    }

    private Uri afterChosePic(@NonNull Intent data) {
        // 获取图片的路径：
        String[] proj = {MediaStore.Images.Media.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = mContext.getContentResolver().query(data.getData(), proj, null, null, null);
        if (cursor == null) {
            Toast.makeText(mContext, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") ||
                path.endsWith(".jpg") || path.endsWith(".JPG"))) {
            return UriCompat.fromFile(mContext, new File(path));
        } else {
            Toast.makeText(mContext, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return null;
    }

    private File compressFile(String path, String compressPath) {
        return null;
    }
}