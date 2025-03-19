package com.sanglv.testapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast
import androidx.media.session.MediaButtonReceiver

class MyMediaService : Service() {

    private val mediaSessionCompatCallBack = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            super.onPlay()
            Toast.makeText(application, "Play Button is pressed!", Toast.LENGTH_SHORT).show()
        }

        override fun onPause() {
            super.onPause()
            Toast.makeText(application, "Pause Button is pressed!", Toast.LENGTH_SHORT).show()
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            Toast.makeText(application, "Next Button is pressed!", Toast.LENGTH_SHORT).show()
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            Toast.makeText(application, "Previous Button is pressed!", Toast.LENGTH_SHORT).show()
        }

        override fun onStop() {
            super.onStop()
            Toast.makeText(application, "Stop Button is pressed!", Toast.LENGTH_SHORT).show()
        }

        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            val intentAction = mediaButtonEvent?.action

            if (Intent.ACTION_MEDIA_BUTTON == intentAction) {
                val event = mediaButtonEvent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)

                event?.let {
                    val action = it.action
                    if (action == KeyEvent.ACTION_DOWN) {
                        when (it.keyCode) {
                            KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> {
                                // code for fast forward
                                return true
                            }
                            KeyEvent.KEYCODE_MEDIA_NEXT -> {
                                // code for next
                                return true
                            }
                            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                                // code for play/pause
                                Toast.makeText(application, "Play Button is pressed!", Toast.LENGTH_SHORT).show()
                                return true
                            }
                            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                                // code for previous
                                return true
                            }
                            KeyEvent.KEYCODE_MEDIA_REWIND -> {
                                // code for rewind
                                return true
                            }
                            KeyEvent.KEYCODE_MEDIA_STOP -> {
                                // code for stop
                                Toast.makeText(application, "Stop Button is pressed!", Toast.LENGTH_SHORT).show()
                                return true
                            }
                        }
                        return false
                    }
                    if (action == KeyEvent.ACTION_UP) {
                        // Handle action up if needed
                    }
                }
            }
            return super.onMediaButtonEvent(mediaButtonEvent)
        }
    }

    private lateinit var mediaSessionCompat: MediaSessionCompat

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show()
        Log.e("SERVICE", "onCreate")

        mediaSessionCompat = MediaSessionCompat(this, "MEDIA").apply {
            setCallback(mediaSessionCompatCallBack)
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
            val mStateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                            PlaybackStateCompat.ACTION_PAUSE or
                            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                            PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(mStateBuilder.build())
            isActive = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show()
        Log.e("SERVICE", "onDestroy")
        mediaSessionCompat.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show()
        Log.e("SERVICE_STARTUP", "onStart")

        MediaButtonReceiver.handleIntent(mediaSessionCompat, intent)

        return super.onStartCommand(intent, flags, startId)
    }



//    private lateinit var mediaSessionCompat: MediaSessionCompat
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        MediaBtnReceiver.handleIntent(mediaSessionCompat, intent)
//        val mediaAction = intent?.getIntExtra("media_action", -1)
//
//        when (mediaAction) {
//            KeyEvent.KEYCODE_MEDIA_PLAY -> Log.d("MediaService", "Play button pressed")
//            KeyEvent.KEYCODE_MEDIA_PAUSE -> Log.d("MediaService", "Pause button pressed")
//            KeyEvent.KEYCODE_MEDIA_NEXT -> Log.d("MediaService", "Next button pressed")
//            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> Log.d("MediaService", "Previous button pressed")
//        }
//
//        return START_STICKY
//    }

    override fun onBind(intent: Intent?): IBinder? = null
}
