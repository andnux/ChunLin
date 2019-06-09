package top.andnux.locale;

class LocaleBean {

    private boolean mSelect;
    private LocaleModel mLocaleModel;

    LocaleBean(LocaleModel locale) {
        mSelect = false;
        mLocaleModel = locale;
    }

    LocaleBean(boolean select, LocaleModel locale) {
        mSelect = select;
        mLocaleModel = locale;
    }

    public boolean isSelect() {
        return mSelect;
    }

    public void setSelect(boolean select) {
        mSelect = select;
    }

    public LocaleModel getLocaleModel() {
        return mLocaleModel;
    }

    public void setLocaleModel(LocaleModel localeModel) {
        mLocaleModel = localeModel;
    }
}
