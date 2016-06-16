package xdemo.xq.com.xdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import xdemo.xq.com.xdemo.R;

/**
 * Created by lhq on 2016/3/31.
 */
public class StringSingleAdapter extends BaseAdapter{
    private String[] strings;
    private Context mContext;
    public StringSingleAdapter(String[] strings,Context context){
        this.strings = strings;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(14);
        textView.setMinHeight(50);
        textView.setPadding(11,11,11,11);
        textView.setText(strings[position]);
        return textView;
    }
}
