package com.example.itsukakotori.calculator;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itsuka Kotori on 2/24/2018.
 */

public class Presenter {

    protected MainActivity mainActivity;
    protected List<String> mathList;
    private List<Double> numbers;
    protected List<String> operator;
    protected MathOperation mo;
    protected String mode;

    public Presenter(MainActivity ma){
        this.mainActivity = ma;
        this.mathList = new LinkedList<>();
        this.operator = new LinkedList<>();
        this.numbers = new LinkedList<>();
        this.mo = new MathOperation();
        this.mode = "native";
    }

    public void clearList(){
        this.numbers.clear();
        this.operator.clear();
    }

    public void addMath(String x){
        mathList.add(x);
    }

    public void addMath(LinkedList list){
        this.mathList = list;
    }

    protected void spreadNumberOperator(){
        for(int i = 0;i<mathList.size();i++){
            if(mathList.get(i) != null) {
                if (mathList.get(i).equals("+") || mathList.get(i).equals("-") || mathList.get(i).equals("*") || mathList.get(i).equals("/")) {
                    operator.add(mathList.get(i).toString());
                } else {
                    numbers.add(Double.parseDouble(mathList.get(i)));
                }
            }
        }
    }

    public void countAlternate(){
        if(this.mode.equals("native")){
            this.spreadNumberOperator();
            this.count();
        }
        else{
            this.stackList();
            this.spreadNumberOperator();
            this.count();
        }
    }

    public void count(){
        while(!operator.isEmpty()){
            String x = operator.remove(0);
            if(x.equals("+")){
                double a = numbers.get(0);
                double b = numbers.get(1);
                Log.d("mathlist", a + " " + b);
                double y = mo.add(a,b);
                numbers.remove(1);
                numbers.remove(0);
                numbers.add(0,y);
            }
            else if(x.equals("-")){
                double a = numbers.get(0);
                double b = numbers.get(1);
                double y = mo.substract(a,b);
                numbers.remove(1);
                numbers.remove(0);
                numbers.add(0,y);
            }
            else if(x.equals("*")){
                double a = numbers.get(0);
                double b = numbers.get(1);
                double y = mo.multiply(a,b);
                numbers.remove(1);
                numbers.remove(0);
                numbers.add(0,y);
            }
            else if(x.equals("/")){
                double a = numbers.get(0);
                double b = numbers.get(1);
                double y = mo.divide(a,b);
                numbers.remove(1);
                numbers.remove(0);
                numbers.add(0,y);
            }
            else{
                Log.d("debug", "counting: error");
            }
        }
    }

    public void stackList(){
        //method stack buat stack mathList, biar kalo nnt di count() sama di spreadNumber() udh terurut countingnya
        //maksudnya biar kalo ada 5+6/3*4-2 jd 6/3 dlu trus * 4 ditambah 5 baru dikurang 2
        ///*+-
        //63452

    }

    public void toggleMode(){
        if(this.mode.equals("native")){
            this.mode = "presendence";
        }
        else{
            this.mode = "native";
        }
    }

    public double getResult(){
        if(numbers.size()>1||numbers.size()<1){
            Log.d("debug", "getResult: need to count the result first");
            return 0.0;
        }
        else{
            return numbers.get(0);
        }
    }
}
