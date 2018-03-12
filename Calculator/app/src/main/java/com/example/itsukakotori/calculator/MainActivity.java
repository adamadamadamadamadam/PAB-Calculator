package com.example.itsukakotori.calculator;

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

        //new Thread(this).start();
        //this.createBitmap();
        //this.counterX = 0;
        //kalo udh jd si UInya kirim ke gw dam biar gw tw harus nambah apa lagi di presenter ato harus nambah kelas apa lg
    }

    private void initializeCanvas(){
        this.mBitmap= Bitmap.createBitmap(this.mCanvas.getWidth(),this.mCanvas.getHeight(),Bitmap.Config.ARGB_8888);
        this.mCanvas.setImageBitmap(mBitmap);
        this.canvas = new Canvas(mBitmap);

        this.painter = new Paint();
        this.painter.setTextSize(this.painter.getTextSize() * 5);

        this.mCanvas.invalidate();
        canvasInitiated = true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    public void onClick(View view) {
        if(!canvasInitiated){
            this.initializeCanvas();
        }

        int[] coords = new int[2];
        mCanvas.getLocationOnScreen(coords);
        int left = coords[0];
        int right = coords[0] + mCanvas.getWidth();
        int top = coords[1];
        int bottom = coords[1] + mCanvas.getHeight();

        int a = this.mCanvas.getWidth() / 10;
        int b = this.mCanvas.getHeight() / 10;
        //ini yang nextFloatnya nanti di benerin
        if(view.getId()==this.btnAddNumber.getId()){
            if(etnumber.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Angka belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Vessel numVessel = new Number(this.etnumber.getText().toString(), rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2, this);
                this.listOfVessel.add(numVessel);
                //tempVessel.draw(canvas, mCanvas, painter, this);
                this.draw(numVessel);
            }
        }
        else if(view.getId()==this.btnAddOperator.getId()){
            if(etOperator.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Operator belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Vessel opVessel = new Operator(this.etOperator.getText().toString(), rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2, this);
                this.listOfVessel.add(opVessel);
                //tempVessel.draw(canvas, mCanvas, painter, this);
                this.draw(opVessel);
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.mCanvas.setOnTouchListener(this);
        mCanvas.invalidate();
    }

    private void draw(Vessel box){
        Rect rect = new Rect();
        //isi method buat bikin rect
        //float a = this.mCanvas.getWidth() / 20;
        //float b = this.mCanvas.getHeight() / 20;
        if(box.getTop() < 0){
            box.setMiddlePoint(box.getMiddlePoint().x, 0 + box.getB());
        }
        if(box.getBottom() > mCanvas.getHeight()){
            box.setMiddlePoint(box.getMiddlePoint().x, mCanvas.getHeight() - box.getB());
        }
        if(box.getLeft() < 0){
            box.setMiddlePoint(0 + box.getA(), box.getMiddlePoint().y);
        }
        if(box.getRight() > mCanvas.getWidth()){
            box.setMiddlePoint(mCanvas.getWidth() - box.getA(), box.getMiddlePoint().y);
        }
        rect.set((int) Math.floor(box.getMiddlePoint().x - box.a), (int) Math.floor(box.getMiddlePoint().y - box.b),
                (int) Math.floor(box.getMiddlePoint().x + box.a), (int) Math.floor(box.getMiddlePoint().y + box.b));
        if(box.getType().equals("number")){
            this.painter.setColor(this.getResources().getColor(R.color.number_color));
        }
        else{
            this.painter.setColor(this.getResources().getColor(R.color.operator_color));
        }
        canvas.drawRect(rect,painter);
        canvas.drawRect(rect, painter);
        painter.setColor(Color.BLACK);
        float textLength = painter.measureText(box.getValue());
        canvas.drawText(box.getValue(), box.getMiddlePoint().x - (textLength / 2), box.getMiddlePoint().y, painter);
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
                canvas.drawColor(getResources().getColor(R.color.default_background_color));
                //listOfVessel.add(vesselClicked);
                vesselClicked.setMiddlePoint(e2.getX(), e2.getY());
                for(int i = 0; i < listOfVessel.size(); i++){
                    draw(listOfVessel.get(i));
                }
                mCanvas.invalidate();
            }
            Log.d("event", "list size = " + listOfVessel.size());
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            //Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            //toast.show();
        }
    }
}