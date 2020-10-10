package com.example.chulangnghiem

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chulangnghiem.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContentView(R.layout.activity_main)
        xuLyWeb()

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

           topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mn_menu -> {
                    val urlnight: String = "file:///android_asset/CHULANGNGHIEMNIGHT.htm"
                    myWeb.loadUrl(urlnight)
                    true
                }
                R.id.mn_toi ->{
                    val url: String = "file:///android_asset/CHULANGNGHIEM.htm"
                    myWeb.loadUrl(url)
                    true
                }
                R.id.menu_gioiThieu ->{
                    startActivity(Intent(this,GioiThieu::class.java))
                    true
                }

                R.id.menu_khac -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/search?q=pub%3APhat93&c=apps")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/search?q=pub%3APhat93&c=apps")
                            )
                        )
                    }
                    true
                }
                else -> false
            }
        }

    }

    private fun xuLyWeb() {
        val url: String = "file:///android_asset/CHULANGNGHIEM.htm"
        myWeb.loadUrl(url)
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





