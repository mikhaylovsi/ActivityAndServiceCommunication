package com.example.sergei.serviceandpendingintentdelte;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int TASK1_CODE = 1;
    public static final int TASK2_CODE = 2;
    public static final int TASK3_CODE = 3;

    public static String PARAM_TIME = "time";
    public static String PARAM_PINTENT = "pintent";

    public static int STATUS_START = 100;
    public static int STATUS_FINISH = 200;

    public static String PARAM_RESULT = "result";

    TextView tv1;
    TextView tv2;
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

        tv1.setText("Task 1");
        tv2.setText("Task 2");
        tv3.setText("Task 3");
    }


    public void onClickStart(View view) {

        PendingIntent pi;
        Intent intent;

        pi = createPendingResult(TASK1_CODE, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 7).putExtra(PARAM_PINTENT, pi);
        startService(intent);

        pi = createPendingResult(TASK2_CODE, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 4).putExtra(PARAM_PINTENT, pi);
        startService(intent);

        pi = createPendingResult(TASK3_CODE, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 6).putExtra(PARAM_PINTENT, pi);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == STATUS_START) {

            switch (requestCode) {
                case TASK1_CODE:
                    tv1.setText("Task 1 start");
                    break;
                case TASK2_CODE:
                    tv2.setText("Task 2 start");
                    break;
                case TASK3_CODE:
                    tv3.setText("Task 3 start");
                    break;

            }

        }


        if (resultCode == STATUS_FINISH) {

            String result = data.getStringExtra(PARAM_RESULT);

            switch (requestCode) {
                case TASK1_CODE:
                    tv1.setText("Task 1 result " + result);
                    break;
                case TASK2_CODE:
                    tv2.setText("Task 2 result " + result);
                    break;
                case TASK3_CODE:
                    tv3.setText("Task 3 result " + result);
                    break;

            }

        }


    }
}
