package xdemo.xq.com.xdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import xdemo.xq.com.xdemo.R;

public class ImageActivity extends Activity {
    private GridView gridView;
    private int[] data = new int[]{R.mipmap.mv1,R.mipmap.mvv2,R.mipmap.mv3,R.mipmap.mv4,R.mipmap.mv5,R.mipmap.mv6,R.mipmap.mv7,R.mipmap.mv1,};
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();

    }


    private void initView(){
        gridView = (GridView) findViewById(R.id.gv_image_act);
        gridView.setAdapter(new Adapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.getX();
//                view.getY();
//                view.getWidth();
//                view.getHeight();
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                intent =  new Intent(ImageActivity.this,ImagePhotoActivity.class);
                intent.putExtra("x",rect.centerX());
                intent.putExtra("y",rect.centerY());
                startActivity(intent);
//                overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
            }
        });
    }

    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.length;
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
//            convertView = LayoutInflater.from(ImageActivity.this).inflate(R.layout.image_item_layout,null);
//            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image_item);

            ImageView iv = new ImageView(ImageActivity.this);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,200 );
            iv.setLayoutParams(layoutParams);
            iv.setImageResource(data[position]);
//            imageView.setImageResource(data[position]);
            return iv;
        }
    }
}
