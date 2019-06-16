package top.andnux.chunlin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import top.andnux.ui.dialog.AlertDialog;
import top.andnux.web.BaseWebChromeClient;
import top.andnux.web.BaseWebSetting;
import top.andnux.web.BaseWebView;
import top.andnux.web.BaseWebViewClient;

public class WebActivity extends AppCompatActivity {

    private BaseWebView mWebView;
    private SwipeRefreshLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_web);
        mContainer = findViewById(R.id.container);
        mWebView = new BaseWebView(this);
        mWebView.setWebViewClient(new BaseWebViewClient(this) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mContainer.setRefreshing(false);
            }
        });
        mWebView.setWebChromeClient(new BaseWebChromeClient(this, null));
        new BaseWebSetting(mWebView, this, true);
        mContainer.addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
//        mWebView.loadUrl("http://192.168.31.11:8020/ROOT/upload.html");
        mWebView.loadUrl("https://www.baidu.com");
        mContainer.setOnRefreshListener(() -> {
            mWebView.reload();
        });
        new AlertDialog(this)
                .setCenterButton("取消", v -> {

                })
                .setNegativeButton("hhh", v -> {

                })
                .setPositiveButton("gggg", v -> {

                })
                .setMessage("消息")
                .setTitle("标题")
                .show();
    }
}
