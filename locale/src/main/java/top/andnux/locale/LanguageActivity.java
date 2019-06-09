package top.andnux.locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageActivity extends LocaleActivity implements AdapterView.OnItemClickListener {

    public static final String BACKGROUNDCOLOR = "backgroundColor";
    public static final String TITLE = "title";
    public static final String BACKRES = "backRes";
    public static final String FOREGROUNDCOLOR = "foregroundColor";

    private List<LocaleBean> mList;
    private int mIndex = -1;
    private LanguageAdapter adapter;


    public static void newInstance(Context context,
                                   @StringRes int title,
                                   @DrawableRes int backRes,
                                   @DrawableRes int backgroundColor,
                                   @DrawableRes int foregroundColor) {
        Intent intent = new Intent(context, LanguageActivity.class);
        intent.putExtra(BACKGROUNDCOLOR, backgroundColor);
        intent.putExtra(TITLE, title);
        intent.putExtra(BACKRES, backRes);
        intent.putExtra(FOREGROUNDCOLOR, foregroundColor);
        context.startActivity(intent);
    }

    public static void newInstance(Context context,
                                   @StringRes int title,
                                   @DrawableRes int backgroundColor,
                                   @DrawableRes int foreground) {
        newInstance(context, title, 0, backgroundColor, foreground);
    }

    public static void newInstance(Context context,
                                   @DrawableRes int backgroundColor,
                                   @DrawableRes int foreground) {
        newInstance(context, R.string.language_setting, 0, backgroundColor, foreground);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_language);
        ListView listView = findViewById(R.id.listView);
        RelativeLayout titleLayout = findViewById(R.id.titleLayout);
        AppCompatImageView backView = findViewById(R.id.backView);
        AppCompatTextView titleView = findViewById(R.id.titleView);
        AppCompatTextView doneView = findViewById(R.id.doneView);
        backView.setOnClickListener(v -> finish());
        doneView.setOnClickListener(v -> {
            LocaleBean localeBean = mList.get(mIndex);
            if (localeBean != null) {
                LocaleManager instance = LocaleManager.getInstance();
                instance.switchLocale(localeBean.getLocaleModel().getLocale());
            }
        });
        Intent intent = getIntent();
        int titleRes = intent.getIntExtra(TITLE, 0);
        if (titleRes != 0) {
            titleView.setText(titleRes);
        }
        int backRes = intent.getIntExtra(BACKRES, 0);
        if (backRes != 0) {
            backView.setImageResource(backRes);
        }
        int backgroundColor = intent.getIntExtra(BACKGROUNDCOLOR, 0);
        if (backgroundColor != 0) {
            titleLayout.setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        }
        int foregroundColor = intent.getIntExtra(FOREGROUNDCOLOR, 0);
        if (foregroundColor != 0) {
            backView.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, foregroundColor)));
            titleView.setTextColor(ContextCompat.getColor(this, foregroundColor));
            doneView.setTextColor(ContextCompat.getColor(this, foregroundColor));
        }
        mList = new ArrayList<>();
        List<LocaleModel> localeList = LocaleManager.getInstance().getLocaleList();
        Locale currentLocale = LocaleManager.getInstance().getSetLanguageLocale();
        for (int i = 0; i < localeList.size(); i++) {
            LocaleModel value = localeList.get(i);
            if (currentLocale == value.getLocale()) {
                LocaleBean e = new LocaleBean(true, value);
                mIndex = i;
                mList.add(e);
            } else {
                mList.add(new LocaleBean(value));
            }
        }
        adapter = new LanguageAdapter(this, mList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mIndex != -1) {
            LocaleBean localeBean = mList.get(mIndex);
            localeBean.setSelect(false);
            mList.set(mIndex, localeBean);
        }
        LocaleBean localeBean = mList.get(position);
        localeBean.setSelect(true);
        mIndex = position;
        mList.set(position, localeBean);
        adapter.notifyDataSetChanged();
    }
}
