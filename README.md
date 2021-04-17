# StyleableBannerView
A styleable Banner View with the possibility of designating a highlighted String value 

## Features! [![](https://jitpack.io/v/SMehranB/StyleableBannerView.svg)](https://jitpack.io/#SMehranB/StyleableBannerView)

 •	 Text Color, Text Size, Text Font, Text Style
 
 •	 Highlight part of the text, Highlight Color, Highlight Size, Highlight Font, Highlight Style, Underline Highlight
 
 •	 Shadow Color, Shadow X Offset and Y Offset, Shadow Radius, Shadow can be disabled for the text and the highlighted part independantly
 
 •	 Gradient overlay for the whole banner (including the highlighted part) or just the Text (bot the highlighted text)
 
 •	 Easily call bannerView.start() in the onCreate() block and you are good to go! 

## Screen recording
 
 <img src="./screen_recording.gif" height="600">
 
# Install
## Gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	 implementation 'com.github.SMehranB:StyleableBannerView:1.0.0'
}
```
## Maven
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
	<groupId>com.github.SMehranB</groupId>
	<artifactId>StyleableBannerView</artifactId>
	<version>1.0.0</version>
</dependency>
 ```
# Use
 
## XML

```xml
<com.smb.bannerview.BannerView
    android:id="@+id/bv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    android:shadowColor="#80F4FF00"
    android:shadowDx="15"
    android:shadowDy="15"
    android:shadowRadius="1"
    android:text="@string/message"
    android:textColor="@color/white"
    android:textSize="24dp"
    app:bv_TextFont="@font/zendots_regular"
    app:bv_enableTextShadow="false"
    app:bv_highlightColor="#FF8400"
    app:bv_highlightSize="50dp"
    app:bv_highlightStyle="italic|bold"
    app:bv_highlightText="BannerView"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
 ```

## Kotlin
```kotlin
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

viewHolder.addView(bv)

bv.start() // DO NOT FORGET THIS LINE
```

## 📄 License
```text
MIT License

Copyright (c) 2021 Seyed Mehran Behbahani

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
