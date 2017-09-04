package com.example.sergei.serviceandpendingintentdelte;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    ExecutorService ex;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ex = Executors.newFixedThreadPool(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

        MyRun mr = new MyRun(time, startId, pi);
        ex.execute(mr);

        return super.onStartCommand(intent, flags, startId);

    }

    class MyRun implements Runnable{

        private final int time;
        private final PendingIntent pi;
        private final int startId;

        MyRun(int time, int startId, PendingIntent pi){
            this.time = time;
            this.pi = pi;
            this.startId = startId;
        }

        @Override
        public void run() {

            try{

                pi.send(MainActivity.STATUS_START);

                TimeUnit.SECONDS.sleep(time);

                Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, String.valueOf(time * 100));
                pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);

            } catch (Exception e){
                e.printStackTrace();
            }

            stop();

        }

        private void stop() {
            stopSelfResult(startId);
        }
    }

}
