package top.andnux.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class PreStorageDao<T> extends BaseStorageDao<T> {

    private SharedPreferences mPreferences;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    private ExecutorService mService;

    public PreStorageDao(Class<T> clazz) {
        super(clazz);
        mPreferences = mContext.getSharedPreferences(
                clazz.getCanonicalName(), Context.MODE_PRIVATE);
    }

    @Override
    public void save(T data) throws Exception {
        Field[] fields = data.getClass().getDeclaredFields();
        SharedPreferences.Editor edit = mPreferences.edit();
        for (Field field : fields) {
            field.setAccessible(true);
            saveData(mPreferences, data, edit, field);
        }
        edit.apply();
    }

    protected void saveData(SharedPreferences sp, T data,
                            SharedPreferences.Editor edit,
                            Field field) throws Exception {
        if (field.getType().equals(String.class)) {
            edit.putString(field.getName(), (String) field.get(data));
        } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            edit.putInt(field.getName(), (Integer) field.get(data));
        } else if (field.getType().equals(Long.class)) {
            edit.putLong(field.getName(), (Long) field.get(data));
        } else if (field.getType().equals(Float.class)) {
            edit.putFloat(field.getName(), (Float) field.get(data));
        } else if (field.getType().equals(boolean.class) ||
                field.getType().equals(Boolean.class)) {
            edit.putBoolean(field.getName(), (Boolean) field.get(data));
        } else if (field.getType().equals(Date.class)) {
            edit.putString(field.getName(), sdf.format(field.get(data)));
        }
    }

    @Override
    public T load() {
        T t = null;
        try {
            t = mClass.newInstance();
            Map<String, ?> all = mPreferences.getAll();
            Set<String> strings = all.keySet();
            Field[] fields = mClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                for (String string : strings) {
                    if (field.getName().equals(string)) {
                        loadData(mPreferences, t, field);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void clear() {
        mPreferences.edit().clear().apply();
    }

    protected void loadData(SharedPreferences sp, T t, Field field)
            throws Exception {
        if (field.getType().equals(String.class)) {
            String value = sp.getString(field.getName(), "");
            field.set(t, value);
        } else if (field.getType().equals(Integer.class)
                || field.getType().equals(int.class)) {
            Integer value = sp.getInt(field.getName(), 0);
            field.set(t, value);
        } else if (field.getType().equals(Float.class)) {
            Float value = sp.getFloat(field.getName(), 0);
            field.set(t, value);
        } else if (field.getType().equals(Long.class)) {
            Long value = sp.getLong(field.getName(), 0);
            field.set(t, value);
        } else if (field.getType().equals(Boolean.class) ||
                field.getType().equals(boolean.class)) {
            Boolean value = mPreferences.getBoolean(field.getName(), false);
            field.set(t, value);
        } else if (field.getType().equals(Date.class)) {
            String value = mPreferences.getString(field.getName(), "");
            try {
                if (!TextUtils.isEmpty(value)) {
                    Date parse = sdf.parse(value);
                    field.set(t, parse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
