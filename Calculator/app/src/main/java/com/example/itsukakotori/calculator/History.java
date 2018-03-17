package com.example.itsukakotori.calculator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itsuka Kotori on 3/17/2018.
 */

public class History {

    protected HashMap history;

    public History(){
        this.history = new HashMap();
    }

    public void addHistory(LinkedList list, double result){
        history.put(result, list);
    }

    public LinkedList getList(double i){
        return (LinkedList)history.get(i);
    }
}
