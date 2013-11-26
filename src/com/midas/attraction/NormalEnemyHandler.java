package com.midas.attraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.midas.attraction.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;

public class NormalEnemyHandler {
	Bitmap baseBitmap;
	Bitmap bitmap;
	double baseSize;
	boolean hurt = false;
	
	Options options;

	public List<NormalEnemy> normalEnemies;
	private Random random;
	private Paint paint;
	private int paintAlpha = 30;
	GameEngine gameEngine;

	public NormalEnemyHandler(Resources resources, int level,
			GameEngine gameEngine) {
		random = new Random();

		paint = new Paint();
		paint.setAlpha(paintAlpha);

		this.gameEngine = gameEngine;

		baseBitmap = BitmapFactory.decodeResource(resources,
				R.drawable.normal_enemy);
		
		bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,
				R.drawable.normal_enemy), baseBitmap.getWidth() / 3, baseBitmap.getHeight() / 3, true);

		normalEnemies = new ArrayList<NormalEnemy>();
		float x;
		float y;

		for (int i = 0; i < level; i++) {
			x = gameEngine.getScreenWidth() * random.nextFloat();
			y = gameEngine.getScreenHeight() * random.nextFloat();
			normalEnemies.add(new NormalEnemy(bitmap, bitmap.getWidth(), x, y, paintAlpha));
			baseSize = bitmap.getWidth();
		}
	}

	public boolean isHurt() {
		return hurt;
	}

	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}

	public void update(float screenWidth, float screenHeight) {
		if (paintAlpha < 255) {
			paintAlpha += 1;
		}
		paint.setAlpha(paintAlpha);
		Iterator<NormalEnemy> normalEnemiesIterator = normalEnemies.iterator();
		NormalEnemy normalEnemy;
		while (normalEnemiesIterator.hasNext()) {
			normalEnemy = normalEnemiesIterator.next();
			
			normalEnemy.setPaintAlpha(paintAlpha);
			normalEnemy.move(screenWidth, screenHeight);
			if (normalEnemy.collided) {
				normalEnemiesIterator.remove();
				break;
			}
			if (normalEnemy.size < baseSize * 3) {
				normalEnemy.size = normalEnemy.size + 1;
			}
			if (normalEnemy.size >= baseSize*3 && normalEnemy.isHurt() == false) {
				gameEngine.hurt();
				normalEnemy.setHurt(true);
			}
		}
		((ArrayList<NormalEnemy>) normalEnemies).trimToSize();
	}
	
	public void draw(Canvas canvas) {
		for (NormalEnemy normalEnemy : normalEnemies) {
			normalEnemy.bitmap = Bitmap.createScaledBitmap(bitmap, normalEnemy.size, normalEnemy.size, true);
			normalEnemy.draw(canvas, normalEnemy.x, normalEnemy.y, paint, normalEnemy.bitmap);
		}
	}

}