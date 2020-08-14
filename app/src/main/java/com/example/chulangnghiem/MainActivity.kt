package com.example.chulangnghiem

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Full Screen hãy thay đổi  AppCompatActivity thành Activity
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
     //Load WebView
        xuLyWeb()
        //   xyLyWebN()

    }



    private fun xuLyWeb() {
        var url: String = "file:///android_asset/CHULANGNGHIEM.htm"

        val myWebView: WebView = findViewById(R.id.myWeb)

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.setSupportZoom(true)
        myWebView.settings.builtInZoomControls = true
        myWebView.settings.displayZoomControls = true
        myWebView.loadUrl(url)

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }

    //Tao phuong thuc AlerDialog va nhan phim back quay ve
    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.alert)
        builder.setTitle("Mô Phật")
        builder.setMessage("Thí chủ muốn xuống núi?")
        builder.setPositiveButton("Mệt Nghỉ", DialogInterface.OnClickListener { dialog, which ->
          finish()
        })
        builder.setNegativeButton("Tới bến", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


}


