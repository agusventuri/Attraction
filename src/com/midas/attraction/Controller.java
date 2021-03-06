package com.midas.attraction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Controller extends View {

    OnMoveListener moveListener;
    GameEngine gameEngine = Abstract.getGameEngine();
    
    float x,y;
    double r,t, angle;
    int cx, cy,w,h;
    final int RADIUS = 20;
    
    Paint black = new Paint();
    Paint grey = new Paint();
    Paint white = new Paint();

    private int toDo;

    public Controller(Context context, AttributeSet attrs) {
        super(context, attrs);
        black.setColor(Color.BLACK);
        black.setFlags(Paint.ANTI_ALIAS_FLAG);
        
        grey.setColor(Color.GRAY);
        grey.setFlags(Paint.ANTI_ALIAS_FLAG);
        
        white.setColor(Color.WHITE);
        white.setFlags(Paint.ANTI_ALIAS_FLAG);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        cx = w/2;
        cy = h/2;
        x = cx;y=cy;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMyStuff(canvas);
        switch (toDo) {
        case 1:
            center();
            break;
        default:
            break;
        }

    }

    private void drawMyStuff(final Canvas canvas) {
        canvas.drawCircle(cx, cy, w/2, black);
        canvas.drawCircle(cx, cy, w/2-5, grey);
        canvas.drawCircle(cx, cy, w/2-10, black);
        canvas.drawCircle(x, y, RADIUS+2, white);
        canvas.drawCircle(x, y, RADIUS, grey);
    }

//  n2p : normal to polar coordinates conversion
//  p2n : polar to normal coordinates conversion
//  R : distance to polar center 
//  T : angle
//  rad : radians
    
    double n2pR(double x, double y){
        return distance(x,y,cx,cy);
    }

    double n2pT(double x, double y){
        return Math.atan2((y-cy),(x-cx));
    }
    
    double rad2T(double x, double y){
    	double rad = Math.atan2((y-cy),(x-cx));
    	double angle = Math.toDegrees(rad);
        return angle;
    }

    double p2nX(double r, double t){
        return r*Math.cos(t) + cx;
    }

    double p2nY(double r, double t){
        return r*Math.sin(t) + cy;
    }

    double n2pR(){
        return distance(x,y,cx,cy);
    }

    double n2pT(){
        return Math.atan2((y-cy),(x-cx));
    }

    double p2nX(){
        return r*Math.cos(t) + cx;
    }

    double p2nY(){
        return r*Math.sin(t) + cy;
    }

    double distance(double x1 , double y1, double x2, double y2 ){
        return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	angle = rad2T(event.getX(),event.getY());
    	
        switch(event.getAction()){
        case MotionEvent.ACTION_DOWN :
            updatePosition(event);
            break;

        case MotionEvent.ACTION_MOVE :
        	gameEngine.crosshairAngle = (int)angle + 90;
        	gameEngine.crosshairDirection = angle;
        	gameEngine.crosshairIncrement = n2pR(event.getX(),event.getY()) / 3;
            if(moveListener != null) {

                	gameEngine.crosshairVelocity = gameEngine.ADD_CROSSHAIR_VELOCITY;
            }
            updatePosition(event);
            break;

        case MotionEvent.ACTION_UP :
            toDo = 1;
            center();
            gameEngine.crosshairVelocity = gameEngine.SUBSTRACT_CROSSHAIR_VELOCITY;
            break;
            
        default :
        	break;
        }

        return true;
    }

    private void center(){
        if(r>15){
            r-=15;
        }else{
            toDo=0;
            r=0;
        }
        x = (float) p2nX();
        y= (float) p2nY();
        invalidate();
    }

    void updatePosition(MotionEvent e){
        r= Math.min(w/2-RADIUS, n2pR(e.getX(),e.getY()));
        t = n2pT(e.getX(),e.getY());
        angle = rad2T(e.getX(),e.getY());
        x = (float) p2nX();
        y =(float) p2nY();

        if(moveListener != null)
            if(r == w/2-RADIUS)
                moveListener.onMaxMoveInDirection(angle);
            else if(r >= w/4-RADIUS/2)
                moveListener.onHalfMoveInDirection(angle);

        invalidate();
    }

    public void setOnMoveListener(OnMoveListener listener){
        moveListener =listener;
    }

    public interface OnMoveListener{
        public void onHalfMoveInDirection(double polarAngle);
        public void onMaxMoveInDirection(double polarAngle);
    }
}