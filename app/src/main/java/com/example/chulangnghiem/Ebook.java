package com.example.chulangnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ebook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        ImageView giangGiai = findViewById(R.id.giangGiai);
        ImageView chiaCau  = findViewById(R.id.chiaCau);


        giangGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ebook.this, GioiThieu.class);
                startActivity(intent);
            }
        });
        chiaCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ebook.this,BangChiaCau.class);
                startActivity(intent);
            }
        });

    }
}