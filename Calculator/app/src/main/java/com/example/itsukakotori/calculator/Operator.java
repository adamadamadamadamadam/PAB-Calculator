package com.example.itsukakotori.calculator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.widget.ImageView;

/**
 * Created by Itsuka Kotori on 3/6/2018.
 */

public class Operator extends Vessel {



    public Operator(String value, float x, float y, MainActivity activity){
        super.value = value;
        super.middlePoint = new PointF(x,y);
        super.type = "operator";
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
    }

    /*@Override
    void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity) {
        Rect rect = new Rect();
        //isi method buat bikin rect
        paint.setColor(activity.getResources().getColor(R.color.operator_color));
        canvas.drawRect(rect,paint);
        mCanvas.invalidate();
    }

    @Override
    String getType() {
        return super.type;
    }

    public String getValue(){
        return this.value;
    }
    */
}
