package com.midas.attraction;

import java.util.List;

import com.midas.attraction.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Crosshair extends GfxObject {
	private Paint paint;
	private double increment;

	public Crosshair(Resources resources, float x, float y, double velocity,
			double direction, int angle) {
		paint = new Paint();
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.crosshair);
		this.x = x;
		this.y = y;
		setVelocity(velocity);
		setDirection(direction);
		this.angle = angle;
	}

	public double getIncrement() {
		return increment;
	}

	public void setIncrement(double increment) {
		this.increment = increment;
	}

	public void update(float screenWidth, float screenHeight,
			List<NormalEnemy> normalEnemies, int event, GameStatus gameStatus,
			int angle, double direction, int thrust) {
		float dX;
		float dY;
		float distance;
		this.angle = angle;

		if (thrust == 0) {
			addVelocity(increment, angle);
		} else if (thrust == 1) {
			substractVelocity(angle);
		}

		move(screenWidth, screenHeight);
		setCollided(false);
		for (NormalEnemy normalEnemy : normalEnemies) {
			dX = Math.abs(x - normalEnemy.x);
			dY = Math.abs(y - normalEnemy.y);
			distance = (float) Math.sqrt(dX * dX + dY * dY);
			if (distance <= (bitmap.getWidth() / 2 + normalEnemy.bitmap
					.getWidth() / 2)) {
				collided = true;
			}
		}
	}

	public void draw(Canvas canvas) {
		draw(canvas, x, y, paint);
	}

}