package top.andnux.locale;

import java.util.Locale;

public class LocaleModel {

    private String mDisplayName;
    private Locale mLocale;

    public LocaleModel(String displayName, Locale locale) {
        mDisplayName = displayName;
        mLocale = locale;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public Locale getLocale() {
        return mLocale;
    }

    public void setLocale(Locale locale) {
        mLocale = locale;
    }
}
