package com.example.chulangnghiem

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.RemoteViews
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chulangnghiem.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    //test notifi
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "chulangnghiem"
    private val description = "Chú Lăng Nghiêm"

    private lateinit var mp: MediaPlayer
    var check_st: Boolean = false


    private val TAG = "AudioFocus"
    private val mOnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> {
                    Log.i(TAG, "AUDIOFOCUS_GAIN")
                    // Set volume level to desired levels
                    mp.start()
                }
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                    Log.i(TAG, "AUDIOFOCUS_GAIN_TRANSIENT")
                    // You have audio focus for a short time
                    mp.start()
                }
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK -> {
                    Log.i(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK")
                    // Play over existing audio
                    mp.start()
                }
                AudioManager.AUDIOFOCUS_LOSS -> {
                    Log.e(TAG, "AUDIOFOCUS_LOSS asdasd")


                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT")
                    // Temporary loss of audio focus - expect to get it back - you can keep your resources around
                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> Log.e(
                    TAG,
                    "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK"
                )
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE -> {
                    Log.e(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Tạo thông báo
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Media Player
        mp = MediaPlayer.create(this, R.raw.chulangnghiem)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)

//        //Full Screen hãy thay đổi  AppCompatActivity thành Activity
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        setContentView(R.layout.activity_main)
        xulyWebView()


        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menuMp3 -> {
                    if (mp.isPlaying) {
                        menuItem.isChecked
                        // Stop
                        mp.pause()
                        menuItem.setIcon(R.drawable.ic_baseline_music_note_24)
                    } else {
                        val gotFocus = requestAudioFocusForMyApp(this@MainActivity)
                        if (gotFocus) {
                            // Start
                            mp.start()
                            menuItem.setIcon(R.drawable.ic_baseline_music_off_24)
                            //Tạo thông báo
                            val intent = Intent(this,MainActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

                            val contentView = RemoteViews(packageName,R.layout.notification_layout)
                            contentView.setTextViewText(R.id.tv_title,"Chú Lăng Nghiêm")
                            contentView.setTextViewText(R.id.tv_content,"Nhấn để quay lại ứng dụng")

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                                notificationChannel.enableLights(true)
                                notificationChannel.lightColor = Color.GREEN
                                notificationChannel.enableVibration(false)
                                notificationManager.createNotificationChannel(notificationChannel)

                                builder = Notification.Builder(this,channelId)
                                    .setContent(contentView)
                                    .setSmallIcon(R.drawable.musicnotifi)
                                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_baseline_music_note_24))
                                    .setContentIntent(pendingIntent)

                            }else{

                                builder = Notification.Builder(this)
                                    .setContent(contentView)
                                    .setSmallIcon(R.drawable.musicnotifi)
                                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_baseline_music_note_24))
                                    .setContentIntent(pendingIntent)

                            }
                            notificationManager.notify(1234,builder.build())
                        }
                        // Start
                    }
                    true
                }

                R.id.menuST ->  {
                    if(menuItem.isChecked){
                        menuItem.setIcon(R.drawable.ic_sun)
                    } else if(check_st == false){
                        menuItem.isChecked
                        check_st = true
                        val urln: String = "file:///android_asset/CHULANGNGHIEMNIGHT.htm"
                        val myWebView: WebView = findViewById(R.id.myWeb)
                        myWebView.loadUrl(urln)
                        menuItem.setIcon(R.drawable.ic_sun)
                    }else if (check_st == true) {
                        check_st = false
                        val urln: String = "file:///android_asset/CHULANGNGHIEM.htm"
                        val myWebView: WebView = findViewById(R.id.myWeb)
                        myWebView.loadUrl(urln)
                        menuItem.setIcon(R.drawable.ic_night)
                    }
                    true
                }

                R.id.menuBook -> {
                    startActivity(Intent(this, GioiThieu::class.java))
                    true
                }
                else -> false
            }
        }

    }

    private fun xulyWebView() {
        //Load WebView
        var url: String = "file:///android_asset/CHULANGNGHIEM.htm"
        var myWebView: WebView = findViewById(R.id.myWeb)
        myWebView.loadUrl(url)
    }
    //Tao phuong thuc AlerDialog va nhan phim back quay ve
    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.alert)
        builder.setTitle("Mô Phật")
        builder.setMessage("Thí chủ muốn xuống núi?")
        builder.setPositiveButton("Mệt Nghỉ", DialogInterface.OnClickListener { dialog, which ->
            finish()
            mp.stop()
        })
        builder.setNegativeButton("Tới bến", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
    private fun requestAudioFocusForMyApp(context: Context): Boolean {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Request audio focus for playback
        val result = am.requestAudioFocus(
            mOnAudioFocusChangeListener,  // Use the music stream.
            AudioManager.STREAM_MUSIC,  // Request permanent focus.
            AudioManager.AUDIOFOCUS_GAIN
        )
        return if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d(TAG, "Audio focus received")
            true
        } else {
            Log.d(TAG, "Audio focus NOT received")
            false
        }
    }

    fun releaseAudioFocusForMyApp(context: Context) {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.abandonAudioFocus(mOnAudioFocusChangeListener)
    }

}



