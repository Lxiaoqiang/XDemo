package xdemo.xq.com.xdemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.activity.SlidingActivity;
import xdemo.xq.com.xdemo.contant.TestUrl;
import xdemo.xq.com.xdemo.toast.CustomToast;
import xdemo.xq.com.xdemo.util.BitMapUtil;
import xdemo.xq.com.xdemo.view.CircleImageView;

/**
 * Created by lhq on 2016/3/22.
 */
public class FragmentD extends LazyFragment implements View.OnClickListener {
    CircleImageView header;
    private boolean isPrepared = false;
    private boolean isFirstShow = true;
    TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_d_layout, null);
        initView(v);
        lazyLoad();
        return v;
    }
    /**
     * 初始化事件
     */
    private void initEvent() {
        header.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView(View v) {
        header = (CircleImageView) v.findViewById(R.id.img_circle_header);
        tv = (TextView) v.findViewById(R.id.tv_sliding);
        initEvent();

        isPrepared = true;
    }

    private void initData() {
        Glide.with(getActivity()).load(TestUrl.img).into(header);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstShow)
            return;
        isFirstShow = false;
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_circle_header:
                BitMapUtil.choseHeadImageFromGallery(getActivity());
                break;
            case R.id.tv_sliding:
                startActivity(new Intent(getActivity(), SlidingActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case BitMapUtil.CODE_GALLERY_REQUEST:
                BitMapUtil.cropRawPhoto(intent.getData(), getActivity());
                break;

            case BitMapUtil.SELECT_PIC_KITKAT:// 4.4版本的处理
                Uri selectedImage = intent.getData();
                String imagePath = BitMapUtil.getPath(getActivity(), selectedImage); // 获取图片的绝对路径
                Uri newUri = Uri.parse("file:///" + imagePath); // 将绝对路径转换为URL
                BitMapUtil.cropRawPhoto(newUri, getActivity());
                break;
            case BitMapUtil.CODE_CAMERA_REQUEST:
                if (BitMapUtil.hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            BitMapUtil.IMAGE_FILE_NAME);
                    BitMapUtil.cropRawPhoto(Uri.fromFile(tempFile),
                            getActivity());
                } else {
                    CustomToast.showToast(getActivity(), "没有SDCard!", 800);
                }

                break;
            case BitMapUtil.CODE_RESULT_REQUEST:
                if (intent != null) {
                    BitMapUtil.setImageToHeadView(intent, header, getActivity());
                }
        }
    }
}
