package com.example.itsukakotori.calculator;

import android.graphics.PointF;

/**
 * Created by Asus on 13/03/2018.
 */

public class Empty extends Vessel {

    private boolean empty;

    public Empty(float x, float y, MainActivity activity){
        super.middlePoint = new PointF(x,y);
        super.type = "empty";
        super.a = activity.mCanvas.getWidth() / 9;
        super.b = activity.mCanvas.getHeight() / 9;
        this.empty = true;
    }

    public boolean isEmpty(){
        return empty;
    }
    public void setEmpty(boolean bool){
        this.empty = bool;
    }
}
