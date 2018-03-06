package com.example.itsukakotori.calculator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    protected Presenter presenter;

    RelativeLayout boxContainer, mainContainer, calcOperators;
    LinearLayout calcDisplay;
    CalculatorImageView ivSelected;

    private Button btnAddOperator, btnAddNumber;
    private EditText etOperator, etnumber;

    float counterX;
    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mainContainer = findViewById(R.id.main_layout);
        this.boxContainer = findViewById(R.id.calculator_box_container);
        this.calcDisplay = findViewById(R.id.calculator_display);
        this.btnAddNumber = findViewById(R.id.btn_add_number);
        this.btnAddOperator = findViewById(R.id.btn_add_operator);
        this.etnumber = findViewById(R.id.et_number);
        this.etOperator = findViewById(R.id.et_operator);
        this.calcOperators = findViewById(R.id.calculator_operator);
        this.presenter = new Presenter(this);
        btnAddNumber.setOnClickListener(this);
        btnAddOperator.setOnClickListener(this);
        rng = new Random();
        this.counterX = 0;
        //kalo udh jd si UInya kirim ke gw dam biar gw tw harus nambah apa lagi di presenter ato harus nambah kelas apa lg
    }


    public void setIvSelected(CalculatorImageView calcIv){
        this.ivSelected = calcIv;
    }

    private void addNewView(int type){
        int[] coords = new int[2];
        calcDisplay.getLocationOnScreen(coords);
        int dispTop = coords[1];
        int dispBottom = coords[1] + calcDisplay.getHeight();
        System.out.println(calcDisplay.getHeight());

        int[] coords2 = new int[2];
        calcOperators.getLocationOnScreen(coords2);
        final int opTop = coords2[1] - calcOperators.getHeight();
        int opBottom = coords2[1] + calcOperators.getHeight();
        System.out.println(opTop);

        final CalculatorImageView calcIv = new CalculatorImageView(this, type);
        if(type == 1){ //Operator
            calcIv.setLayoutParams(new ViewGroup.LayoutParams(btnAddOperator.getWidth() / 2, btnAddNumber.getHeight()));
            calcIv.setText(etOperator.getText().toString());
            mainContainer.addView(calcIv);
        }
        else{ //Number
            calcIv.setLayoutParams(new ViewGroup.LayoutParams((int) Math.floor(btnAddOperator.getWidth() * 0.8), btnAddNumber.getHeight()));
            calcIv.setText(etnumber.getText().toString());
            mainContainer.addView(calcIv);
        }
        calcIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    calcIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else{
                    calcIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                calcIv.setX(rng.nextInt(mainContainer.getWidth() - calcIv.getWidth()));
                calcIv.setY(rng.nextInt(opTop - calcDisplay.getHeight() - calcIv.getHeight() + 1) + calcDisplay.getHeight());
            }
        });
        calcIv.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnAddOperator.getId()){
            this.addNewView(1);
        }
        else if(view.getId() == btnAddNumber.getId()){
            this.addNewView(2);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = 0;
        float y = 0;
        CalculatorImageView cView = (CalculatorImageView) view;
            switch (motionEvent.getActionMasked()){
                case MotionEvent.ACTION_DOWN: {
                    cView.bringToFront();
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                    cView.setmLastTouch(x - cView.getX(), y - cView.getY());
                }
                break;
                case MotionEvent.ACTION_MOVE: {
                    cView.setX(motionEvent.getRawX() - cView.getmLastTouchX());
                    cView.setY(motionEvent.getRawY() - cView.getmLastTouchY());
                }
                break;
                case MotionEvent.ACTION_UP:{
                    if(cView.)
                    System.out.println(cView.text + " " + cView.getX() + " " + cView.getY());
                    System.out.println("Width = " + cView.getWidth());
                }
                break;
                default:
                    return false;
            }
            return true;
    }
}