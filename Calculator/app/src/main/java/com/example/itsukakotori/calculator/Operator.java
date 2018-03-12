<<<<<<< HEAD
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

public class Operator extends Vessel {

    public Operator(String value, float x, float y, MainActivity activity){
        super.value = value;
        super.middlePoint = new PointF(x,y);
        super.type = "operator";
        super.a = activity.mCanvas.getWidth() / 10;
        super.b = activity.mCanvas.getHeight() / 10;
    }

    //@Override
    /*void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity) {
        Rect rect = new Rect();
        //isi method buat bikin rect
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
        rect.set((int) Math.floor(middlePoint.x - super.a), (int) Math.floor(middlePoint.y - super.b), (int) Math.floor(middlePoint.x + super.a), (int) Math.floor(middlePoint.y + super.b));
        paint.setColor(activity.getResources().getColor(R.color.operator_color));
        canvas.drawRect(rect,paint);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(this.value, middlePoint.x, middlePoint.y, paint);
        mCanvas.invalidate();
    }*/

    @Override
    String getType() {
        return super.type;
    }
}
=======
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

public class Operator extends Vessel {

    public Operator(String value, float x, float y, MainActivity activity){
        super.value = value;
        super.middlePoint = new PointF(x,y);
        super.type = "operator";
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
    }

    //@Override
    /*void draw(Canvas canvas, ImageView mCanvas, Paint paint, MainActivity activity) {
        Rect rect = new Rect();
        //isi method buat bikin rect
        super.a = activity.mCanvas.getWidth() / 20;
        super.b = activity.mCanvas.getHeight() / 20;
        rect.set((int) Math.floor(middlePoint.x - super.a), (int) Math.floor(middlePoint.y - super.b), (int) Math.floor(middlePoint.x + super.a), (int) Math.floor(middlePoint.y + super.b));
        paint.setColor(activity.getResources().getColor(R.color.operator_color));
        canvas.drawRect(rect,paint);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(this.value, middlePoint.x, middlePoint.y, paint);
        mCanvas.invalidate();
    }*/

    @Override
    String getType() {
        return super.type;
    }
}
>>>>>>> 8aa4119bd339c9e10748876418d6382a5e33b9fb
