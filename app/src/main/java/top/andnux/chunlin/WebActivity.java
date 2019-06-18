package top.andnux.chunlin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import top.andnux.ui.dialog.AlertDialog;
import top.andnux.ui.image.photoview.PhotoActivity;
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
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560661550590&di=53ec964af429493732fc7a78e982a10f&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F67%2F01%2F13558PIC4R7_1024.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560661550589&di=63b8b50fd383d8bce87e093b1554edca&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160907%2F963561b9374b48c094a420e4e8218118_th.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560661550589&di=a50a1097f5d0824dee9afb6ce3c98016&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160924%2Fa1c0b27f9fcf4985b5f6e8eda154cbc3_th.jpeg");
        PhotoActivity.newInstance(this, urls);
    }
}
