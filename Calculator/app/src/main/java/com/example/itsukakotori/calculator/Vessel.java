package com.example.itsukakotori.calculator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.widget.ImageView;

/**
 * Created by Itsuka Kotori on 3/6/2018.
 */

public abstract class Vessel {

    protected PointF middlePoint;
    protected float x;
    protected float y;
    protected float a,b;
    protected String type;

    public Vessel(){

    }

    abstract void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity);

    public boolean getField(float x, float y) {
        return x > (middlePoint.x - a) && x < (middlePoint.x + a) && y > (middlePoint.y - b) && y < (middlePoint.y + b);
    }

    abstract String getType();

    public void setMiddlePoint(float x, float y){
        middlePoint.set(x, y);
    }

    public PointF getMiddlePoint(){
        return middlePoint;
    }
}
