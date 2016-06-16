package xdemo.xq.com.xdemo.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by lhq on 2016/3/28.
 * Fragment懒加载
 */
public abstract class LazyFragment extends Fragment {

    protected boolean isVisible;
    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}
}
