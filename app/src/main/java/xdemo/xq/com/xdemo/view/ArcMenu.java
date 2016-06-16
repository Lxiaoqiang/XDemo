package xdemo.xq.com.xdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import xdemo.xq.com.xdemo.R;


/**
 * Created by bob on 2016/3/31.
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {
    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;
    private Status mCurrentStatus = Status.CLOSE;
    /**
     * 菜单的主按钮
     */
    private View mCButton;
    private OnMenuItemClickListener mOnMenuItemClickListener;

    /**
     * 菜单的状态枚举类
     */
    public enum Status {
        OPNE, CLOSE
    }

    /**
     * 菜单的位置枚举类
     */
    public enum Position {
        LEFT_BOTTOM, LEFT_TOP, RIGHT_BOTTOM, RIGHT_TOP
    }

    /**
     * 点击子菜单项的回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }

    public void setmOnMenuItemClickListener(OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        int pos = a.getInt(R.styleable.ArcMenu_position, 3);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        a.recycle();

        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                        getResources().getDisplayMetrics()));
        Log.e("TAG", "position = " + mPosition + "  radius = " + mRadius);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量Child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //创建主按钮
            layoutCButton();

            int count = getChildCount();
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(View.GONE);
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();
                //如果菜单的位置在左下。右下
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                //右上 右下
                if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + width);
    }

    @Override
    public void onClick(View v) {
        mCButton = findViewById(R.id.id_button);
        if (mCButton == null) {
            mCButton = getChildAt(0);
        }
        rotateCButton(v, 0f, 360f, 300);
        toggleMenu(300);
    }

    /**
     * 切换菜单
     */
    public void toggleMenu(int duration) {
        //为menuItem添加平移动画和旋转动画
        int count = getChildCount();
        for (int i = 0;i<count-1;i++){
            final View childView = getChildAt(i+1);
            childView.setVisibility(View.VISIBLE);
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xflag = 1;
            int yflag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM){
                xflag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP){
                yflag = -1;
            }
            AnimationSet animSet = new AnimationSet(true);
            Animation tranAnim = null;
            //to open
            if (mCurrentStatus == Status.CLOSE){
                tranAnim = new TranslateAnimation(xflag*cl,0,yflag*ct,0);
                childView.setClickable(true);
                childView.setFocusable(true);
            }else{//to close
                tranAnim = new TranslateAnimation(0,xflag*cl,0*ct,yflag*ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i*100)/count);
//            Interpolator interpolator = new BounceInterpolator();
//            tranAnim.setInterpolator(interpolator);
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus==Status.CLOSE){
                        childView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            //旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);
            animSet.addAnimation(rotateAnim);
            animSet.addAnimation(tranAnim);
            childView.startAnimation(animSet);

            final int position = i+1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener!=null){
                        mOnMenuItemClickListener.onClick(childView,position);
                    }
                    menuItemAnim(position-1);
                    changeStatus();
                }
            });

        }
        //切换菜单状态
        changeStatus();
    }

    /**
     * 添加menuItem的点击动画
     */
    private void menuItemAnim(int pos) {
        for (int i=0;i<getChildCount()-1;i++){
            View childView = getChildAt(i+1);
            if (i==pos){
                childView.startAnimation(scaleBigAnim(300));
            }else{
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    private Animation scaleSmallAnim(int duration) {
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f,0.0f);
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(alphaAnim);
        animSet.setDuration(duration);
        animSet.setFillAfter(true);
        return animSet;
    }

    /**
     * 为当前点击Item设置变大和透明度降低动画
     * @param duration
     * @return
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f,2.0f,1.0f,2.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f,0.0f);
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(alphaAnim);
        animSet.setDuration(duration);
        animSet.setFillAfter(true);
        return animSet;
    }

    /**
     * 切换菜单状态
     */
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus==Status.CLOSE? Status.OPNE:Status.CLOSE);
    }

    private void rotateCButton(View v, float start, float end, int duration) {
        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}
