package com.sanglv.testapp

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast

class MediaBtnReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR)
            when (state) {
                BluetoothAdapter.STATE_CONNECTED -> Log.d("sanglv","Tai nghe Bluetooth đã kết nối")
                BluetoothAdapter.STATE_DISCONNECTED -> Log.d("sanglv","Tai nghe Bluetooth đã ngắt kết nối")
            }
        } else if (action == Intent.ACTION_MEDIA_BUTTON) {
            val event: KeyEvent? = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
            if (event != null) {
                Log.d("sanglv","Nút media đã được nhấn: ${event.keyCode}")
            }
        }
    }

}
