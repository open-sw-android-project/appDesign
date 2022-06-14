package com.example.qr_check;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class WebViewCustom extends WebView {

        public WebViewCustom(Context context) {
            super(context);
        }

        public WebViewCustom(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public WebViewCustom(Context context, AttributeSet attributeSet, int defStyle) {
            super(context, attributeSet, defStyle);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            requestDisallowInterceptTouchEvent(true);
            return super.onTouchEvent(event);
        }
    }