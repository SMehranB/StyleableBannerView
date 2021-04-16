package com.smb.bannerviewsample

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smb.bannerview.BannerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        banner1.start()
        banner2.start()
        banner3.start()
        banner4.start()
        banner5.start()
        banner6.start()

        val bv = BannerView(this)

        bv.apply {
            message = "This is a BannerView with different styleable BannerView properties."
            setTextSizeDP(16)
            setGradientColors(Color.GREEN, Color.RED)
            textStyle = Typeface.NORMAL
            textFont = R.font.dancing_script
            textColor = Color.WHITE
            setShadowParams(Color.LTGRAY, 10f, 10f, 5f)
            animationDuration = 15000
            highlightFont = R.font.dancing_script
            highlightStyle = Typeface.BOLD
            setHighlightText("BannerView", false)
            highlightColor = Color.MAGENTA
            setHighlightSize(18)
            isHighlightShadowEnabled = true
            isTextShadowEnabled = false
        }

//        viewHolder.addView(bv)
//
//        bv.start()


    }
}