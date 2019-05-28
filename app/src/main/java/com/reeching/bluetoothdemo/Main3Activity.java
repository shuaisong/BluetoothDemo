package com.reeching.bluetoothdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.reeching.hotfixlibrary.Constants;
import com.reeching.hotfixlibrary.FileUtils;
import com.reeching.hotfixlibrary.FixDexUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("toolbar");
        setSupportActionBar(toolbar);
        final TimeHandler handler = new TimeHandler(new WeakReference<Main3Activity>(this));
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.CHINA);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        Timer timer = new Timer(true);
        timer.schedule(task, strToDateLong(hehe + " 21:02:00"));
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
    public void fix(View view) {
        File sourcefile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
        File targetfile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                +File.separator+Constants.DEX_NAME);
        if (targetfile.exists())
            targetfile.delete();
        try {
            FileUtils.copyDexFile(sourcefile,targetfile);
            FixDexUtils.laodDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(View view) {
        ErrorClass.error();
    }

    static class TimeHandler extends Handler {
        TimeHandler(WeakReference<Main3Activity> weakReference) {
            this.weakReference = weakReference;
        }

        WeakReference<Main3Activity> weakReference;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d(msg.what);
        }
    }
}
