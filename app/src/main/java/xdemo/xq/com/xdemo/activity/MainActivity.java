package xdemo.xq.com.xdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.fragment.FragmentA;
import xdemo.xq.com.xdemo.fragment.FragmentB;
import xdemo.xq.com.xdemo.fragment.FragmentC;
import xdemo.xq.com.xdemo.fragment.FragmentD;
import xdemo.xq.com.xdemo.util.BitMapUtil;
import xdemo.xq.com.xdemo.view.CircleImageView;
import xdemo.xq.com.xdemo.view.ImageViewWithTextView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    ViewPager pager;
    private List<Fragment> fragmentList = new ArrayList<>();
    ImageViewWithTextView one;
    ImageViewWithTextView two;
    ImageViewWithTextView three;
    ImageViewWithTextView four;
    CircleImageView headerView;
//    View leftContentView;
    private List<ImageViewWithTextView> tabList = new ArrayList<>();
//    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
    }

    private void initEvent() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        headerView.setOnClickListener(this);
    }

    private void initView() {
//        navigationView= (NavigationView) findViewById(R.id.nav);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        leftContentView = navigationView.getHeaderView(0);
        headerView = (CircleImageView) findViewById(R.id.custom_circle_hear_imageview);
        one = (ImageViewWithTextView) findViewById(R.id.indicator_one);
        two = (ImageViewWithTextView) findViewById(R.id.indicator_two);
        three = (ImageViewWithTextView) findViewById(R.id.indicator_three);
        four = (ImageViewWithTextView) findViewById(R.id.indicator_four);
        tabList.add(one);
        tabList.add(two);
        tabList.add(three);
        tabList.add(four);
        fragmentList.add(new FragmentA());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentC());
        fragmentList.add(new FragmentD());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    tabList.get(position).setIconAlpha(1 - positionOffset);
                    tabList.get(position + 1).setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetTab();
        one.setIconAlpha(1.0f);
    }
    private void initData(){
        Glide.with(this)
                .load("http://imgsrc.baidu.com/forum/pic/item/5c7d2d292df5e0fe0f0efcdb5c6034a85fdf72bc.jpg")
                .fitCenter().into(headerView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }
        fragmentList.get(3).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        resetTab();
        switch (v.getId()) {
            case R.id.indicator_one:
                tabList.get(0).setIconAlpha(1.0f);
                pager.setCurrentItem(0,false);
                break;
            case R.id.indicator_two:
                tabList.get(1).setIconAlpha(1.0f);
                pager.setCurrentItem(1,false);
                break;
            case R.id.indicator_three:
                tabList.get(2).setIconAlpha(1.0f);
                pager.setCurrentItem(2,false);//第二个参数false，取消viewpager的滑动动画
                break;
            case R.id.indicator_four:
                tabList.get(3).setIconAlpha(1.0f);
                pager.setCurrentItem(3,false);
                break;
            case R.id.custom_circle_hear_imageview:
                BitMapUtil.choseHeadImageFromGallery(this);
                break;
        }
    }

    private void resetTab() {
        for (ImageViewWithTextView im : tabList) {
            im.setIconAlpha(0);
        }
    }

    class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
