package com.example.itsukakotori.calculator;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Itsuka Kotori on 3/17/2018.
 */

public class History {

    protected HashMap<String, LinkedList<String>> history;

    public History(){
        this.history = new HashMap<>();
    }

    public void addHistory(LinkedList<String> list, String result){
        LinkedList<String> temp = new LinkedList<>();
        temp.addAll(list);
        history.put(result, temp);
    }

    public LinkedList<String> getList(String i){
        //Log.d("vesselhistory", history.get(i))
        return history.get(i);
    }

    public boolean keyExist(String i){
        return history.containsKey(i);
    }
}
