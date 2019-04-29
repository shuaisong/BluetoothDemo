package com.reeching.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {
    private static final String TAG = BluetoothReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        BluetoothClass bluetoothClass = intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);
        switch (action) {
            case BluetoothDevice.ACTION_ACL_CONNECTED:
                if (addBluetooth != null) addBluetooth.connectBluetooth(device);
                LogUtil.d("onReceive: " + "蓝牙设备:" + device.getName() + "已链接");
                Toast.makeText(context, "蓝牙设备:" + device.getName() + "已链接", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                if (addBluetooth != null) addBluetooth.disconnectBluetooth(device);
                LogUtil.d("onReceive: " + "蓝牙设备:" + device.getName() + "已断开");
                Toast.makeText(context, "蓝牙设备:" + device.getName() + "已断开", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int status = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (status) {
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(context, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(context, "蓝牙已打开", Toast.LENGTH_SHORT).show();
//                         LogUtil.d( "onReceive: " + BluetoothAdapter.getDefaultAdapter().startDiscovery());
                }
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                LogUtil.d("开始搜索蓝牙设备");
                Toast.makeText(context, "开始搜索蓝牙设备", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                LogUtil.d("搜索蓝牙设备结束");
                Toast.makeText(context, "搜索蓝牙设备结束", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothDevice.ACTION_FOUND:
                if (addBluetooth != null) addBluetooth.addBluetooth(device);
                Toast.makeText(context, "找到蓝牙设备" + device.getName(), Toast.LENGTH_SHORT).show();
//                LogUtil.d("onReceive: getName" + device.getName());
//                LogUtil.d("onReceive: " + bluetoothClass.toString());
                break;
        }
    }

    private AddBluetooth addBluetooth;

    void setAddBluetooth(AddBluetooth addBluetooth) {
        this.addBluetooth = addBluetooth;
    }

    interface AddBluetooth {
        void addBluetooth(BluetoothDevice device);

        void connectBluetooth(BluetoothDevice device);

        void disconnectBluetooth(BluetoothDevice device);
    }
}

