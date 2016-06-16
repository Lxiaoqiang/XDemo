package xdemo.xq.com.xdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lhq on 2016/3/25.
 * aidl
 */
public class IRemoteService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    IBinder iBinder = new IMyAidlInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }
    };
}
