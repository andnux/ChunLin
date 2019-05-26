package top.andnux.http;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import top.andnux.http.cache.Cache;
import top.andnux.http.cache.SQLiteCache;

public class HttpEngine implements Engine {

    private static final String TAG = "HttpEngine";
    private OkHttpClient mClient;
    private Map<String, Call> mCallMap = new WeakHashMap<>();
    private Cache<String, String> mCache = new SQLiteCache();

    public HttpEngine() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .callTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }

    @Override
    public void execute(HttpRequest request) {
        Log.d(TAG, "execute() called with: request = [" + request + "]");
        RequestMethod requestMethod = request.getRequestMethod();
        switch (requestMethod) {
            case GET: {
                Request.Builder builder = new Request.Builder().get();
                builder.tag(request.getTag());
                Set<String> headers = request.getHeaders().keySet();
                for (String header : headers) {
                    builder.addHeader(header, request.getHeader(header));
                }
                Set<String> params = request.getParams().keySet();
                Uri.Builder upon = Uri.parse(request.getUrl()).buildUpon();
                for (String param : params) {
                    upon.appendQueryParameter(param, request.getParam(param).toString());
                }
                String url = upon.build().toString();
                request.setUrl(url);
                builder.url(url);
                enqueue(request, builder);
            }
            break;
            case POST: {
                switch (request.getBodyType()) {
                    case BODY: {

                    }
                    break;
                    case FORM: {

                    }
                    break;
                    case MULTIPART: {

                    }
                    break;
                }
            }
            break;
        }

    }

    private void enqueue(HttpRequest request, Request.Builder builder) {
        if (!Utils.isNetworkConnected()) {
            String data = mCache.get(Utils.md5(request.getUrl()));
            HttpCallback callback = request.getCallback();
            if (!TextUtils.isEmpty(data)) {
                if (callback != null) {
                    callback.onSuccess(data);
                }
                return;
            }
        }
        Call mCall = mClient.newCall(builder.build());
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String data = mCache.get(Utils.md5(request.getUrl()));
                HttpCallback callback = request.getCallback();
                if (callback != null) {
                    if (TextUtils.isEmpty(data)) {
                        callback.onFail(e);
                    } else {
                        callback.onSuccess(data);
                    }
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                String cache = mCache.get(Utils.md5(request.getUrl()));
                mCache.put(Utils.md5(request.getUrl()), data);
                HttpCallback callback = request.getCallback();
                if (callback != null) {
                    callback.onSuccess(data);
                }
            }
        });
        mCallMap.put(request.getTag().toString(), mCall);
    }

    @Override
    public void cancel(HttpRequest request) {
        Call call = mCallMap.get(request.getTag().toString());
        if (call != null) {
            call.cancel();
        }
    }
}
