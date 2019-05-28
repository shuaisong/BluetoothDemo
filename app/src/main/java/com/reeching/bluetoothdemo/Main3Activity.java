package com.reeching.bluetoothdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.reeching.hotfixlibrary.Constants;
import com.reeching.hotfixlibrary.FileUtils;
import com.reeching.hotfixlibrary.FixDexUtils;

import java.io.File;
import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("toolbar");
        setSupportActionBar(toolbar);
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
}
