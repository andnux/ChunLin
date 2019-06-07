package top.andnux.chunlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import top.andnux.adapter.CommonAdapter;
import top.andnux.adapter.CommonViewHolder;
import top.andnux.http.HttpManager;

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
        refreshLayout.setOnRefreshListener(it ->
                HttpManager.getInstance().with(Main2Activity.this)
                        .url("http://v.juhe.cn/toutiao/index")
                        .callback(new JsonCallback2<NewBean>() {
                            @Override
                            public void onSuccess(Result<NewBean> data) {
                                it.finishRefresh();
                                Log.e(TAG, "onSuccess() called with: data = [" + data + "]");
                                mBeans.clear();
                                mBeans.addAll(data.getResult().getData());
                                mAdapter.notifyDataSetHasChanged();
                            }

                            @Override
                            public void onFail(Exception e) {
                                super.onFail(e);
                                it.finishRefresh(false);
                            }
                        })
                        .addParam("type", "top")
                        .addParam("key", "fd583f1c64d9e2d03699629c4c4e8639")
                        .execute());
        refreshLayout.setOnLoadMoreListener(it -> {
            it.finishLoadMore(/*,false*/);//传入false表示加载失败
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
        mAdapter = null;
        HttpManager.getInstance().cancel(this);
    }
}
