package com.midas.attraction;

import com.midas.attraction.R;
import com.midas.attraction.Controller.OnMoveListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;



public class GameActivity extends Activity {
	Controller controller;
    TextView showMoveEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        showMoveEvent = (TextView) findViewById(R.id.showMoveEvent);
        controller = (Controller) findViewById(R.id.controller);
        controller.setOnMoveListener(new OnMoveListener() {

            @Override
            public void onMaxMoveInDirection(double polarAngle) {
            }

            @Override
            public void onHalfMoveInDirection(double polarAngle) {
            }
        });
    }
}
