package top.andnux.http.netstate;

public interface NetStateListener {

    void onConnect(NetType state);

    void onDisConnect();
}
