package top.andnux.locale;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.List;

public class LocaleApplication extends Application {

    private LocaleManager instance = LocaleManager.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        instance.attachBaseContext(this);
    }

    public void setLocaleListener(LocaleManager.LocaleListener listener) {
        instance.setLocaleListener(listener);
    }

    public void addLocale(List<LocaleModel> models) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        instance.init(base);
        addLocale(instance.getLocaleList());
        Context context = instance.attachBaseContext(base);
        super.attachBaseContext(context);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        instance.attachBaseContext(this);
    }
}
