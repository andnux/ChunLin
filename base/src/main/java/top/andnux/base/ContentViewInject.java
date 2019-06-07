package top.andnux.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import top.andnux.base.annotation.ContentView;

public class ContentViewInject {

    public static void inject(Activity activity) {
        ContentView injection = activity.getClass().getAnnotation(ContentView.class);
        if (injection != null) {
            int value = injection.value();
            activity.setContentView(value);
        }
    }

    public static View inject(Fragment fragment, LayoutInflater inflater, ViewGroup container) {
        ContentView injection = fragment.getClass().getAnnotation(ContentView.class);
        if (injection != null) {
            int value = injection.value();
            return LayoutInflater.from(fragment.getContext()).inflate(value, container, false);
        }
        return null;
    }
}
