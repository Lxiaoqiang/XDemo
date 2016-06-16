package xdemo.xq.com.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import xdemo.xq.com.xdemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface iMyAidlInterface;
    EditText num1;
    EditText num2;
    EditText result;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("xdemo.xq.com.xdemo","xdemo.xq.com.xdemo.IRemoteService"));
        boolean b = bindService(intent,conn, Context.BIND_AUTO_CREATE);
        Log.i("bindService-------","-========================="+b);
        num1= (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        result = (EditText) findViewById(R.id.result);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int c = iMyAidlInterface.add(Integer.parseInt(num1.getText().toString()),Integer.parseInt(num2.getText().toString()));
                    result.setText(c+"");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.i("tag","远程异常");
                }
            }
        });
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //
            iMyAidlInterface =  IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //资源回收
            iMyAidlInterface = null;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
