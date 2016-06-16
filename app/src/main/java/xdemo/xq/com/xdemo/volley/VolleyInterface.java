package xdemo.xq.com.xdemo.volley;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import xdemo.xq.com.xdemo.util.PromptManager;

/**
 * Created by lhq on 2016/2/3.
 */
public abstract class VolleyInterface {

    public abstract void volleySucess(String s);
    public abstract void volleyError(VolleyError error);
    public static Response.Listener<String> mSucessListener;
    public static Response.ErrorListener mErrorListener;
    public VolleyInterface(Response.Listener<String > sucessListener, Response.ErrorListener errorListener){
        this.mSucessListener = sucessListener;
        this.mErrorListener = errorListener;
    }
    public Response.Listener<String> getmSucessListener(){
        mSucessListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                PromptManager.closeProgressDialog();
                volleySucess(s);
            }
        };
        return  mSucessListener;
    }
    public Response.ErrorListener getmErrorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                PromptManager.closeProgressDialog();
                volleyError(volleyError);
            }
        };
        return mErrorListener;
    }
}
