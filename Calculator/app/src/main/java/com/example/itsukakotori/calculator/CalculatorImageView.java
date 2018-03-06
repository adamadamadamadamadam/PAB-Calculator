package com.example.itsukakotori.calculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Asus on 03/03/2018.
 */

public class CalculatorImageView extends AppCompatImageView{

    String text;
    Paint paint;
    MainActivity activity;
    private int type, xMid, yMid;
    private float mLastTouchX, mLastTouchY;

    public CalculatorImageView(Context context) {
        super(context);
        init(context);
    }

    public CalculatorImageView(Context context, String s) {
        super(context);
        setText(s);
        init(context);
    }

    public CalculatorImageView(Context context, int type) {
        super(context);
        this.type = type;
        init(context);
    }

    public CalculatorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalculatorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setText(String s) {
        this.text = s;
    }

    private void init(final Context context){
        this.paint = new Paint();
        paint.setColor(Color.BLACK);
        this.activity = (MainActivity) context;
        this.setScaleType(ScaleType.MATRIX);
        xMid = this.getWidth() / 2;
        yMid = this.getHeight() / 2;
        System.out.println("AO");
    }

    public void setmLastTouch(float x, float y){
        this.mLastTouchX = x;
        this.mLastTouchY = y;
    }



    public float getmLastTouchX(){
        return mLastTouchX;
    }
    public float getmLastTouchY(){
        return mLastTouchY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == 1) {
            canvas.drawColor(Color.YELLOW);
        }
        else{
            canvas.drawColor(Color.GREEN);
        }
        this.paint.setTextSize(20);
        canvas.drawText(text, getWidth() / 2, getHeight() / 2, paint);
        Log.d("Gesture", "Drawn");
    }
}
