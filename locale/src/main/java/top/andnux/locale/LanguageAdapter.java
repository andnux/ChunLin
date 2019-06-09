package top.andnux.locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {

    private Context mContext;
    private List<LocaleBean> mList;

    public LanguageAdapter(Context context, List<LocaleBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_language, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = convertView.findViewById(R.id.text);
            viewHolder.mRadioButton = convertView.findViewById(R.id.select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LocaleBean localeBean = mList.get(position);
        viewHolder.mTextView.setText(localeBean.getLocaleModel().getDisplayName());
        viewHolder.mRadioButton.setChecked(localeBean.isSelect());
        return convertView;
    }

    static class ViewHolder {
        TextView mTextView;
        RadioButton mRadioButton;
    }
}
