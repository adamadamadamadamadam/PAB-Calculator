package com.example.itsukakotori.calculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.widget.ImageView;

/**
 * Created by Itsuka Kotori on 3/6/2018.
 */

public class Number extends Vessel {


    public Number(String value, float x, float y, MainActivity activity){
        super.value = value;
        super.middlePoint = new PointF(x,y);
        super.type = "number";
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
    }

    /*@Override
    void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity) {
        Rect rect = new Rect();
        //isi method buat bikin rect
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
        rect.set((int) Math.floor(middlePoint.x - activity.mCanvas.getWidth() / 20), (int) Math.floor(middlePoint.y - activity.mCanvas.getHeight() / 20), (int) Math.floor(middlePoint.x + activity.mCanvas.getWidth() / 20), (int) Math.floor(middlePoint.y + activity.mCanvas.getHeight() / 20));
        paint.setColor(activity.getResources().getColor(R.color.number_color));
        canvas.drawRect(rect,paint);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(String.valueOf(this.value), middlePoint.x, middlePoint.y, paint);
        mCanvas.invalidate();
    }*/

    @Override
    String getType() {
        return super.type;
    }
}
