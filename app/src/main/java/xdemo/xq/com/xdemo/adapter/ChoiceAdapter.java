package xdemo.xq.com.xdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.entity.User;

/**
 * Created by lhq on 2016/3/31.
 */
public class ChoiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> list;

    public ChoiceAdapter(List<User> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回true listview.getCheckedItemIds()才能拿到值
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choice_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User u = list.get(position);
        holder.tv.setText(u.getName());

        return convertView;
    }

    class ViewHolder {
        TextView tv;
        CheckBox cb;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv_choice);
            cb = (CheckBox) view.findViewById(R.id.cb_choice);
        }
    }
}
