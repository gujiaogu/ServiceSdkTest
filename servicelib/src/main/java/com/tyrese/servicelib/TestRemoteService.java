package com.tyrese.servicelib;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class TestRemoteService extends Service {
    public TestRemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.tyrese.servicelib.REMOTECALL_PERMISSION");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d("TestService", "bind failed");
            return null;
        }
        return mBinder;
    }


    private IBinder mBinder = new ITestCall.Stub() {
        @Override
        public void printPid() throws RemoteException {
            System.out.println("remote service id : " + Process.myPid());
        }
    };
}
