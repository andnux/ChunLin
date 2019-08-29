package top.andnux.chunlin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import top.andnux.ui.dialog.BottomSheetDialog;
import top.andnux.ui.pagergridview.GridDataBean;
import top.andnux.ui.pagergridview.ViewPagerGridView;
import top.andnux.ui.snackbar.EasySnackBar;
import top.andnux.utils.common.ToastUtil;
import top.andnux.utils.netstate.NetState;
import top.andnux.utils.netstate.NetStateManager;
import top.andnux.utils.netstate.annotation.NetSupport;
import top.andnux.utils.storage.StorageDao;
import top.andnux.utils.storage.StorageFactory;


public class Main3Activity extends AppCompatActivity {

    private EasySnackBar snackBar;
    private ViewPagerGridView mPagerGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mPagerGridView = findViewById(R.id.gridView);
        NetStateManager.getInstance().registerObserver(this);
        StorageDao<Config> preDao = StorageFactory.getPreDao(Config.class);
        Config data = new Config();
        data.setName("张春林");
        try {
            preDao.save(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<GridDataBean> beans = new ArrayList<>();
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        beans.add(new GridDataBean());
        mPagerGridView.setBeans(beans);
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

    public void onClick(View view) {
        new BottomSheetDialog(this)
                .addSheetItem("相机", BottomSheetDialog.SheetItemColor.Blue, which -> {

                })
                .addSheetItem("相册", BottomSheetDialog.SheetItemColor.Blue, which -> {

                })
                .show();

    }
}
