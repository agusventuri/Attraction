package com.midas.attraction;

import android.graphics.Bitmap;

public class NormalEnemy extends GfxEnemy {

	int size;
	int paintAlpha;
	boolean hurt;

	public NormalEnemy(Bitmap bitmap, int size, float x, float y, int paintAlpha) {
		this.bitmap = bitmap;
		this.size = this.bitmap.getWidth();
		this.paintAlpha = paintAlpha;
		this.x = x;
		this.y = y;
		hurt = false;
	}

	public boolean isHurt() {
		return hurt;
	}

	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPaintAlpha() {
		return paintAlpha;
	}

	public void setPaintAlpha(int paintAlpha) {
		this.paintAlpha = paintAlpha;
	}

}