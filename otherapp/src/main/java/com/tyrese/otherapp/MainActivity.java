package com.tyrese.otherapp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tyrese.servicelib.ITestCall;
import com.tyrese.servicelib.TestRemoteService;

public class MainActivity extends AppCompatActivity {

    private ITestCall remoteService;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteService = ITestCall.Stub.asInterface(service);
            new TestTask().start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("com.tyrese.intent.REMOTE_CALL_TEST_ACTION");
        bindService(intent, sc, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sc);
    }

    private class TestTask extends Thread {
        @Override
        public void run() {
            super.run();
            if (remoteService != null) {
                try {
                    remoteService.printPid();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
