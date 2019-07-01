package top.andnux.chunlin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import top.andnux.ui.snackbar.EasySnackBar;
import top.andnux.utils.common.ToastUtil;
import top.andnux.utils.netstate.NetState;
import top.andnux.utils.netstate.NetStateManager;
import top.andnux.utils.netstate.annotation.NetSupport;


public class Main3Activity extends AppCompatActivity {

    private EasySnackBar snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        NetStateManager.getInstance().registerObserver(this);
    }

    @NetSupport(NetState.AUTO)
    public void checkNet(NetState type) {
        switch (type) {
            case NONE: {
                ToastUtil.normal("没有网络连接");
            }
            break;
            case WIFI:
            case MOBILE:
            case AUTO: {
                ToastUtil.normal("开始下载");
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        NetStateManager.getInstance().unRegisterObserver(this);
        super.onDestroy();
    }
}
