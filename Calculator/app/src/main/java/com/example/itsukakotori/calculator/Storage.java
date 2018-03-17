package com.example.itsukakotori.calculator;

import android.graphics.PointF;
import android.util.Log;

/**
 * Created by Itsuka Kotori on 3/17/2018.
 */

public class Storage extends Vessel {

    private boolean empty;
    private int typeInt;
    private Vessel inVessel;

    public Storage(float x, float y, MainActivity activity, int type){
        super.middlePoint = new PointF(x,y);
        super.type = "empty";
        if(type == 0){ //number
            super.a = activity.mCanvas.getWidth() / 9;
            super.b = activity.mCanvas.getHeight() / 9;
        }
        else{
            super.a = activity.mCanvas.getWidth() / 12;
            super.b = activity.mCanvas.getHeight() / 12;
        }
        this.empty = true;
        this.inVessel = null;
        this.typeInt = type;
    }

    public boolean isEmpty(){
        return empty;
    }
    public void setEmpty(boolean bool){
        this.empty = bool;
    }
    public void setInVessel(Vessel vessel){
        this.inVessel = vessel;
    }
    public Vessel getInVessel(){
        return inVessel;
    }
    public int getTypeInt(){
        return this.typeInt;
    }
}
