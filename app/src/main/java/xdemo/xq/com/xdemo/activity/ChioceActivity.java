package xdemo.xq.com.xdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xdemo.xq.com.xdemo.R;
import xdemo.xq.com.xdemo.adapter.ChoiceAdapter;
import xdemo.xq.com.xdemo.entity.User;
import xdemo.xq.com.xdemo.toast.CustomToast;

public class ChioceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private ListView lvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chioce);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        lvv = (ListView) findViewById(R.id.lv_choice);
        btn = (Button) findViewById(R.id.btn_choice);
    }

    private void initEvent() {
        btn.setOnClickListener(this);
        lvv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    List<User> list = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 20; i++) {
            list.add(new User(i, "大毅" + i, i % 2 == 0 ? 1 : 2));
        }
        lvv.setAdapter(new ChoiceAdapter(list, this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choice:
                if (lvv.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
                    int positon = lvv.getCheckedItemPosition();
                    CustomToast.showToast(this, list.get(positon).getName(), 2000);
                } else if (lvv.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                    //对应adapter需要重写hasStableIds并返回true才能拿到值
                    long[] positons = lvv.getCheckedItemIds();
                    for (int i = 0; i < positons.length; i++) {
                        CustomToast.showToast(this, list.get((int) positons[i]).getName(), 2000);
                    }
                }
                break;
        }
    }
}
