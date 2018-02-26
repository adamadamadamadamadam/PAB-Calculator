package com.example.itsukakotori.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    protected Presenter presenter;

    RelativeLayout boxContainer;
    LinearLayout calcDisplay;

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnComma, btnPlus, btnMinus, btnMult, btnDivide, btnClear, btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.boxContainer = findViewById(R.id.calculator_box_container);
        this.calcDisplay = findViewById(R.id.calculator_display);

        this.btn0 = findViewById(R.id.btn_num_zero);
        this.btn1 = findViewById(R.id.btn_num_one);
        this.btn2 = findViewById(R.id.btn_num_two);
        this.btn3 = findViewById(R.id.btn_num_three);
        this.btn4 = findViewById(R.id.btn_num_four);
        this.btn5 = findViewById(R.id.btn_num_five);
        this.btn6 = findViewById(R.id.btn_num_six);
        this.btn7 = findViewById(R.id.btn_num_seven);
        this.btn8 = findViewById(R.id.btn_num_eight);
        this.btn9 = findViewById(R.id.btn_num_nine);

        this.btnComma = findViewById(R.id.btn_comma);
        this.btnPlus = findViewById(R.id.btn_operator_plus);
        this.btnMinus = findViewById(R.id.btn_operator_minus);
        this.btnMult = findViewById(R.id.btn_operator_multiply);
        this.btnDivide = findViewById(R.id.btn_operator_divide);
        this.btnClear = findViewById(R.id.btn_clear);
        this.btnResult = findViewById(R.id.btn_operator_result);

        this.presenter = new Presenter(this);
        //kalo udh jd si UInya kirim ke gw dam biar gw tw harus nambah apa lagi di presenter ato harus nambah kelas apa lg
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
