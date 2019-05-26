package top.andnux.http.cache;

public class HttpCache {

    private String url;
    private String data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpCache{" +
                "url='" + url + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
