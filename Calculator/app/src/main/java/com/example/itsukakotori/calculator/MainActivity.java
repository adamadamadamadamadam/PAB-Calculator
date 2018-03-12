package com.example.itsukakotori.calculator;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itsukakotori.calculator.Presenter;
import com.example.itsukakotori.calculator.R;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    protected Presenter presenter;
    protected LinkedList<Vessel> listOfVessel;
    protected Vessel vesselClicked;

    protected GestureDetector gestureDetector;
    protected Paint painter;

    protected RelativeLayout boxContainer;
    protected RelativeLayout mainContainer;
    protected RelativeLayout calcOperators;
    protected LinearLayout calcDisplay;
    protected ImageView mCanvas;
    protected Canvas canvas;
    protected Bitmap mBitmap;
    //CalculatorImageView ivSelected;

    private Button btnAddOperator, btnAddNumber;
    private EditText etOperator, etnumber;

    private boolean canvasInitiated;
    float counterX;
    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainContainer = findViewById(R.id.main_layout);
        this.btnAddNumber = findViewById(R.id.btn_add_number);
        this.btnAddOperator = findViewById(R.id.btn_add_operator);
        this.etnumber = findViewById(R.id.et_number);
        this.etOperator = findViewById(R.id.et_operator);
        this.calcOperators = findViewById(R.id.calculator_operator);
        this.mCanvas = findViewById(R.id.iv_Canvas);


        CustomGestureListener cgl = new CustomGestureListener(this);
        this.gestureDetector = new GestureDetector(this,cgl);

        this.presenter = new Presenter(this);
        this.listOfVessel = new LinkedList<>();

        this.btnAddNumber.setOnClickListener(this);
        this.btnAddOperator.setOnClickListener(this);
        this.canvasInitiated = false;

        rng = new Random();
        //this.counterX = 0;
        //kalo udh jd si UInya kirim ke gw dam biar gw tw harus nambah apa lagi di presenter ato harus nambah kelas apa lg
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public void onClick(View view) {
        if(!canvasInitiated){
            this.mBitmap= Bitmap.createBitmap(this.mCanvas.getWidth(),this.mCanvas.getHeight(),Bitmap.Config.ARGB_8888);
            this.mCanvas.setImageBitmap(mBitmap);
            this.canvas = new Canvas(mBitmap);

            this.painter = new Paint();

            this.mCanvas.invalidate();
            canvasInitiated = true;
        }

        int[] coords = new int[2];
        mCanvas.getLocationOnScreen(coords);
        int left = coords[0];
        int right = coords[0] + mCanvas.getWidth();
        int top = coords[1];
        int bottom = coords[1] + mCanvas.getHeight();

        int a = this.mCanvas.getWidth() / 20;
        int b = this.mCanvas.getHeight() / 20;
        //ini yang nextFloatnya nanti di benerin
        if(view.getId()==this.btnAddNumber.getId()){
            if(etnumber.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Mana angkanya goblok????", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Vessel tempVessel = new Number(Double.parseDouble(this.etnumber.getText().toString()), rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2);
                this.listOfVessel.add(tempVessel);
                tempVessel.draw(canvas, mCanvas, painter, this);
            }
        }
        else if(view.getId()==this.btnAddOperator.getId()){
            if(etOperator.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Operatornya????", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Vessel tempVessel = new Operator(this.etOperator.getText().toString(), rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2);
                this.listOfVessel.add(tempVessel);
                tempVessel.draw(canvas, mCanvas, painter, this);
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.mCanvas.setOnTouchListener(this);
        mCanvas.invalidate();
    }

    private class CustomGestureListener extends GestureDetector.SimpleOnGestureListener{

        MainActivity activity;

        public CustomGestureListener(MainActivity activity){
            this.activity = activity;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("event", "onDown: acc");
            //1.ambil koordinat yang di pencet
            float touchedX = e.getX();
            float touchedY = e.getY();
            //2.check kotak/vessel mana yang di pencet method getField()
            for(int i = 0; i < listOfVessel.size(); i++){
                if(listOfVessel.get(i).getField(touchedX, touchedY)){
                    vesselClicked = listOfVessel.get(i);
                    listOfVessel.remove(i);
                    Log.d("Gesture", vesselClicked.getType());
                    break;
                }
                vesselClicked = null;
            }
            // kalo gk true gk bakal terjadi apa2
            //kalo true ambil semua infonya masukin ke vesselClicked
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("event", "onScroll: acc");
            //gambar ulang si vessel yang di pencet
            if(vesselClicked != null) {
                canvas.drawColor(Color.WHITE);
                vesselClicked.setMiddlePoint(e2.getX(), e2.getY());
                listOfVessel.add(vesselClicked);
                for(int i = 0; i < listOfVessel.size(); i++){
                    listOfVessel.get(i).draw(canvas, mCanvas, painter, activity);
                }
                mCanvas.invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            //Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            //toast.show();
        }
    }
}