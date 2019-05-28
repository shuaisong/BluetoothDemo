package com.reeching.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * Created by lenovo on 2019/5/17.
 * auther:lenovo
 * Date：2019/5/17
 */
interface ConnectBlueCallBack {
    void onStartConnect();
    void onConnectSuccess(BluetoothDevice device, BluetoothSocket socket);
    void onConnectFail(BluetoothDevice device);
}
