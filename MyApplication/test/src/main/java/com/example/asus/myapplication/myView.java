package com.example.asus.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by asus on 2016/2/25.
 */
public class myView extends View {
    public myView(Context context) {
        super(context);
    }

    public myView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
    }
}
