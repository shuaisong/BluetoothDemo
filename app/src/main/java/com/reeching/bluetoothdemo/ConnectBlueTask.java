package com.reeching.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lenovo on 2019/5/17.
 * auther:lenovo
 * Date：2019/5/17
 */
public class ConnectBlueTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {
    private BluetoothDevice bluetoothDevice;
    private ConnectBlueCallBack callBack;

    public ConnectBlueTask(ConnectBlueCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LogUtil.d("onStartConnect");
        if (callBack != null) callBack.onStartConnect();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     *
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param socket The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(BluetoothSocket socket) {
        super.onPostExecute(socket);
        if (socket != null && !socket.isConnected()) {
            LogUtil.d("onConnectSuccess");
            if (callBack != null) callBack.onConnectSuccess(bluetoothDevice, socket);
        } else {
            LogUtil.d("onConnectFail");
            if (callBack != null) callBack.onConnectFail(bluetoothDevice);

        }
    }

    /**
     * <p>Applications should preferably override {@link #onCancelled(Object)}.
     * This method is invoked by the default implementation of
     * {@link #onCancelled(Object)}.</p>
     *
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object[])} has finished.</p>
     *
     * @see #onCancelled(Object)
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param bluetoothDevices The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected BluetoothSocket doInBackground(BluetoothDevice... bluetoothDevices) {
        bluetoothDevice = bluetoothDevices[0];
        BluetoothSocket socket = null;
        try {
//            LogUtil.d("开始连接socket,uuid:" + ClassicsBluetooth.UUID);
            UUID uuid = UUID.randomUUID();
            LogUtil.d("开始连接socket,uuid:" + uuid);
            socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            if (socket != null && !socket.isConnected()) {
                socket.connect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("socket连接失败");
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                LogUtil.e("socket关闭失败");
            }
        }
        return socket;
    }
}
