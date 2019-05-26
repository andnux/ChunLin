package top.andnux.http;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class HttpManager {

    private static final String TAG = "HttpManager";
    private static final HttpManager INSTANCE = new HttpManager();
    private Map<String, List<HttpRequest>> mMap = new WeakHashMap<>();
    private HttpRequest mRequest;
    private Engine mEngine;

    public static HttpManager getInstance() {
        return INSTANCE;
    }

    private HttpManager() {
        mEngine = new HttpEngine();
    }

    public HttpManager with(Activity activity) {
        mRequest = new HttpRequest();
        mRequest.setTag(activity.getClass().getCanonicalName());
        return this;
    }

    public HttpManager with(Fragment fragment) {
        mRequest = new HttpRequest();
        mRequest.setTag(fragment.getClass().getCanonicalName());
        return this;
    }

    public HttpManager setEngine(Engine engine) {
        mEngine = engine;
        return this;
    }

    public HttpManager cancel(Activity activity) {
        List<HttpRequest> engines = mMap.get(activity.getClass().getCanonicalName());
        if (engines != null && engines.size() > 0) {
            for (HttpRequest request : engines) {
                mEngine.cancel(request);
            }
        }
        mMap.remove(activity.toString());
        return this;
    }

    public HttpManager cancel(Fragment fragment) {
        List<HttpRequest> engines = mMap.get(fragment.getClass().getCanonicalName());
        if (engines != null && engines.size() > 0) {
            for (HttpRequest request : engines) {
                mEngine.cancel(request);
            }
        }
        mMap.remove(fragment.toString());
        return this;
    }

    public HttpManager cancelAll() {
        Collection<List<HttpRequest>> engines = mMap.values();
        for (List<HttpRequest> engine : engines) {
            for (HttpRequest engine1 : engine) {
                mEngine.cancel(engine1);
            }
        }
        mMap.clear();
        return this;
    }

    public HttpManager bodyType(BodyType type) {
        mRequest.setBodyType(type);
        ;
        return this;
    }

    public HttpManager url(String url) {
        mRequest.setUrl(url);
        return this;
    }

    public HttpManager loading(boolean loading) {
        mRequest.setLoading(loading);
        return this;
    }

    public HttpManager addHeader(String key, String value) {
        mRequest.addHeader(key, value);
        return this;
    }

    public HttpManager addHeaders(Map<String, String> headers) {
        mRequest.addHeaders(headers);
        return this;
    }

    public HttpManager addParam(String key, String value) {
        mRequest.addParam(key, value);
        return this;
    }

    public HttpManager addParams(Map<String, Object> params) {
        mRequest.addParams(params);
        return this;
    }

    public HttpManager setRequestMethod(RequestMethod requestMethod) {
        mRequest.setRequestMethod(requestMethod);
        return this;
    }

    public HttpManager setBodyType(BodyType bodyType) {
        mRequest.setBodyType(bodyType);
        return this;
    }


    public <T> HttpManager callback(HttpCallback callback) {
        mRequest.setCallback(callback);
        return this;
    }

    public void execute() {
        if (mEngine == null) {
            mEngine = new HttpEngine();
        }
        List<HttpRequest> requests = mMap.get(mRequest.getTag().toString());
        if (requests == null) {
            requests = new ArrayList<>();
        }
        requests.add(mRequest);
        mMap.put(mRequest.getTag().toString(), requests);
        mEngine.execute(mRequest);
    }
}
