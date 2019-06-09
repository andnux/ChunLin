package top.andnux.locale;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class LocaleActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        LocaleManager instance = LocaleManager.getInstance();
        Context context = instance.attachBaseContext(newBase);
        super.attachBaseContext(context);
    }
}
