package com.midas.attraction;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameStatus {
	private Paint textPaint;
	private int level;
	private int firedBullets;
	private int firedBulletsLastLevel;
	private long levelStartTime;
	private long passedLevelTime;
	private long passedLevelTimeLastLevel;
	
	private Paint backgroundLifePaint;
	private Paint lifePaint;
	private int DEAD = 0;
	private int QUARTER = 1;
	private int HALF = 2;
	private int THREE_QUARTERS = 3;
	private int FULL = 4;
	private int remainingLife = 4;

	private float smallTextSize = 25;
	private float mediumTextSize = 30;
	private float largeTextSize = 50;

	private static final long GRACE_PERIOD = 5000000000L;

	public GameStatus() {
		textPaint = new Paint();
		textPaint.setColor(Color.LTGRAY);
		
		lifePaint = new Paint();
		lifePaint.setColor(Color.GREEN);
		lifePaint.setAlpha(100);
		
		backgroundLifePaint = new Paint();
		backgroundLifePaint.setColor(Color.GRAY);
		backgroundLifePaint.setAlpha(100);
	}

	public void updateSurfaceDimensions(int width, int height) {
		// 25, 30 and 50 are perfect sizes on a 320 points wide screen
		smallTextSize = width / (320 / 25);
		mediumTextSize = width / (320 / 30);
		largeTextSize = width / (320 / 50);
	}

	public int getRemainingLife() {
		return remainingLife;
	}

	public void setRemainingLife(int remainingLife) {
		this.remainingLife = remainingLife;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;

		firedBulletsLastLevel = firedBullets;
		passedLevelTimeLastLevel = passedLevelTime;

		firedBullets = 0;
		levelStartTime = System.nanoTime() + GRACE_PERIOD;
	}

	public long getPassedLevelTime() {
		return passedLevelTime;
	}

	public void increaseFiredBullets() {
		firedBullets++;
	}

	public void update() {
		passedLevelTime = (System.nanoTime() - levelStartTime) / 1000000000L;
	}
	
	public void updateLifeBar () {
		if (remainingLife > 0) {
			this.remainingLife = this.remainingLife - 1;
		}
	}

	public void draw(Canvas canvas, float screenHeight, float screenWidth) {
		textPaint.setTextSize(smallTextSize);
		if (passedLevelTime > 0) {
			textPaint.setTextAlign(Paint.Align.LEFT);
			canvas.drawText("Level: " + level, 1, smallTextSize, textPaint);
			textPaint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawText("Time: " + passedLevelTime + "s", screenWidth - 1,
					50, textPaint);
			canvas.drawText("Bullets: " + firedBullets, screenWidth - 1,
					screenHeight - 2, textPaint);
			float top = (screenHeight / 18) * 1.5f;
			float bottom = (screenHeight / 18) * 2.5f;
			float left = screenWidth / 8;
			float right = screenWidth;
			canvas.drawRect(left, top, right, bottom, backgroundLifePaint);
			if (remainingLife == FULL) {
				canvas.drawRect(left + 5, top + 5, right - 5, bottom - 5, lifePaint);
			}
			if (remainingLife == THREE_QUARTERS) {
				canvas.drawRect(left + 5, top + 5, ((right / 4) * 3) - 5, bottom - 5, lifePaint);
			}
			if (remainingLife == HALF) {
				canvas.drawRect(left + 5, top + 5, ((right / 4) * 2) - 5, bottom - 5, lifePaint);
			}
			if (remainingLife == QUARTER) {
				canvas.drawRect(left + 5, top + 5, (right - 5 / 4) - 5, bottom - 5, lifePaint);
			}
			if (remainingLife == DEAD) {
//				drawGameOverScreen(canvas, screenHeight, screenWidth);
			}
		} else {
			textPaint.setTextAlign(Paint.Align.CENTER);
			if (level > 1) {
				canvas.drawText("Level " + (level - 1) + " completed! ",
						screenWidth / 2, mediumTextSize, textPaint);
				canvas.drawText(" Time: " + passedLevelTimeLastLevel + "s",
						screenWidth / 2, mediumTextSize * 2, textPaint);
				canvas.drawText("Bullets: " + firedBulletsLastLevel,
						screenWidth / 2, mediumTextSize * 3, textPaint);
			}
			textPaint.setTextSize(mediumTextSize);
			canvas.drawText("Level " + level + " starts in "
					+ (-passedLevelTime) + "s", screenWidth / 2,
					(float) (screenHeight * 0.75), textPaint);
		}
	}

	public void drawGameOverScreen(Canvas canvas, float screenHeight,
			float screenWidth) {
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(largeTextSize);
		canvas.drawText("GAME OVER", screenWidth / 2,
				(float) (screenHeight * 0.50), textPaint);
		textPaint.setTextSize(smallTextSize);
		canvas.drawText("You reached level " + level, screenWidth / 2,
				(float) (screenHeight * 0.60), textPaint);
		canvas.drawText("Press 'Back' for Main Menu", screenWidth / 2,
				(float) (screenHeight * 0.85), textPaint);
	}
}