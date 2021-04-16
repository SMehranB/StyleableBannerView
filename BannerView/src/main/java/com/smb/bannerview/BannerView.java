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
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;

public class BannerView extends View {

    private final float verticalPadding = dpToPixel(8);

    public BannerView(Context context) {
        super(context);
        initAttributes(context, null);
    }

    public BannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttributes(context, attributeSet);
    }

    private LinearGradient shader = null;

    long animationDuration = 15000L;

    @ColorInt
    int shadowColor = 0;
    float shadowOffSetX = 0f;
    float shadowOffSetY = 0f;
    float shadowRadius = 0f;

    private final Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String highlightText = "";
    @ColorInt
    int highlightColor = 0;
    boolean highlightUnderlined = false;
    int highlightStyle = 0;
    int highlightFont = 0;
    float highlightSize = 0f;
    boolean highlightShadowEnabled = false;

    private final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | TextPaint.LINEAR_TEXT_FLAG);
    String message = "";
    @ColorInt
    int textColor = 0;
    float textSize = 0;
    int textStyle = 0;
    int textFont = 0;
    boolean textShadowEnabled = false;

    @ColorInt
    int gradientStart = Color.GREEN;
    @ColorInt
    int gradientEnd = Color.RED;

    private int textX = 0;
    private int textY = 0;


    @Override
    protected void onDraw(Canvas canvas) {

        textPaint.setShader(shader);
        if (highlightColor == 0) {
            highlightPaint.setColor(Color.WHITE);
            highlightPaint.setShader(shader);
        }

        if (highlightText == null) {
            canvas.drawText(message, textX, textY, textPaint);
        } else {
            drawSpannedText(canvas);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {

        TypedArray attr = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.BannerView, 0, 0);

        message = attr.getString(R.styleable.BannerView_android_text);
        if (message == null) message = "This is just an awesome styleable BannerView.";
        message += " ";

        textSize = attr.getDimension(R.styleable.BannerView_android_textSize, dpToPixel(16));
        textColor = attr.getInteger(R.styleable.BannerView_android_textColor, Color.BLACK);
        textStyle = attr.getInt(R.styleable.BannerView_android_textStyle, Typeface.NORMAL);
        textFont = attr.getResourceId(R.styleable.BannerView_bv_TextFont, 0);

        highlightText = attr.getString(R.styleable.BannerView_bv_highlightText); //May return null
        highlightSize = attr.getDimension(R.styleable.BannerView_bv_highlightSize, textSize);
        highlightColor = attr.getInteger(R.styleable.BannerView_bv_highlightColor, 0);
        highlightStyle = attr.getInt(R.styleable.BannerView_bv_highlightStyle, textStyle);
        highlightFont = attr.getResourceId(R.styleable.BannerView_bv_highlightFont, textFont);
        highlightUnderlined = attr.getBoolean(R.styleable.BannerView_bv_highlightUnderlined, false);

        shadowColor = attr.getInt(R.styleable.BannerView_android_shadowColor, 0);
        shadowOffSetX = attr.getFloat(R.styleable.BannerView_android_shadowDx, 0f);
        shadowOffSetY = attr.getFloat(R.styleable.BannerView_android_shadowDy, 0f);
        shadowRadius = attr.getFloat(R.styleable.BannerView_android_shadowRadius, 0f);

        gradientStart = attr.getInteger(R.styleable.BannerView_bv_gradientStart, 0);
        gradientEnd = attr.getInteger(R.styleable.BannerView_bv_gradientEnd, 0);

        highlightShadowEnabled = attr.getBoolean(R.styleable.BannerView_bv_enableHighlightShadow, true);
        textShadowEnabled = attr.getBoolean(R.styleable.BannerView_bv_enableTextShadow, true);

        animationDuration = attr.getInt(R.styleable.BannerView_android_animationDuration, 15000);

        //THIS IS TO SHOW PROPER VIEW SIZES IN THE LAYOUT VIEW
        textPaint.setTextSize(textSize);
        highlightPaint.setTextSize(highlightSize);

        attr.recycle();

    }

    private void drawSpannedText(Canvas canvas) {
        String[] messageParts = message.split(highlightText);

        for (int i = 0; i < messageParts.length - 1; i++) {
            canvas.drawText(messageParts[i], textX + getNextX(messageParts, i), textY, textPaint);
            canvas.drawText(highlightText, textX + getNextX(messageParts, i) + textPaint.measureText(messageParts[i]), textY, highlightPaint);
        }

        canvas.drawText(messageParts[messageParts.length - 1], textX + getNextX(messageParts, messageParts.length - 1), textY, textPaint);
    }

    private int getNextX(String[] textParts, int stage) {

        if (stage == 0) return 0;

        int result = 0;
        float highlightLength = highlightPaint.measureText(highlightText);

        for (int i = 0; i < stage; i++) {
            result += textPaint.measureText(textParts[i]) + highlightLength;
        }

        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int textHeight = (int) (textPaint.descent() - textPaint.ascent());
        int highlightHeight = (int) (highlightPaint.descent() - highlightPaint.ascent());

        int minHeight = (int) (Math.max(textHeight, highlightHeight) + verticalPadding * 2 + shadowOffSetY);

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                setMeasuredDimension(w, h);
                break;
            case MeasureSpec.AT_MOST:
                setMeasuredDimension(w, Math.min(minHeight, h));
                break;
            case MeasureSpec.UNSPECIFIED:
                setMeasuredDimension(w, minHeight);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        setTextPaintAttr();
        setHighlightPaintAttr();

        if (gradientStart != 0 && gradientEnd != 0) {
            shader = new LinearGradient(0f, 0f, getWidth(), 0f, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
        }

        int textHeight = (int) (textPaint.descent() + textPaint.ascent());
        int highlightHeight = (int) (highlightPaint.descent() + highlightPaint.ascent());

        textY = (int) ((getHeight() / 2) - (Math.min(textHeight, highlightHeight)) / 2 - shadowOffSetY / 2);
        textX = getWidth();

        super.onLayout(changed, left, top, right, bottom);
    }

    private void setTextPaintAttr() {
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        Typeface tf = Typeface.DEFAULT;
        if (textFont != 0) {
            tf = ResourcesCompat.getFont(getContext(), textFont);
        }
        textPaint.setTypeface(Typeface.create(tf, textStyle));
        if (textShadowEnabled) textPaint.setShadowLayer(shadowRadius, shadowOffSetX, shadowOffSetY, shadowColor);
    }

    private void setHighlightPaintAttr() {
        highlightPaint.setTextSize(textSize);
        highlightPaint.setColor(highlightColor);
        Typeface tf = Typeface.DEFAULT;
        if (highlightFont != 0) {
            tf = ResourcesCompat.getFont(getContext(), highlightFont);
        } else if (textFont != 0) {
            tf = ResourcesCompat.getFont(getContext(), textFont);
        }
        highlightPaint.setTypeface(Typeface.create(tf, highlightStyle));
        highlightPaint.setUnderlineText(highlightUnderlined);
        highlightPaint.setTextSize(highlightSize);
        if (highlightShadowEnabled) highlightPaint.setShadowLayer(shadowRadius, shadowOffSetX, shadowOffSetY, shadowColor);
    }

    private float dpToPixel(int dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private int getEndPosition() {

        int result = 0;
        if (highlightText != null) {

            String[] messageParts = message.split(highlightText);


            for (int i = 0; i < messageParts.length - 1; i++) {
                result += textPaint.measureText(messageParts[i]);
                //MEASURING THE TEXT PLUS AN EMPTY SPACE FOR THE TEXT TO FULLY EXIT THE SCREEN WHEN THE HIGHLIGHT TEXT IS LARGER IN SIZE
                result += highlightPaint.measureText(highlightText + " ");
            }

            result += textPaint.measureText(messageParts[messageParts.length - 1]);
        }else{
            result = (int) textPaint.measureText(message);
        }

        return result * -1;
    }

    public void start() {

        setTextPaintAttr();
        setHighlightPaintAttr();

        int startPos = getResources().getDisplayMetrics().widthPixels;
        int endPos = getEndPosition();

        ValueAnimator va = ValueAnimator.ofInt(startPos, endPos);
        va.addUpdateListener(valueAnimator -> {
            textX = (int) valueAnimator.getAnimatedValue();
            invalidate();
        });

        AnimatorSet textAnimation = new AnimatorSet();
        textAnimation.setDuration(animationDuration);
        textAnimation.setInterpolator(null);
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




    public long getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setShadowParams(@ColorInt int color, float xOffset, float yOffset, float radius) {
        this.shadowColor = color;
        this.shadowOffSetX = xOffset;
        this.shadowOffSetY = yOffset;
        this.shadowRadius = radius;

        requestLayout();
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public float getShadowOffSetX() {
        return shadowOffSetX;
    }

    public float getShadowOffSetY() {
        return shadowOffSetY;
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    public String getHighlightText() {
        return highlightText;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setHighlightText(String highlightText, boolean highlightUnderlined) {
        this.highlightText = highlightText;
        this.highlightUnderlined = highlightUnderlined;
    }

    public float getHighlightSize() {
        return highlightSize;
    }

    public void setHighlightSize(int size) {
        this.highlightSize = dpToPixel(size);
        highlightPaint.setTextSize(highlightSize);
        requestLayout();
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public boolean isHighlightUnderlined() {
        return highlightUnderlined;
    }

    public int getHighlightStyle() {
        return highlightStyle;
    }

    public void setHighlightStyle(int highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    public int getHighlightFont() {
        return highlightFont;
    }

    public void setHighlightFont(@FontRes int font) {
        this.highlightFont = font;
    }

    public boolean isHighlightShadowEnabled() {
        return highlightShadowEnabled;
    }

    public void setHighlightShadowEnabled(boolean highlightShadowEnabled) {
        this.highlightShadowEnabled = highlightShadowEnabled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(@ColorInt int color) {
        this.textColor = color;
    }

    public float getTextSizeDp() {
        return textSize / getResources().getDisplayMetrics().density;
    }

    public void setTextSizeDP(int size) {
        this.textSize = dpToPixel(size);
        textPaint.setTextSize(dpToPixel(size));
        requestLayout();
    }

    public int getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(int style) {
        this.textStyle = style;
        Typeface tf = Typeface.DEFAULT;
        if (textFont != 0) {
            tf = ResourcesCompat.getFont(getContext(), textFont);
        }
        textPaint.setTypeface(Typeface.create(tf, textStyle));
    }

    public int getTextFont() {
        return textFont;
    }

    public void setTextFont(@FontRes int font) {
        this.textFont = font;
        Typeface tf = Typeface.DEFAULT;
        if (textFont != 0) {
            tf = ResourcesCompat.getFont(getContext(), textFont);
        }
        textPaint.setTypeface(Typeface.create(tf, textStyle));
        requestLayout();
    }

    public boolean isTextShadowEnabled() {
        return textShadowEnabled;
    }

    public void setTextShadowEnabled(boolean textShadowEnabled) {
        this.textShadowEnabled = textShadowEnabled;
    }

    public int getGradientStart() {
        return gradientStart;
    }

    public void setGradientColors(int gradientStart, int gradientEnd) {
        this.gradientStart = gradientStart;
        this.gradientEnd = gradientEnd;
    }

    public int getGradientEnd() {
        return gradientEnd;
    }
}
