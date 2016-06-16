package xdemo.xq.com.xdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.adapter.WhetherAdapter;
import xdemo.xq.com.xdemo.contant.TestUrl;
import xdemo.xq.com.xdemo.dataanalysis.Data;
import xdemo.xq.com.xdemo.entity.Whether;
import xdemo.xq.com.xdemo.location.MLocation;
import xdemo.xq.com.xdemo.toast.CustomToast;
import xdemo.xq.com.xdemo.volley.VolleyHttp;
import xdemo.xq.com.xdemo.volley.VolleyInterface;

/**
 * Created by lhq on 2016/3/22.
 */
public class FragmentC extends LazyFragment {
    private boolean isPrepared = false;
    private boolean isFirstShow = true;
    private String appKey = "be93be5d38a0cb5894131cb1d75b9fcc";
    private RecyclerView recyclerView;
    private TextView tvDate;
    private TextView city;
    private TextView temture;
    private Whether whether;
    private MLocation location;
    private WhetherAdapter whetherAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_c_layout, null);
        initView(v);
        return v;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lv_fragmentC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        tvDate = (TextView) view.findViewById(R.id.tv_whether_date);
        city = (TextView) view.findViewById(R.id.tv_whether_city);
        temture = (TextView) view.findViewById(R.id.tv_whether_temture);
        isPrepared = true;
        initData();
    }

    private void initData() {
        location = new MLocation(getActivity());
        location.getPosition();
        location.setFinishEdListener(new MLocation.OnlocationFinished() {
            @Override
            public void locationFinished(String latitude, String longitude, String cityCode, String citys) {
                city.setText(citys);
            }
        });
        getWhether();

    }

    @Override
    public void onPause() {
        super.onPause();
        location.removeLocation();
    }

    private void getWhether() {
        try {
            VolleyHttp.get(TestUrl.WHETHER + "?format=2&cityname=" + URLEncoder.encode("上海", "utf-8") + "&key=" + appKey, "fragmentC", new VolleyInterface(VolleyInterface.mSucessListener, VolleyInterface.mErrorListener) {
                @Override
                public void volleySucess(String s) {
//                    whether = Data.getWhether(s);
//                    tvDate.setText("今天" + whether.getSk().getTime());
//                    city.setText(whether.getToday().getCity());
//                    temture.setText(whether.getSk().getTemp() + "°");
//                    whetherAdapter = new WhetherAdapter(whether.getFuture());
//                    whetherAdapter.setListener(new WhetherAdapter.OnWhetherItemClickListener() {
//                        @Override
//                        public void itemClickListener(Whether.Future future) {
//                            tvDate.setText(future.getDate());
//                            temture.setText(future.getTemperature());
//                        }
//                    });
//                    recyclerView.setAdapter(whetherAdapter);
                }

                @Override
                public void volleyError(VolleyError error) {
                    CustomToast.showToast(getActivity(), error.toString(), 2000);
                }
            }, getActivity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstShow)
            return;
        isFirstShow = false;
        initData();
    }

}
