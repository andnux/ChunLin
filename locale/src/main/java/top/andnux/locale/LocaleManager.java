package top.andnux.locale;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleManager {

    private static final LocaleManager ourInstance = new LocaleManager();
    private static final String FILE_NAME = "locale";
    private static final String SP_KEY = "current";
    private List<LocaleModel> mList = new ArrayList<>();

    private SharedPreferences mPreferences;

    public static LocaleManager getInstance() {
        return ourInstance;
    }

    private Context mContext;
    private LocaleListener mLocaleListener;

    private LocaleManager() {
        mList.add(new LocaleModel("跟随系统", getSystemLocale()));
        mList.add(new LocaleModel("中文(简体)", Locale.SIMPLIFIED_CHINESE));
        mList.add(new LocaleModel("中文(繁體)", Locale.TRADITIONAL_CHINESE));
        mList.add(new LocaleModel("english", Locale.ENGLISH));
    }

    public void addLocale(LocaleModel model) {
        mList.add(model);
    }

    public void addLocale(int index, LocaleModel model) {
        mList.add(index, model);
    }

    public void removeLocale(LocaleModel model) {
        mList.remove(model);
    }

    public void removeLocale(int index) {
        mList.remove(index);
    }


    public List<LocaleModel> getLocaleList() {
        return mList;
    }

    public void setLocaleList(List<LocaleModel> list) {
        mList = list;
    }

    public interface LocaleListener {
        void onChange(Context context);
    }

    public LocaleListener getLocaleListener() {
        return mLocaleListener;
    }

    public void setLocaleListener(LocaleListener localeListener) {
        mLocaleListener = localeListener;
    }

    public void init(Context context) {
        mContext = context;
        if (mContext == null) {
            mContext = getApplicationInner();
        }
        if (mContext != null) {
            mPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            int current = mPreferences.getInt(SP_KEY, -1);
            if (current == -1) {
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putInt(SP_KEY, 0);
                edit.apply();
            }
        }

    }

    private Application getApplicationInner() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method currentApplication = activityThread.getDeclaredMethod("currentApplication");
            Method currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            Object current = currentActivityThread.invoke((Object) null);
            Object app = currentApplication.invoke(current);
            return (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Locale getSystemLocale() {
        Locale locale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = LocaleList.getDefault();
            if (localeList.size() > 0) {
                locale = localeList.get(0);
            }
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public void switchLocale(Locale locale) {
        for (int i = 0; i < mList.size(); i++) {
            LocaleModel value = mList.get(i);
            if (value.getLocale() == locale) {
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putInt(SP_KEY, i);
                edit.apply();
                if (mLocaleListener != null) {
                    mLocaleListener.onChange(mContext);
                }
            }
        }
    }

    public Context attachBaseContext(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context = context.createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
        return context;
    }

    public Locale getSetLanguageLocale() {
        int current = mPreferences.getInt(SP_KEY, 0);
        for (int i = 0; i < mList.size(); i++) {
            LocaleModel model = mList.get(i);
            if (i == current) {
                return model.getLocale();
            }
        }
        return null;
    }
}
