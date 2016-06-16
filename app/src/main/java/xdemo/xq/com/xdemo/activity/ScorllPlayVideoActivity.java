package xdemo.xq.com.xdemo.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;


import xdemo.xq.com.xdemo.R;

public class ScorllPlayVideoActivity extends BaseActivity {

    ListView lv;
    int width ;
    int height;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scorll_play_video;
    }

    @Override
    protected void findViews() {
        lv = (ListView) findViewById(R.id.lv_video);
    }

    @Override
    protected void init() {
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        lv.setAdapter(new demoAdapter());
    }

    @Override
    protected void widgetListener() {

    }

    class demoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
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
            convertView = LayoutInflater.from(ScorllPlayVideoActivity.this).inflate(R.layout.list_item,parent,false);
            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.width = getResources().getDisplayMetrics().widthPixels;
            layoutParams.height = getResources().getDisplayMetrics().heightPixels;
            return convertView;
        }
    }
}
