package top.andnux.http;

public interface Engine {

    void execute(HttpRequest request);

    void cancel(HttpRequest request);
}
