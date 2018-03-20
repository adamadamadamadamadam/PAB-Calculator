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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, Runnable, View.OnLongClickListener {

    protected Presenter presenter;
    protected LinkedList<Vessel> listOfVessel;
    protected Vessel vesselClicked;
    protected LinkedList<Vessel> listOfAssignedVessel;
    protected LinkedList<String> listOfValues;
    protected ArrayList<Storage> touchedBoxes;
    protected Vessel resultVessel;
    protected Storage[] emptyVessel;
    protected LinkedList<String> tempToCalculate;
    protected History history;

    protected GestureDetector gestureDetector;
    protected Paint painter;

    protected RelativeLayout mainLayout;
    protected RelativeLayout boxContainer;
    protected RelativeLayout mainContainer;
    protected RelativeLayout calcOperators;
    protected LinearLayout calcDisplay;
    protected ImageView mCanvas;
    protected Canvas canvas;
    protected Bitmap mBitmap;

    private Button btnAddOperator, btnAddNumber, btnCompute;
    private EditText etnumber;
    private Spinner spinnerOperator;
    private ImageView ivDelete, ivHistory;
    private boolean willGetHistory;

    Animation shakeAnim;

    private boolean canvasInitiated;
    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainContainer = findViewById(R.id.main_layout);
        this.btnAddNumber = findViewById(R.id.btn_add_number);
        this.btnAddOperator = findViewById(R.id.btn_add_operator);
        this.etnumber = findViewById(R.id.et_number);
        this.spinnerOperator = findViewById(R.id.spinner_operator);
        this.calcOperators = findViewById(R.id.calculator_operator);
        this.mCanvas = findViewById(R.id.iv_Canvas);
        this.btnCompute = findViewById(R.id.btn_compute);
        this.ivDelete = findViewById(R.id.iv_delete);
        this.ivHistory = findViewById(R.id.iv_history);
        this.touchedBoxes = new ArrayList<>();
        this.listOfValues = new LinkedList<>();
        this.tempToCalculate = new LinkedList<>();
        this.emptyVessel = new Storage[8];
        this.shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        this.willGetHistory = false;
        CostumeGestureListener cgl = new CostumeGestureListener(this);
        this.gestureDetector = new GestureDetector(this,cgl);

        this.presenter = new Presenter(this);
        this.listOfVessel = new LinkedList<>();
        this.listOfAssignedVessel = new LinkedList<>();

        this.btnAddNumber.setOnClickListener(this);
        this.btnAddOperator.setOnClickListener(this);
        this.btnCompute.setOnClickListener(this);
        this.btnAddNumber.setOnLongClickListener(this);
        this.btnAddOperator.setOnLongClickListener(this);
        this.history = new History();
        this.canvasInitiated = false;

        rng = new Random();

        this.mCanvas.post(this);
        //this.counterX = 0;
        //kalo udh jd si UInya kirim ke gw dam biar gw tw harus nambah apa lagi di presenter ato harus nambah kelas apa lg
    }

    private void populateAssVesselList(){
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                Storage v;
                if(index != 7) {
                    v = new Storage((float) ((mCanvas.getWidth() / 6) + ((mCanvas.getWidth() / 8) * 1.8 * j)), (float) ((mCanvas.getHeight() / 6) + ((mCanvas.getHeight() / 8) * 2 * i)), this, index % 2);
                }
                else{
                    v = new Storage((float) ((mCanvas.getWidth() / 6) + ((mCanvas.getWidth() / 8) * 1.8 * j)), (float) ((mCanvas.getHeight() / 6) + ((mCanvas.getHeight() / 8) * 2 * i)), this, 0);
                }
                //listOfAssignedVessel.add(v);
                emptyVessel[index] = v;
                if(i == 1 && j == 3){
                    resultVessel = v;
                }

                draw(v);
                index ++;
            }
        }
    }

    private void addToArray(){
        //masukin semua data yang ada di box atas ke array (box empty)
        for(int i = 0;i<this.emptyVessel.length;i++){
            //kalo null masukin null, kalo  ada isi masukin isi
            //this.emptyVessel[i]=
        }
    }

    private void temping(){
        for(int i = 0;i<this.emptyVessel.length;i++) {
            if (this.emptyVessel != null) {
                if (emptyVessel[i].getInVessel() != null) {
                    this.tempToCalculate.add(this.emptyVessel[i].getInVessel().getValue());
                }
            }
        }
    }

    private int getNumberHead(){
        int head = -1;
        for(int i = 0; i < emptyVessel.length; i+=2){
            if(!emptyVessel[i].isEmpty()){
                head = i;
            }
        }
        return head;
    }

    private int getHead(int type){
        int head = -1;
        int start = 0;
        if(type == 2){
            start = 1;
        }
        for(int i = start; i < emptyVessel.length - 1; i+= 2){
            if(!emptyVessel[i].isEmpty()){
                head = i;
            }
        }
        return head;
    }

    private void initializeCanvas(){
        this.mBitmap= Bitmap.createBitmap(this.mCanvas.getWidth(),this.mCanvas.getHeight(),Bitmap.Config.ARGB_8888);
        this.mCanvas.setImageBitmap(mBitmap);
        this.canvas = new Canvas(mBitmap);
        this.painter = new Paint();
        this.painter.setTextSize(this.painter.getTextSize() * 5);
        System.out.println("Canvas Created");
        this.mCanvas.setOnTouchListener(this);
        this.mCanvas.invalidate();
        canvasInitiated = true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.d("event", "onDown: acc");
                //1.ambil koordinat yang di pencet
                float touchedX = motionEvent.getX();
                float touchedY = motionEvent.getY();
                //2.check kotak/vessel mana yang di pencet method getField()
                for (int i = 0; i < listOfVessel.size(); i++) {
                    if (listOfVessel.get(i).getField(touchedX, touchedY)) {
                        vesselClicked = listOfVessel.get(i);
                        for(int j = 0; j < emptyVessel.length; j++) {
                            if (vesselClicked.equals(emptyVessel[j].getInVessel())){
                                emptyVessel[j].setInVessel(null);
                                emptyVessel[j].setEmpty(true);
                                Log.d("vessel", "set null");
                                break;
                            }
                        }
                        listOfVessel.remove(i);
                        //listOfVessel.add(vesselClicked);
                        Log.d("Gesture", vesselClicked.getType());
                        break;
                    }
                    vesselClicked = null;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("delete coords", ivDelete.getX() + " " +  ivDelete.getY());
                if(vesselClicked != null) {
                    Log.d("delete coords", vesselClicked.getBottom() + " " + vesselClicked.getRight());
                    int index = -1;
                    PointF minPoint = new PointF();
                    minPoint.set(Float.MAX_VALUE, Float.MAX_VALUE);
                    int temp = 0;
                    if(vesselClicked.getType().equals("operator")){
                        temp ++;
                    }
                    for (int i = 0; i < touchedBoxes.size(); i++) {
                        float x = Math.abs(vesselClicked.getMiddlePoint().x - touchedBoxes.get(i).getMiddlePoint().x);
                        float y = Math.abs(vesselClicked.getMiddlePoint().y - touchedBoxes.get(i).getMiddlePoint().y);
                        if(x < minPoint.x && y < minPoint.y && touchedBoxes.get(i).getType().equals("empty")
                                && !resultVessel.equals(touchedBoxes.get(i)) && touchedBoxes.get(i).getInVessel() == null
                                && touchedBoxes.get(i).getTypeInt() == temp){
                            index = i;
                            minPoint = new PointF(x, y);
                        }
                    }
                    canvas.drawColor(getResources().getColor(R.color.default_background_color));
                    if(index != -1){
                        vesselClicked.setMiddlePoint(touchedBoxes.get(index).getMiddlePoint().x, touchedBoxes.get(index).getMiddlePoint().y);
                        for(int i = 0; i < emptyVessel.length; i++){
                            if(touchedBoxes.get(index).equals(emptyVessel[i])){
                                Log.d("vessel", "set not null");
                                emptyVessel[i].setInVessel(vesselClicked);
                                emptyVessel[i].setEmpty(false);
                                break;
                            }
                        }
                        touchedBoxes.clear();
                    }
                    //Masuk ke history
                    if(willGetHistory) {
                        if (history.keyExist(vesselClicked.getValue())) {
                            LinkedList tempList = history.getList(vesselClicked.getValue());
                            Log.d("vesselhistory", String.valueOf(history.getList(vesselClicked.getValue()).size()));
                            int a = this.mCanvas.getWidth() / 10;
                            for (int i = 0; i < emptyVessel.length; i++) {
                                if (!emptyVessel[i].isEmpty()) {
                                    for (int j = 0; j < listOfVessel.size(); j++) {
                                        //Harusnya kalo kita pgn liat history yg keacak cuma kotak-kotak yg diatas, bukan semua
                                        //Jadi ini masih ngaco
                                        //if (emptyVessel[i].getInVessel().equals(listOfVessel.get(j))) {
                                        listOfVessel.get(j).setMiddlePoint(rng.nextInt(mCanvas.getWidth() - a * 2) + a
                                                , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2);
                                        emptyVessel[i].emptyVessel();
                                        //}
                                    }
                                }
                            }
                            //resultVessel = vesselClicked;
                            for(int i = 0; i < tempList.size(); i++){
                                Vessel newVessel;
                                if(i % 2 == 0){
                                    newVessel = new Number(tempList.get(i).toString(), emptyVessel[i].getMiddlePoint().x,
                                            emptyVessel[i].getMiddlePoint().y, this);
                                }
                                else{
                                    newVessel = new Operator(tempList.get(i).toString(), emptyVessel[i].getMiddlePoint().x,
                                            emptyVessel[i].getMiddlePoint().y, this);
                                }
                                emptyVessel[i].fillVessel(newVessel);
                                listOfVessel.add(newVessel);
                                Log.d("vesselhistory", String.valueOf(tempList.get(i)));
                            }
                            vesselClicked.setMiddlePoint(resultVessel.getMiddlePoint().x, resultVessel.getMiddlePoint().y);
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Tidak ada history untuk ditampilkan", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        willGetHistory = false;
                    }
                    for (int i = 0; i < emptyVessel.length; i++) {
                        draw(emptyVessel[i]);
                    }
                    if(!vesselClicked.getDeleteStatus()){
                        Log.d("event", "not deleted");
                        listOfVessel.add(vesselClicked);
                    }
                    vesselClicked = null;
                    for(int i = 0; i < listOfVessel.size(); i++){
                        draw(listOfVessel.get(i));
                    }
                    mCanvas.invalidate();
                }
                break;
        }
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    private void draw(Vessel box){
        Rect rect = new Rect();
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
        if (box.getType().equals("number")) {
            this.painter.setColor(this.getResources().getColor(R.color.number_color));
        } else if (box.getType().equals("operator")) {
            this.painter.setColor(this.getResources().getColor(R.color.operator_color));
        } else {
            this.painter.setColor(Color.BLACK);
            this.painter.setStyle(Paint.Style.STROKE);
        }
        canvas.drawRect(rect,painter);
        canvas.drawRect(rect, painter);
        painter.setColor(Color.BLACK);
        painter.setTextAlign(Paint.Align.CENTER);
        if(!(box.getValue() == null)){
            canvas.drawText(box.getValue(), box.getMiddlePoint().x, box.getMiddlePoint().y, painter);
        }
        if(box.equals(resultVessel)){
            canvas.drawText("Result", box.getMiddlePoint().x, box.getMiddlePoint().y, painter);
        }
        painter.setStyle(Paint.Style.FILL);
        mCanvas.invalidate();
    }

    private void resetTemping(){
        this.tempToCalculate.clear();
    }

    /**
     * Method biar yg onClick ga terlalu padat
     * @param type
     * @param x
     * @param y
     * @return
     */
    private Vessel createBox(int type, float x, float y){
        Vessel v;
        if(type == 1){ //number
            Vessel numVessel = new Number(this.etnumber.getText().toString(), x, y, this);
            this.listOfVessel.add(numVessel);
            this.draw(numVessel);
            this.reset();
            v = numVessel;
        }
        else{
            Vessel opVessel = new Operator(this.spinnerOperator.getSelectedItem().toString(), x, y, this);
            this.listOfVessel.add(opVessel);
            this.draw(opVessel);
            v = opVessel;
        }
        return v;
    }

    private boolean isEmptyEmptyVessel(){
        for (Storage anEmptyVessel : emptyVessel) {
            if (!anEmptyVessel.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(!canvasInitiated){
            this.initializeCanvas();
        }

        int[] coords = new int[2];
        mCanvas.getLocationOnScreen(coords);

        int a = this.mCanvas.getWidth() / 10;
        int b = this.mCanvas.getHeight() / 10;
        if(view.getId()==this.btnAddNumber.getId()){
            if(etnumber.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Angka belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                this.createBox(1, rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2);
            }
        }
        else if(view.getId()==this.btnAddOperator.getId()){
            if(spinnerOperator.getSelectedItem().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Operator belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                this.createBox(2, rng.nextInt(mCanvas.getWidth() - a * 2) + a
                        , rng.nextInt(2 + mCanvas.getHeight() / 2) + mCanvas.getHeight() / 2);
            }
        }
        else if(view.getId()==this.btnCompute.getId()){
            if(this.isEmptyEmptyVessel()){
                Toast toast = Toast.makeText(getApplicationContext(), "Anda belum memasukan data kedalam kotak", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                this.temping();
                this.presenter.addMath(tempToCalculate);
                this.presenter.countAlternate();
                double x = this.presenter.getResult();
                String result = ""+x;
                this.history.addHistory(this.tempToCalculate,result);
                Number numVessel = new Number(result, this.resultVessel.getMiddlePoint().x
                        , this.resultVessel.getMiddlePoint().y, this);
                listOfVessel.add(numVessel);
                this.draw(numVessel);
                this.resetTemping();
                this.presenter.clearList();
                Log.d("event", "onClick: out");
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mCanvas.invalidate();
    }

    public void reset(){
        this.etnumber.setText("");
    }
    @Override
    public void run() {
        this.initializeCanvas();
        this.populateAssVesselList();

    }

    @Override
    public boolean onLongClick(View view) {
        int head = 0;
        Vessel temp = null;
        int a = this.mCanvas.getWidth() / 10;
        int b = this.mCanvas.getHeight() / 10;
        if(view.getId()==this.btnAddNumber.getId()){
            head = getHead(1) + 1;
            if(etnumber.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Angka belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                while(head % 2 != 0) {
                    head ++;
                }
                if(head < 7) {
                    Log.d("headlength", String.valueOf(head));
                    Storage headStorage = emptyVessel[head];
                    temp = this.createBox(1, headStorage.getMiddlePoint().x, headStorage.getMiddlePoint().y);
                }
            }
        }
        else if(view.getId()==this.btnAddOperator.getId()){
            head = getHead(2) + 1;
            if(spinnerOperator.getSelectedItem().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Operator belum dimasukan", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                while(head % 2 != 1){
                    head ++;
                }
                if(head < 7) {
                    Log.d("headlength", String.valueOf(head));
                    Storage headStorage = emptyVessel[head];
                    temp = this.createBox(2, headStorage.getMiddlePoint().x, headStorage.getMiddlePoint().y);
                }
            }
        }
        if(head < 7) {
            emptyVessel[head].setInVessel(temp);
            emptyVessel[head].setEmpty(false);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Penuh", Toast.LENGTH_SHORT);
            toast.show();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return true;
    }

    private class CostumeGestureListener extends GestureDetector.SimpleOnGestureListener{

        MainActivity activity;

        public CostumeGestureListener(MainActivity activity){
            this.activity = activity;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("event", "onDown: acc");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //gambar ulang si vessel yang di pencet
            if(vesselClicked != null) {
                touchedBoxes.clear();
                canvas.drawColor(getResources().getColor(R.color.default_background_color));
                for(int i = 0; i < emptyVessel.length; i++){
                    draw(emptyVessel[i]);
                }
                vesselClicked.setMiddlePoint(e2.getX(), e2.getY());
                for(int i = 0; i < emptyVessel.length; i++){
                    if((vesselClicked.getTop() < emptyVessel[i].getBottom() || vesselClicked.getBottom() < emptyVessel[i].getTop())
                            && vesselClicked.getLeft() > emptyVessel[i].getLeft()
                            && vesselClicked.getRight() < emptyVessel[i].getRight()){
                        touchedBoxes.add(emptyVessel[i]);
                        Log.d("event", "added box " + i);
                    }
                }
                Log.d("event", "touched box list size = " + touchedBoxes.size());
                for(int i = 0; i < listOfVessel.size(); i++){
                    draw(listOfVessel.get(i));
                }
                if(vesselClicked.getType().equals("number") && vesselClicked.getBottom() > ivHistory.getY() && vesselClicked.getLeft() < (ivHistory.getX() + ivHistory.getWidth())){
                    ivHistory.startAnimation(shakeAnim);
                    //showHistory((Number) vesselClicked);
                    willGetHistory = true;
                }
                else{
                    willGetHistory = false;
                }
                if(vesselClicked.getBottom() > ivDelete.getY() && vesselClicked.getRight() > ivDelete.getX()){
                    vesselClicked.setDeletion(true);
                    ivDelete.startAnimation(shakeAnim);
                }
                else{
                    vesselClicked.setDeletion(false);
                }
                draw(vesselClicked);
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

