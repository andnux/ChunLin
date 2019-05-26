package top.andnux.http;

public interface HttpCallback {

    void onSuccess(String data);

    void onFail(Exception e);
}
