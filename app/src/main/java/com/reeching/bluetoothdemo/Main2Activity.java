package com.reeching.bluetoothdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);LogUtil.d("onCreate");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("onStart");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("onStop");
    }
}
