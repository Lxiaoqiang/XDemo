package xdemo.xq.com.xdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.activity.AnimActivity;
import xdemo.xq.com.xdemo.activity.ImageActivity;
import xdemo.xq.com.xdemo.activity.MediaRecorderActivity;
import xdemo.xq.com.xdemo.activity.ScorllPlayVideoActivity;
import xdemo.xq.com.xdemo.util.ClickStateUtils;
import xdemo.xq.com.xdemo.view.TypeTextView;

/**
 * Created by lhq on 2016/3/22.
 */
public class FragmentB extends LazyFragment implements View.OnClickListener{
    private boolean isPrepared = false;
    private boolean isFirstShow = true;
//    private ArcMenu arcMenu;
    private TypeTextView typeTextView;
    private Button buttonVcl,image,anim,qm,lz,go;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b_layout, null);
        initView(v);
        return v;
    }

    private void initView(View view) {
//        arcMenu = (ArcMenu) view.findViewById(R.id.id_menu);
//        typeTextView = (TypeTextView) view.findViewById(R.id.typeview);
        buttonVcl = (Button) view.findViewById(R.id.btn_vcl);
        image = (Button) view.findViewById(R.id.btn_image);
        anim = (Button) view.findViewById(R.id.btn_anim);
        qm = (Button) view.findViewById(R.id.btn_qm);
        lz = (Button) view.findViewById(R.id.btn_lz);
        go = (Button) view.findViewById(R.id.btn_go);
        go.setOnClickListener(this);
        lz.setOnClickListener(this);
        qm.setOnClickListener(this);
        anim.setOnClickListener(this);
        image.setOnClickListener(this);
        buttonVcl.setOnClickListener(this);
        ClickStateUtils.click(buttonVcl);
        isPrepared = true;
    }
    private void initData() {

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
        switch (v.getId()){
            case R.id.btn_vcl:
                ((TextView)getActivity().findViewById(R.id.tv_whether_date)).setText("aaaaaaaaaaaaaa");
                break;
            case R.id.btn_image:
                startActivity(new Intent(getActivity(), ImageActivity.class));
                break;
            case R.id.btn_anim:
                startActivity(new Intent(getActivity(), AnimActivity.class));
                break;
            case R.id.btn_qm:
                startActivity(new Intent(getActivity(), ScorllPlayVideoActivity.class));
                break;
            case R.id.btn_lz:
                startActivity(new Intent(getActivity(), MediaRecorderActivity.class));
                break;
            case R.id.btn_go:

                break;
        }
    }
}
