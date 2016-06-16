package xdemo.xq.com.xdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.view.deletelistview.ListViewCompat;
import xdemo.xq.com.xdemo.view.deletelistview.MessageItem;
import xdemo.xq.com.xdemo.view.deletelistview.SlideAdapter;

public class DeleteViewActivity extends AppCompatActivity {

    private ListViewCompat listViewCompat;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_view);
        initViews();
        initValues();
    }

    private void initViews() {
        listViewCompat = (ListViewCompat) findViewById(R.id.lv_delte);
        listViewCompat.setLayoutAnimation(getListAnim());
    }

    private void initValues() {
        for (int i =0;i<20;i++){
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.mipmap.ic_menu_emoticons;
                item.title = "腾讯新闻";
                item.msg = "斗鱼";
                item.time = "晚上18:18";
            } else {
                item.iconRes = R.mipmap.ic_menu_add;
                item.title = "小星星";
                item.msg = "欢迎你使用微信";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        listViewCompat.setAdapter(new SlideAdapter(this,mMessageItems));
    }
    private LayoutAnimationController getListAnim() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        set.addAnimation(animation);
//        Animation animation = new TranslateAnimation(1.0f, 0.0f, 1.0f, 0.0f);
//        animation.setDuration(1000);
//        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        return controller;
    }
}
