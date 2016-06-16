package xdemo.xq.com.xdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.nfc.Tag;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.entity.VideoBean;
import xdemo.xq.com.xdemo.util.DensityUtils;
import xdemo.xq.com.xdemo.util.L;
import xdemo.xq.com.xdemo.util.SharedPreferencesHelper;

public class MediaRecorderActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener ,Camera.PreviewCallback{
    private final String TAG = MediaRecorderActivity.class.getSimpleName();
    /**
     * 开始录制
     */
    private Button btn_start;
    /**
     * 删除
     */
    private Button btn_delete;
    /**
     * 完成
     */
    private Button btn_complete;
    /**
     * 闪动的imageview
     */
    private ImageView iv_shan;
    /**
     * 进度条
     */
    private LinearLayout ll_progress;

    private SurfaceView surfaceView;
    /**
     * 摄像头
     */
    private Camera mCamera;

    private ArrayList<VideoBean> list;
    /**
     * 视频最大长度
     */
    private final int maxTime = 10;
    /**
     * 视频最小长度
     */
    private final int minTime = 5;
    /**
     * 点击删除，偶数才删除
     */
    private int deleteEven = 0;
    /**
     * 前面录制了多少时间
     */
    private int old = 0;
    /**
     * 当前录制了多少秒
     */
    private int currentTime = 0;

    private TimeCount timeCount;
    /**
     * 录制视频的类
     */
    private MediaRecorder mMediaRecorder;
    /**
     * 视频输出质量
     */
    private CamcorderProfile mProfile;
    /**
     * 回调
     */
    private SurfaceHolder mHolder;
    /**
     * 摄像头参数
     */
    private Camera.Parameters mParameters;
    private String pPath = Environment.getExternalStorageDirectory() + "/RRRRRR/";
    /**
     * 录制视频保存文件
     */
    private String vedioPath;

    /**
     * 1表示后置，0表示前置
     */
    private int cameraPosition = 1;
    /**
     * 屏幕宽度
     */
    private int mWidth;
    private VideoBean bean;
    private Handler handler = new Handler();
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        L.i(TAG,data.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        initView();
        initListener();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCamera = getCamera();
        if (mCamera != null) {
            // 因为android不支持竖屏录制，所以需要顺时针转90度，让其游览器显示正常
            mCamera.setDisplayOrientation(90);
            mCamera.lock();
            initCameraParameters();
        }
    }

    private void initView() {
        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_start = (Button) findViewById(R.id.btn_start);
        iv_shan = (ImageView) findViewById(R.id.iv_shan);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
        surfaceView = (SurfaceView) findViewById(R.id.sufaceviewre);
    }

    private void init() {
        list = new ArrayList<>();
        handler.postDelayed(shan, 0);
        mWidth = getWindowManager().getDefaultDisplay().getWidth();
        //创建文件夹
        File file = new File(pPath);
        if (!file.exists()) {
            file.mkdir();
        }
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        readVideoPreferences();
    }

    private void initListener() {
        btn_complete.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (ll_progress.getChildCount() > 1)
                            ll_progress.getChildAt(ll_progress.getChildCount() - 2).setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        deleteEven = 0;
                        addProgressView();
                        timeCount = new TimeCount(maxTime * 1000 - old, 50);
                        timeCount.start();
                        startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        old = currentTime + old;
                        addBlackView();
                        timeCount.cancel();
                        stopRecord();
                        break;
                }
                return false;
            }
        });
    }

    private void addProgressView() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) DensityUtils.px2dp(this, 1), LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        ll_progress.addView(imageView);
    }

    /**
     * 每次录制后添加显示间隔
     */
    private void addBlackView() {
        ImageView interval = new ImageView(this);
        interval.setBackgroundColor(getResources().getColor(R.color.black01));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) DensityUtils.px2dp(this, 5), LinearLayout.LayoutParams.MATCH_PARENT);
        interval.setLayoutParams(layoutParams);
        ll_progress.addView(interval);
    }

    /**
     * 第一次点击删除，变色
     */
    private void addDeleteColor() {
        ImageView imageView = (ImageView) ll_progress.getChildAt(ll_progress.getChildCount() - 2);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 初始化摄像头参数
     */
    private void initCameraParameters() {
        // 初始化摄像头参数
        mParameters = mCamera.getParameters();

        mParameters.setPreviewSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        mParameters.setPreviewFrameRate(mProfile.videoFrameRate);

        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        // 设置白平衡参数。
        String whiteBalance = SharedPreferencesHelper.getValueByKey(this, "pref_camera_whitebalance_key", "auto");
        if (isSupported(whiteBalance, mParameters.getSupportedWhiteBalance())) {
            mParameters.setWhiteBalance(whiteBalance);
        }

        // 参数设置颜色效果。
        String colorEffect = SharedPreferencesHelper.getValueByKey(this, "pref_camera_coloreffect_key", "none");
        if (isSupported(colorEffect, mParameters.getSupportedColorEffects())) {
            mParameters.setColorEffect(colorEffect);
        }

        try {
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置参数
     */
    private void readVideoPreferences() {
        String quality = SharedPreferencesHelper.getValueByKey(this, "pref_video_quality_key", "high");

        boolean videoQualityHigh = getVideoQuality(quality);

        // 设置视频质量。
        Intent intent = getIntent();
        if (intent.hasExtra(MediaStore.EXTRA_VIDEO_QUALITY)) {
            int extraVideoQuality = intent.getIntExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            videoQualityHigh = (extraVideoQuality > 0);
        }

        videoQualityHigh = false;
        mProfile = CamcorderProfile.get(videoQualityHigh ? CamcorderProfile.QUALITY_HIGH : CamcorderProfile.QUALITY_LOW);
        mProfile.videoFrameWidth = (int) (mProfile.videoFrameWidth * 2.0f);
        mProfile.videoFrameHeight = (int) (mProfile.videoFrameHeight * 2.0f);
        mProfile.videoBitRate = 256000 * 3;

        CamcorderProfile highProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mProfile.videoCodec = highProfile.videoCodec;
        mProfile.audioCodec = highProfile.audioCodec;
        mProfile.fileFormat = MediaRecorder.OutputFormat.MPEG_4;
    }

    private static boolean isSupported(String value, List<String> supported) {
        return supported == null ? false : supported.indexOf(value) >= 0;
    }

    public static boolean getVideoQuality(String quality) {
        return "youtube".equals(quality) || "high".equals(quality);
    }

    /**
     * 开启预览
     */
    private void setStartPreView(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取camera
     *
     * @return
     */
    private Camera getCamera() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {

        }
        return camera;
    }

    /**
     * 释放camera
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    /**
     * 闪动图片
     */
    private Runnable shan = new Runnable() {
        @Override
        public void run() {
            if (iv_shan.isShown()) {
                iv_shan.setVisibility(View.GONE);
            } else {
                iv_shan.setVisibility(View.VISIBLE);
            }
            handler.postDelayed(shan, 500);
        }
    };

    private void startRecord() {
        try {
            bean = new VideoBean();
            vedioPath = pPath + System.currentTimeMillis() + ".mp4";
            bean.setPath(vedioPath);
            mCamera.unlock();
            //设置预览视频帧回调
            mCamera.setOneShotPreviewCallback(this);
            mMediaRecorder = new MediaRecorder();// 创建mediaRecorder对象
            mMediaRecorder.setCamera(mCamera);
            // 设置录制视频源为Camera(相机)
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setProfile(mProfile);
            // mMediaRecorder.setVideoSize(560,560);//设置视频大小（分辨率）
            mMediaRecorder.setVideoEncodingBitRate(1024 * 1024);// 设置视频一次写多少字节(可调节视频空间大小)
            // 最大期限
            mMediaRecorder.setMaxDuration(35 * 1000);
            // 第4步:指定输出文件 ， 设置视频文件输出的路径
            mMediaRecorder.setOutputFile(vedioPath);
            mMediaRecorder.setPreviewDisplay(mHolder.getSurface());
            // // 设置保存录像方向
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                if (cameraPosition == 1) {
                    //由于不支持竖屏录制，后置摄像头需要把视频顺时针旋转90度、、但是视频本身在电脑上看还是逆时针旋转了90度
                    mMediaRecorder.setOrientationHint(90);
                } else if (cameraPosition == 0) {
                    //由于不支持竖屏录制，前置摄像头需要把视频顺时针旋转270度、、而前置摄像头在电脑上则是顺时针旋转了90度
                    mMediaRecorder.setOrientationHint(270);
                }
            }
            mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {

                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {

                }
            });
            mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

                @Override
                public void onError(MediaRecorder mr, int what, int extra) {

                }
            });
            // 第6步:根据以上配置准备MediaRecorder
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        if (bean != null) {
//            if (list.size() > 0) {
//                bean.setTime(now - list.get(list.size() - 1).getTime());
//            } else {
//                bean.setTime(now);
//            }
//            bean.setCameraPosition(cameraPosition);
            list.add(bean);
        }

        if (mMediaRecorder != null) {
            try {
                // 停止录像，释放camera
                mMediaRecorder.setOnErrorListener(null);
                mMediaRecorder.setOnInfoListener(null);
                mMediaRecorder.stop();
                // 清除recorder配置
                mMediaRecorder.reset();
                // 释放recorder对象
                mMediaRecorder.release();
                mMediaRecorder = null;
                // 没超过3秒就删除录制所有数据
//                if (old < 3000) {
//                    clearList();
//                }
            } catch (Exception e) {
//                clearList();
            }
        }
    }



    private class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {// 参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示 millisUntilFinished:倒计时剩余时间
            currentTime = (int) (maxTime * 1000 - millisUntilFinished - old);
            if (ll_progress.getChildCount() > 0) {
                ImageView iv = (ImageView) ll_progress.getChildAt(ll_progress.getChildCount() - 1);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
                params.width = (int) (((float) currentTime / 1000f) * (mWidth / maxTime)) + 1;
                L.i("currentTime=========" + currentTime + "");
                L.i("old=========" + old + "");
                L.e("Width============", (int) (currentTime / 1000f) * (mWidth / maxTime) + 1 + "");
                iv.setLayoutParams(params);
            }
        }

        @Override
        public void onFinish() {// 计时完毕时触发

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                deleteEven++;
                addDeleteColor();
                if (deleteEven % 2 == 0) {
                    if (ll_progress.getChildCount() > 1) {
                        ll_progress.removeViewAt(ll_progress.getChildCount() - 1);
                        ll_progress.removeViewAt(ll_progress.getChildCount() - 1);
                    }
                    old = old - currentTime;
                    //删除本地文件
                    File file = new File(list.get(list.size() - 1).getPath());
                    if (file.exists()) {
                        file.delete();
                    }
                    list.remove(list.size()-1);
                } else {
                    if (ll_progress.getChildCount() > 1)
                        (ll_progress.getChildAt(ll_progress.getChildCount() - 2)).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;
        }
    }

    /**
     * 用户放弃该段视频，删除所有视频文件
     */
    private void deleteVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定放弃这段视频吗？");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                for (int i = 0; i < list.size(); i++) {
                    File file = new File(list.get(i).getPath());
                    if (file.exists()) {
                        file.delete();
                    }
                }
                finish();
            }
        });
        builder.create().show();
    }
    @Override
    public void onBackPressed() {
        deleteVideo();
    }
}
