package com.smb.bannerviewsample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.smb.bannerview.BannerView;

public class MainActivity extends AppCompatActivity {

    BannerView banner1, banner2, banner3, banner4, banner5, banner6;
    LinearLayout viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner1 = findViewById(R.id.banner1);
        banner2 = findViewById(R.id.banner2);
        banner3 = findViewById(R.id.banner3);
        banner4 = findViewById(R.id.banner4);
        banner5 = findViewById(R.id.banner5);
        banner6 = findViewById(R.id.banner6);

        viewHolder = findViewById(R.id.viewHolder);

        banner1.start();
        banner2.start();
        banner3.start();
        banner4.start();
        banner5.start();
        banner6.start();


        BannerView bv = new BannerView(this);

        bv.setMessage(getString(R.string.message));
        bv.setTextSizeDp(24);
        bv.setGradientColors(Color.GREEN, Color.RED);
        bv.setTextStyle(Typeface.NORMAL);
        bv.setTextFont(R.font.zendots_regular);
        bv.setTextColor(Color.WHITE);
        bv.setShadowParams(Color.LTGRAY, 10f, 10f, 5f);
        bv.setAnimationDuration(15000);
        bv.setHighlightText("BannerView", false);
        bv.setHighlightColor(Color.MAGENTA);
        bv.setHighlightFont(R.font.karantina_bold);
        bv.setHighlightStyle(Typeface.BOLD);
        bv.setHighlightSizeDp(30);
        bv.setHighlightShadowEnabled(true);
        bv.setTextShadowEnabled(false);

        viewHolder.addView(bv);

        bv.start();
    }
}