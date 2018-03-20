package com.example.itsukakotori.calculator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by Itsuka Kotori on 3/6/2018.
 */

public class Number extends Vessel {


    public Number(String value, float x, float y, MainActivity activity){
        super.value = value;
        super.middlePoint = new PointF(x,y);
        super.type = "number";
        super.a = activity.mCanvas.getWidth() / 11;
        super.b = activity.mCanvas.getHeight() / 11;
    }



    /*@Override
    void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity) {
        Rect rect = new Rect();
        //isi method buat bikin rect
        paint.setColor(activity.getResources().getColor(R.color.number_color));
        canvas.drawRect(rect,paint);
        mCanvas.invalidate();
    }

    @Override
    String getType() {
        return super.type;
    }

    public double getValue(){
        return this.value;
    }*/
}
