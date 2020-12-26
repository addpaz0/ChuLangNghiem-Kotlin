package com.example.chulangnghiem

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chulangnghiem.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    private lateinit var mp: MediaPlayer
    var check_st: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                R.id.menuMp3 ->
                {
                    if (mp.isPlaying) {
                        menuItem.isChecked
                        // Stop
                        mp.pause()
                        menuItem.setIcon(R.drawable.ic_baseline_music_note_24)
                    } else {
                        // Start
                        mp.start()
                        menuItem.setIcon(R.drawable.ic_baseline_music_off_24)
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

}




