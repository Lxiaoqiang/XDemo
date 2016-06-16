package xdemo.xq.com.xdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import xdemo.xq.com.xdemo.R;

public class ImagePhotoActivity extends Activity {
    ImageView iv;
    int x;
    int y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_photo);
        iv = (ImageView) findViewById(R.id.iv_photo);
        Intent intent = getIntent();
        x = intent.getIntExtra("x",0);
        y = intent.getIntExtra("y",0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
    }
}
