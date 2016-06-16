package xdemo.xq.com.xdemo.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lhq on 2016/3/29.
 */
public class App extends Application{


    public static App app;
    /**
     * 全局队列
     * @return
     */
    public static RequestQueue getQueues() {
        return queues;
    }

    public static void setQueues(RequestQueue queue) {
        App.queues = queue;
    }

    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        queues = Volley.newRequestQueue(getApplicationContext());
    }
    public static App getApp() {
        return app;
    }
}
