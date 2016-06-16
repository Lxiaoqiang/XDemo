package xdemo.xq.com.xdemo.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import xdemo.xq.com.xdemo.app.App;
import xdemo.xq.com.xdemo.toast.CustomToast;
import xdemo.xq.com.xdemo.util.NetUtil;
import xdemo.xq.com.xdemo.util.PromptManager;

/**
 * Created by lhq on 2016/2/3.
 */
public class VolleyHttp {
    /**
     * get请求
     * @param url
     * @param tag 当前请求的tag，通过tag取消请求
     * @param volleyInterface
     */
    public static void get(String url, String tag, VolleyInterface volleyInterface, Context context){
        if (NetUtil.isConnected(context)) {
            App.getQueues().cancelAll(tag);
            StringRequest request = new StringRequest(Request.Method.GET, url, volleyInterface.getmSucessListener(), volleyInterface.getmErrorListener());
            request.setTag(tag);
            App.getQueues().add(request);
            App.getQueues().start();
        }else{
            toast(context);
        }
    }

    /***
     * post请求
     * @param url
     * @param tag 当前请求的tag，通过tag取消请求
     * @param volleyInterface
     * @param mapParams 请求的参数
     */
    public static void post(String url, String tag, VolleyInterface volleyInterface, final Map<String,String> mapParams, Context context){

        if (NetUtil.isConnected(context)) {
            PromptManager.showProgressDialog(context);
            App.getQueues().cancelAll(tag);
            StringRequest request = new StringRequest(Request.Method.POST, url, volleyInterface.getmSucessListener(), volleyInterface.getmErrorListener()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return mapParams;
                }
            };
            request.setTag(tag);
            App.getQueues().add(request);
            App.getQueues().start();
        }else{
            toast(context);
        }
    }
    private static void toast(Context context){
        CustomToast.showToast(context,"请检查你的网络连接",2000);
    }
}
