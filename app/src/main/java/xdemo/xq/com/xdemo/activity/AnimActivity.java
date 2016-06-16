package xdemo.xq.com.xdemo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import xdemo.xq.com.xdemo.R;

public class AnimActivity extends Activity {

    private ImageView anim;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        initView();
        ValueAnimator animator = ObjectAnimator.ofFloat(anim,"alpha",1.0f,0.3f,1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.start();

        AnimatorSet set = new AnimatorSet();
        set.play(animator);

    }

    private void initView(){
        anim = (ImageView) findViewById(R.id.iv_anim);
    }
}
