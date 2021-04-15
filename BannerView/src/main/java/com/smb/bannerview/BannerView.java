package com.smb.bannerview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorInt;

public class BannerView extends View {

    public BannerView(Context context){
        super(context);
        initAttributes(context, null);
    }
    public BannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttributes(context, attributeSet);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {

        TypedArray attr = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.BannerView, 0, 0);

        message = attr.getString(R.styleable.BannerView_android_text);
        if(message == null) message = "This is just a banner passing by. Nothing special to see here.";

        textSize = (int) attr.getDimension(R.styleable.BannerView_android_textSize, dpToPixel(16));
        textColor = attr.getInteger(R.styleable.BannerView_android_textColor, Color.BLACK);

        highlightText = attr.getString(R.styleable.BannerView_bv_highlightText); //May return null
        highlightColor = attr.getInteger(R.styleable.BannerView_bv_highlightColor, textColor);

        gradientStart = attr.getInteger(R.styleable.BannerView_bv_gradientStart, 0);
        gradientEnd = attr.getInteger(R.styleable.BannerView_bv_gradientEnd, 0);

        enableGlow = attr.getBoolean(R.styleable.BannerView_bv_enableGlow, false);

        animationDuration = (long) attr.getInt(R.styleable.BannerView_android_animationDuration, 15000);

        attr.recycle();
    }

    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    LinearGradient shader = null;

    long animationDuration = 15000L;

    String highlightText = "";
    @ColorInt
    int highlightColor = 0;

    String message = "";
    @ColorInt
    int textColor = Color.WHITE;
    int textSize = 16;

    @ColorInt
    int gradientStart = Color.GREEN;
    @ColorInt
    int gradientEnd = Color.RED;
    boolean enableGlow = false;

    int textX = 0;
    int textY = 0;


    @Override
    protected void onDraw(Canvas canvas) {

        setLayerType(LAYER_TYPE_SOFTWARE, textPaint);

        if(enableGlow) textPaint.setShadowLayer(textSize / 3f, 0f, 0f, textColor);

        if(shader != null) textPaint.setShader(shader);

        if (highlightText == null) {
            canvas.drawText(message, textX, textY, textPaint);
        } else {
            drawComplexText(canvas);
        }
    }

    private void drawComplexText(Canvas canvas) {
        String[] messageParts = message.split(highlightText);

        for (int i = 0; i < messageParts.length; i++) {
            canvas.drawText(messageParts[i], textX + getNextX(messageParts, i), textY, textPaint);
            if (i != messageParts.length - 1) {
                canvas.drawText(highlightText, textX + getNextX(messageParts, i) + textPaint.measureText(messageParts[i]), textY, highlightPaint);
            }
        }
    }

    private int getNextX(String[] textParts, int stage) {

        if(stage == 0) return 0;

        int result = 0;
        float highlightLength = textPaint.measureText(highlightText);

        for (int i = 0; i < stage; i++) {
            result += textPaint.measureText(textParts[i]) + highlightLength;
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (gradientStart != 0 && gradientEnd != 0) {
            shader = new LinearGradient(0f, 0f, getWidth(), 0f, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
        }

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textY = (int) ((getHeight() / 2) - (fm.ascent + fm.descent) / 2);
        textX = getWidth();

        super.onLayout(changed, left, top, right, bottom);
    }

    public void animateBanner() {

        highlightPaint.setTextSize(dpToPixel(24));
        highlightPaint.setColor(highlightColor);

        textPaint.setTextSize(dpToPixel(24));
        textPaint.setColor(textColor);

        ValueAnimator va = ValueAnimator.ofInt(getResources().getDisplayMetrics().widthPixels, (int) -textPaint.measureText(message));
        va.addUpdateListener(valueAnimator -> {
            textX = (int) valueAnimator.getAnimatedValue();
            invalidate();
        });

        AnimatorSet textAnimation = new AnimatorSet();
        textAnimation.setDuration(animationDuration);
        textAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        textAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                textAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        textAnimation.play(va);
        textAnimation.start();
    }

    private Float dpToPixel(int dp){
        return dp * getResources().getDisplayMetrics().density;
    }
}
