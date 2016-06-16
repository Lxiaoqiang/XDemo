package xdemo.xq.com.xdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.contant.contant;
import xdemo.xq.com.xdemo.entity.Whether;

/**
 * Created by lhq on 2016/4/5.
 */
public class WhetherAdapter extends RecyclerView.Adapter<WhetherAdapter.ViewHolder> {
    List<Whether.Future> list;
    public WhetherAdapter(List<Whether.Future> list) {
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView fix;
        TextView temperature;
        TextView whether;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tv_whether_item_date);
//            fix = (TextView) view.findViewById(R.id.tv_whether_item_fix);
            temperature = (TextView) view.findViewById(R.id.tv_whether_item_temperature);
            whether = (TextView) view.findViewById(R.id.tv_whether_item_whether);
            icon = (ImageView) view.findViewById(R.id.iv_whether_item_icon);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_whether_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Whether.Future future = list.get(position);
//        holder.fix.setText(contant.getWhetherById(future.getWeather_id().getFa()));
        holder.temperature.setText(future.getTemperature());
        holder.whether.setText(future.getWeather());
        holder.date.setText(future.getWeek());
        holder.icon.setImageResource(contant.getWhetherIcon(future.getWeather_id().getFa()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.itemClickListener(future);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListener(OnWhetherItemClickListener listener) {
        this.listener = listener;
    }

    private OnWhetherItemClickListener listener;

    public interface OnWhetherItemClickListener{
        void itemClickListener(Whether.Future future);
    }
}
