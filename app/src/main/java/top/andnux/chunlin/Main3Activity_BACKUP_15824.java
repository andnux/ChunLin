package top.andnux.chunlin;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Main3Activity extends AppCompatActivity {

=======
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import top.andnux.http.netstate.NetStateManager;
import top.andnux.http.netstate.NetType;
import top.andnux.http.netstate.annotation.NetWork;
import top.andnux.ui.snackbar.EasySnackBar;
import top.andnux.utils.common.ToastUtil;


public class Main3Activity extends AppCompatActivity {

    private EasySnackBar snackBar;

>>>>>>> 更新网络状态监听
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
<<<<<<< HEAD
=======
        NetStateManager.getInstance().registerObserver(this);
    }

    @NetWork(value = NetType.AUTO, tips = "当前网络不是WifI,请连接WIFI后操作")
    public void checkNet(NetType type) {
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
>>>>>>> 更新网络状态监听
    }
}
