package xdemo.xq.com.xdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import xdemo.xq.com.xdemo.R;

public class QmActivity extends Activity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qm);
        iv = (ImageView) findViewById(R.id.iv_qm);
        iv.setImageResource(R.mipmap.mv1);
        iv.getDrawable();
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap qm = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Bitmap bitmap = drawable.getBitmap();
        Bitmap tem = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(tem);
        canvas.drawBitmap(bitmap,0,0,null);
        Rect rect = new Rect();
        rect.bottom = bitmap.getHeight()-50;
        rect.right = bitmap.getWidth()-50;
        rect.left = rect.right-50;
        rect.top = rect.bottom-50;
        canvas.drawBitmap(qm,null,rect,null);
        iv.setImageBitmap(tem);

    }
}
