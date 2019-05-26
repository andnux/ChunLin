package top.andnux.http;

import java.util.Map;
import java.util.WeakHashMap;

public class HttpRequest {

    private String mUrl;
    private boolean mLoading;
    private Map<String, String> mHeaders;
    private Map<String, Object> mParams;
    private RequestMethod mRequestMethod;
    private BodyType mBodyType;
    private HttpCallback mCallback;
    private Object mTag;

    public HttpRequest() {
        mHeaders = new WeakHashMap<>();
        mParams = new WeakHashMap<>();
        mLoading = true;
        mRequestMethod = RequestMethod.GET;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, Object> getParams() {
        return mParams;
    }

    public void setParams(Map<String, Object> params) {
        mParams = params;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public HttpCallback getCallback() {
        return mCallback;
    }

    public void setCallback(HttpCallback callback) {
        mCallback = callback;
    }

    public String getHeader(String key) {
        return mHeaders.get(key);
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        mHeaders.putAll(headers);
    }

    public Object getParam(String key) {
        return mParams.get(key);
    }

    public void addParam(String key, Object value) {
        mParams.put(key, value);
    }

    public void addParams(Map<String, Object> params) {
        mParams.putAll(params);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    public RequestMethod getRequestMethod() {
        return mRequestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        mRequestMethod = requestMethod;
    }

    public BodyType getBodyType() {
        return mBodyType;
    }

    public void setBodyType(BodyType bodyType) {
        mBodyType = bodyType;
    }
}
