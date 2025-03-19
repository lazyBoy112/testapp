package com.sanglv.testapp

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class MediaSessionHelper(context: Context) {
    private val mediaSession: MediaSession = MediaSession(context, "MyMediaSession")
    private val context: Context = context

    @RequiresApi(Build.VERSION_CODES.S)
    public fun setMediaButtonReceiver(mbr: ComponentName) {
        val intent: Intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        intent.setComponent(mbr)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        Log.d("sanglv", "setup broadcast")
        mediaSession.setMediaButtonBroadcastReceiver(mbr)
        Log.d("sanglv", "compelete")
        mediaSession.isActive = true
    }

    public fun getMediaSession(): MediaSession {
        return mediaSession
    }
}
