package com.midas.attraction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GfxEnemy {

	protected Bitmap bitmap;

	protected float x;
	protected float y;

	private double dX = 0;
	private double dY = 0;

	private boolean looped = false;

	protected boolean collided = false;

	public boolean getLooped() {
		return looped;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public void move(float screenWidth, float screenHeight) {
		float minX = 0 - bitmap.getWidth() / 2;
		float maxX = screenWidth + bitmap.getWidth() / 2;
		float minY = 0 - bitmap.getHeight() / 2;
		float maxY = screenHeight + bitmap.getHeight() / 2;

		looped = false;
		x += dX;
		y += dY;
		if (x > maxX) {
			x = minX;
			looped = true;
		}
		if (x < minX) {
			x = maxX;
			looped = true;
		}
		if (y > maxY) {
			y = minY;
			looped = true;
		}
		if (y < minY) {
			y = maxY;
			looped = true;
		}
	}

	public void draw(Canvas canvas, float x, float y, Paint paint, Bitmap scaledBitmap) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, paint);
		canvas.restore();
	}

}