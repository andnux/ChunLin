package top.andnux.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class HttpManager {

    private static final String TAG = "HttpManager";
    private static final HttpManager INSTANCE = new HttpManager();
    private Map<Object, List<HttpRequest>> mMap = new WeakHashMap<>();
    private HttpRequest mRequest;
    private Engine mEngine;

    public static HttpManager getInstance() {
        return INSTANCE;
    }

    private HttpManager() {
        mEngine = new HttpEngine();
    }

    public HttpManager with(String url) {
        mRequest = new HttpRequest();
        mRequest.setUrl(url);
        return this;
    }

    public HttpManager tag(Object tag) {
        mRequest.setTag(tag);
        return this;
    }

    public HttpManager setEngine(Engine engine) {
        mEngine = engine;
        return this;
    }

    public HttpManager cancel(Object tag) {
        List<HttpRequest> engines = mMap.get(tag);
        if (engines != null && engines.size() > 0) {
            for (HttpRequest request : engines) {
                mEngine.cancel(request);
            }
        }
        mMap.remove(tag);
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
        Object tag = mRequest.getTag();
        if (tag != null) {
            List<HttpRequest> requests = mMap.get(tag);
            if (requests == null) {
                requests = new ArrayList<>();
            }
            requests.add(mRequest);
            mMap.put(tag, requests);
        }
        mEngine.execute(mRequest);
    }
}
