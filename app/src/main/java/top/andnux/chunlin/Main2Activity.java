package top.andnux.chunlin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.andnux.adapter.CommonAdapter;
import top.andnux.adapter.CommonViewHolder;
import top.andnux.http.HttpManager;
import top.andnux.http.callback.JsonCallback;
import top.andnux.http.core.HttpRequest;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private CommonAdapter<NewBean.DataBean> mAdapter;
    private RecyclerView mRecyclerView;
    private List<NewBean.DataBean> mBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        mAdapter = new CommonAdapter<NewBean.DataBean>(this, mBeans, R.layout.item_main2) {
            @Override
            public void onBind(CommonViewHolder holder, int viewType, int layoutPosition, NewBean.DataBean item) {
                holder.setText(R.id.textView, item.getTitle());
                holder.setImageUrl(R.id.imageView, item.getThumbnail_pic_s());
            }
        };
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(it -> {
            HttpManager instance = HttpManager.getInstance();
            HttpRequest httpRequest = instance.newHttpRequest();
            httpRequest.setUrl("http://v.juhe.cn/toutiao/index");
            Map<String, Object> parameter = httpRequest.getParameter();
            parameter.put("type", "top");
            parameter.put("key", "fd583f1c64d9e2d03699629c4c4e8639");
            httpRequest.setHttpCallback(new JsonCallback<NewBean>() {
                @Override
                public void onFail(Exception e) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onSuccess(NewBean data) {

                }
            });
            instance.sendRequest(httpRequest);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
    }
}
