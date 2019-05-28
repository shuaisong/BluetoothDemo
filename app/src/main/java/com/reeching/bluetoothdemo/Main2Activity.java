package com.reeching.bluetoothdemo;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private EditText topic;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);LogUtil.d("onCreate");
        content = findViewById(R.id.content);
        topic = findViewById(R.id.topic);
    }
    DbHelper user = new DbHelper(this, "user", null, 1);

    public void createDb(View view) {
        user.getWritableDatabase();
    }

    public void createTable(View view) {
        user.onCreate(user.getWritableDatabase());
    }

    public void updateDb(View view) {
        user.onUpgrade(user.getWritableDatabase(), user.getReadableDatabase().getVersion(), 2);

    }

    public void showData(View view) {

    }

    public void deleteData(View view) {
        user.getWritableDatabase().delete("dinary", "topic = ?", new String[]{topic.getText().toString()});

    }

    public void insertData(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("topic", topic.getText().toString());
        contentValues.put("content", content.getText().toString());
        user.getWritableDatabase().insert("dinary", null, contentValues);
    }

    public void updataData(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("topic", topic.getText().toString());
        contentValues.put("content", content.getText().toString());
        user.getWritableDatabase().update("dinary", contentValues, "topic = ?", new String[]{topic.getText().toString()});
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
