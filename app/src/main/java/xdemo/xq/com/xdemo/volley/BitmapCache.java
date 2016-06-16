package xdemo.xq.com.xdemo.volley;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by lhq on 2016/2/3.
 * 此类仅缓存
 */
public class BitmapCache implements ImageLoader.ImageCache{
    public LruCache<String ,Bitmap> cache;
    public int max = 10*1024*1024;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public BitmapCache(){
        cache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String s) {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        cache.put(s,bitmap);
    }
}
