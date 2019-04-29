package com.reeching.bluetoothdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.reeching.sdkdemo.SdkDemo;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private BluetoothReceiver receiver;
    private MyAdapter myAdapter;
    private ArrayList<BluetoothDevice> devices;
    private EditText topic;
    private EditText content;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("onSaveInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.d("onSaveInstanceState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        LogUtil.d("onRestoreInstanceState");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d("onCreate");
        content = findViewById(R.id.content);
        topic = findViewById(R.id.topic);
        SdkDemo sdkDemo = new SdkDemo();
        int add = sdkDemo.add(1, 3);
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density;
        LogUtil.d(density + "");
        Resources resources = getResources();
        DisplayMetrics displayMetrics1 = resources.getDisplayMetrics();
        density = displayMetrics1.density;
        LogUtil.d(density + "");
        RecyclerView mRecyclerView = findViewById(R.id.list);
        final TextView connected = findViewById(R.id.connected);
        devices = new ArrayList<>();
        myAdapter = new MyAdapter(devices, this);
        myAdapter.setOnItemClickListner(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final BluetoothDevice device = devices.get(position);
                /*final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
                UUID uuid = UUID.fromString(SPP_UUID);
                BluetoothSocket socket = null;
                try {
                    socket = devices.get(position).createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                LogUtil.d(device.getUuids());
                BluetoothGatt bluetoothGatt = device.connectGatt(MainActivity.this, true, new BluetoothGattCallback() {

                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        super.onConnectionStateChange(gatt, status, newState);
                        LogUtil.d(status + ":::" + newState);
                        switch (status) {
                            case BluetoothGatt.GATT_SUCCESS:
                                switch (newState) {
                                    case BluetoothProfile.STATE_CONNECTED:
                                        LogUtil.d("蓝牙设备:" + device.getName() + "已链接");
                                        break;
                                    case BluetoothProfile.STATE_DISCONNECTED:
                                        LogUtil.d("蓝牙设备:" + device.getName() + "已断开");
                                        gatt.close();
                                        break;
                                }
                                break;
                            case BluetoothGatt.GATT_FAILURE:
                                break;
                        }
                    }
                });
                bluetoothGatt.disconnect();
                bluetoothGatt.close();
                bluetoothGatt.connect();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        receiver = new BluetoothReceiver();
        receiver.setAddBluetooth(new BluetoothReceiver.AddBluetooth() {
            @Override
            public void addBluetooth(BluetoothDevice device) {
                if (device != null && device.getName() != null)
                    if (!devices.contains(device))
                        devices.add(device);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void connectBluetooth(BluetoothDevice device) {
                connected.setVisibility(View.VISIBLE);
                connected.setText(device.getName());
                int position = devices.indexOf(device);
                devices.remove(device);
                myAdapter.notifyItemRemoved(position);
            }

            @Override
            public void disconnectBluetooth(BluetoothDevice device) {
                connected.setVisibility(View.GONE);
                devices.add(device);
                myAdapter.notifyItemInserted(devices.size() - 1);
            }
        });
        registerReceiver(receiver, filter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");

        unregisterReceiver(receiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d("onConfigurationChanged");
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

    private void search() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
        if (adapter.isDiscovering()) {
            Log.d("BluetoothReceiver", "onReceive: isDiscovering");
        } else
            Log.d("BluetoothReceiver", "onReceive: " + adapter.startDiscovery());
        if (devices != null && devices.size() != 0) {
            devices.clear();
        }
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 36000); //3600为蓝牙设备可见时间
        startActivity(intent);
    }

    public void startB(View view) {
        startActivity(new Intent(this, Main2Activity.class));
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

    int i = 0;

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
}
