package com.reeching.bluetoothdemo;

import android.bluetooth.BluetoothDevice;

/**
 * Created by lenovo on 2019/5/17.
 * auther:lenovo
 * Date：2019/5/17
 */
public interface PinBlueCallBack {
    void onBondRequest();

    void onBondFail(BluetoothDevice device);

    void onBonding(BluetoothDevice device);

    void onBondSuccess(BluetoothDevice device);
}
