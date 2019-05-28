package com.reeching.bluetoothdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.reeching.sdkdemo.SdkDemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends Activity {

    private BluetoothReceiver receiver;
    private MyAdapter myAdapter;
    private ArrayList<BluetoothDevice> devices;
    private ArrayList<BluetoothDevice> bondDevices;
    private BluetoothAdapter adapter;
    private MyAdapter myBondAdapter;
    private AsyncTask<BluetoothDevice, Integer, BluetoothSocket> socketAsyncTask;

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
        adapter = BluetoothAdapter.getDefaultAdapter();
        LogUtil.d("onCreate");

        SdkDemo sdkDemo = new SdkDemo();
        final int add = sdkDemo.add(1, 3);
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
        final RecyclerView bondList = findViewById(R.id.bondList);
        final TextView connected = findViewById(R.id.connected);
        devices = new ArrayList<>();
        myAdapter = new MyAdapter(devices, this);
        myAdapter.setOnItemClickListner(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                pin(devices.get(position));
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);

        bondList.setLayoutManager(new LinearLayoutManager(this));
        bondDevices = new ArrayList<>();
        myBondAdapter = new MyAdapter(bondDevices, this);
        myBondAdapter.setOnItemClickListner(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                connectDevice(bondDevices.get(position));
            }
        });
        bondList.setAdapter(myBondAdapter);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        Switch blueSwitch = findViewById(R.id.blueSwitch);
        if (isBlueEnable()) blueSwitch.setChecked(true);
        blueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (isSupportBlue()) {
                        openBlueAsync();
//                        openBlueSync();
                    } else {
                        Toast.makeText(MainActivity.this, "不支持蓝牙", Toast.LENGTH_LONG).show();
                    }
                } else {
                    adapter.disable();
                    bondDevices.clear();
                    devices.clear();
                    myAdapter.notifyDataSetChanged();
                    myBondAdapter.notifyDataSetChanged();
                }
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

        //配对
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        receiver = new BluetoothReceiver(new PinBlueCallBack() {
            @Override
            public void onBondRequest() {
                LogUtil.d("onBondRequest");
            }

            @Override
            public void onBondFail(BluetoothDevice device) {
                LogUtil.d("onBondFail");

            }

            @Override
            public void onBonding(BluetoothDevice device) {
                LogUtil.d("onBonding");

            }

            @Override
            public void onBondSuccess(BluetoothDevice device) {
                bondDevices.add(device);
                myBondAdapter.notifyItemInserted(bondDevices.size() - 1);
            }
        });

        receiver.setAddBluetooth(new BluetoothReceiver.AddBluetooth() {
            @Override
            public void addBluetooth(BluetoothDevice device) {
                if (device != null && device.getName() != null) {
                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        LogUtil.d(device.getName() + "::::" + device.getBondState());
                        if (!bondDevices.contains(device)) {
                            LogUtil.d(device.getName());
                            bondDevices.add(device);
                            myBondAdapter.notifyDataSetChanged();
                        }
                    } else if (!devices.contains(device)) {
                        LogUtil.d(device.getName() + "::::" + device.getBondState());
                        devices.add(device);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void connectBluetooth(BluetoothDevice device) {
              /*  connected.setVisibility(View.VISIBLE);
                connected.setText(device.getName());
                int position = devices.indexOf(device);
                devices.remove(device);
                myAdapter.notifyItemRemoved(position);*/
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

    private void connectDevice(BluetoothDevice device) {
        if (device == null) {
            LogUtil.d("bond device null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.d("Bluetooth not enable!");
            return;
        }
        //连接之前把扫描关闭
        if (adapter.isDiscovering()) {
            adapter.cancelDiscovery();
        }
        if (socketAsyncTask == null)
            socketAsyncTask = new ConnectBlueTask(new ConnectBlueCallBack() {
                @Override
                public void onStartConnect() {
                    LogUtil.d("onStartConnect");
                }

                @Override
                public void onConnectSuccess(BluetoothDevice device, BluetoothSocket socket) {
                    LogUtil.d("onConnectSuccess");

                }

                @Override
                public void onConnectFail(BluetoothDevice device) {
                    LogUtil.d("onConnectFail");

                }
            });
        socketAsyncTask.execute(device);
    }

    /**
     * 配对
     *
     * @param device
     */
    private void pin(BluetoothDevice device) {
        if (device == null) {
            LogUtil.e("device is null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.e("bluetooth is disable");
            return;
        }
        if (adapter.isDiscovering())
            adapter.cancelDiscovery();
        //判断设备是否配对，没有配对在配，配对了就不需要配了
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            LogUtil.d("attempt bond " + device.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond();
            } else {
                try {
                    Method createBond = device.getClass().getMethod("createBond");
                    Boolean returnValue = (Boolean) createBond.invoke(device);
                    boolean b = returnValue.booleanValue();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cancelPin(BluetoothDevice device) {
        if (device == null) {
            LogUtil.e("device is null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.e("bluetooth is disable");
            return;
        }
        //判断设备是否配对，没有配对就不用取消了
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            LogUtil.d("attempt bond " + device.getName());
            try {
                Method removeBond = device.getClass().getMethod("removeBond");
                removeBond.invoke(device);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 蓝牙是否打开
     *
     * @return
     */
    private boolean isBlueEnable() {
        return isSupportBlue() && adapter.isEnabled();
    }

    /**
     * 是否支持蓝牙
     *
     * @return
     */
    private boolean isSupportBlue() {
        return adapter != null;
    }

    /**
     * 自动打开蓝牙（异步：蓝牙不会立刻就处于开启状态）
     * 这个方法打开蓝牙不会弹出提示
     */
    private void openBlueAsync() {
        if (isSupportBlue()) {
            adapter.enable();
        }
    }

    /**
     * 自动打开蓝牙（同步）
     * * 这个方法打开蓝牙会弹出提示
     * * 需要在onActivityResult 方法中判断resultCode == RESULT_OK  true为成功
     */
    private void openBlueSync() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 36000); //3600为蓝牙设备可见时间
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            LogUtil.d("蓝牙开启成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    private boolean search() {
        if (!isBlueEnable()) {
            LogUtil.d("Bluetooth is not enable");
            return false;
        }
        if (adapter.isDiscovering()) adapter.cancelDiscovery();

        //此方法是个异步操作，一般搜索12秒
        return adapter.startDiscovery();
    }

    private boolean cancleSearch() {
        if (isSupportBlue()) {
            adapter.cancelDiscovery();
        }
        return true;
    }

    public void startB(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }


}
