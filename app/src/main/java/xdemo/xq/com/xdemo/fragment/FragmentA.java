package xdemo.xq.com.xdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.activity.ChioceActivity;
import xdemo.xq.com.xdemo.activity.DeleteViewActivity;
import xdemo.xq.com.xdemo.adapter.StringSingleAdapter;
import xdemo.xq.com.xdemo.view.CircleImageView;

/**
 * Created by lhq on 2016/3/22.
 */
public class FragmentA extends LazyFragment implements AdapterView.OnItemClickListener{
    /**
     * 界面是否初始化完成
     */
    private boolean isPrepared = false;
    /**
     * 是否首次显示界面
     */
    private boolean isFirstShow = true;

    private ListView lv;
    String[] strings = new String[2];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a_layout, null);
        initView(v);
        initEvent();
        return v;
    }

    private void initValues() {
        strings[0] = "1.滑动删除";
        strings[1] = "2.单选多选";
        lv.setAdapter(new StringSingleAdapter(strings, getActivity()));
    }

    private void initView(View v) {
        lv = (ListView) v.findViewById(R.id.lv_navigation);
        isPrepared = true;
        lazyLoad();
    }
    private void initEvent(){
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstShow)
            return;
        isFirstShow = false;
        initValues();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                intent2Activity(DeleteViewActivity.class);
                break;
            case 1:
                intent2Activity(ChioceActivity.class);
                break;
        }
    }
    private void intent2Activity(Class clazz){
        Intent intent = new Intent(getActivity(),clazz);
        startActivity(intent);
    }
    private class ARecyclerViewAdapter extends RecyclerView.Adapter<ARecyclerViewAdapter.ViewHolder> {
        private String[] values;

        public ARecyclerViewAdapter(String[] s) {
            this.values = s;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView imageView;
            TextView contentText;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (CircleImageView) itemView.findViewById(R.id.circle_imageview_item);
                contentText = (TextView) itemView.findViewById(R.id.tv_text_content_item);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.contentText.setText(values[position]);
            Glide.with(holder.imageView.getContext()).load(R.mipmap.ayst_icon).fitCenter().into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return values.length;
        }
    }
}
