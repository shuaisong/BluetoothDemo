package com.reeching.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2019/4/23.
 * auther:lenovo
 * Date：2019/4/23
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<BluetoothDevice> devices;
    private Context context;

    public MyAdapter(List<BluetoothDevice> devices, Context context) {
        this.devices = devices;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (itemClickListener != null)
            viewHolder.getViewById(android.R.id.text1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(i);
                    }
                }
            });

        ((TextView) viewHolder.getViewById(android.R.id.text1)).setText(devices.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    ItemClickListener itemClickListener;

    public void setOnItemClickListner(ItemClickListener itemClickListner) {
        this.itemClickListener = itemClickListner;
    }

    interface ItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        View getViewById(int idRes) {
            return itemView.findViewById(idRes);
        }
    }
}
