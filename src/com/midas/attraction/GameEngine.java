package com.midas.attraction;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class GameEngine {

	Context context;
	Resources resources;

	public float screenWidth;
	public float screenHeight;

	private Paint blackPaint;
	private GameStatus gameStatus;

	private NormalEnemyHandler normalEnemyHandler;
	private BulletHandler bulletHandler;
	private Crosshair crosshair;
	public int crosshairAngle;
	public double crosshairDirection;
	public double crosshairIncrement;
	public final int ADD_CROSSHAIR_VELOCITY = 0;
	public final int SUBSTRACT_CROSSHAIR_VELOCITY = 1;
	public int crosshairVelocity = 2;

	private final int WAITING_FOR_SURFACE = 0;
	private final int RUNNING = 1;
	private final int GAMEOVER = 3;
	private int mode = WAITING_FOR_SURFACE;

	public static final int EVENT_NONE = 0;
	public static final int EVENT_LEFT = 1;
	public static final int EVENT_RIGHT = 2;
	public static final int EVENT_THRUST = 3;
	public static final int EVENT_FIRE = 4;
	public int pendingEvent = EVENT_NONE;

	public void Init(Context context) {
		this.context = context;
		resources = context.getResources();

		blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStyle(Style.FILL);

		gameStatus = new GameStatus();
	}

	private void setLevel(int level) {
		gameStatus.setLevel(level);
		normalEnemyHandler = new NormalEnemyHandler(resources, level, this);
		bulletHandler = new BulletHandler();
		crosshair = new Crosshair(resources, screenWidth / 2,
				screenHeight - 30, 0.1, 0, 0);
	}

	public void onDestroy() {
		try {
		} catch (Exception e) {
		}
	}

	public GameEngine getGameEngine() {
		return this;
	}

	public float getScreenWidth() {
		return screenWidth;
	}

	public float getScreenHeight() {
		return screenHeight;
	}

	public void setSurfaceDimensions(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		if (mode == WAITING_FOR_SURFACE) {
			setLevel(1);
		}
		mode = RUNNING;
		gameStatus.updateSurfaceDimensions(width, height);
	}

	public void update() {
		switch (mode) {
		case (WAITING_FOR_SURFACE):
			break;// There is no screen to do something

		case (RUNNING):
			gameStatus.update();
			if (gameStatus.getPassedLevelTime() > 0) {
				normalEnemyHandler.update(screenWidth, screenHeight);
				crosshair.update(screenWidth, screenHeight,
						normalEnemyHandler.normalEnemies, pendingEvent,
						gameStatus, crosshairAngle, crosshairDirection,
						crosshairVelocity);
				crosshair.setIncrement(crosshairIncrement);
				bulletHandler.update(screenWidth, screenHeight, pendingEvent,
								crosshair, normalEnemyHandler.normalEnemies,
								gameStatus);
				pendingEvent = EVENT_NONE;
				if (normalEnemyHandler.normalEnemies.isEmpty()) {
					setLevel(gameStatus.getLevel() + 1);
				}
				if (gameStatus.getRemainingLife() == 0) {
					mode = GAMEOVER;
				}
				break;
			}
		case (GAMEOVER):
			// Nothing here yet.
			break;
		}

	}
	
	public void hurt () {
		gameStatus.updateLifeBar();
	}

	public void draw(Canvas canvas) {

		switch (mode) {
		case (WAITING_FOR_SURFACE): {
			break;// There is no screen to do something
		}
		case (RUNNING): {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					blackPaint);
			normalEnemyHandler.draw(canvas);
			crosshair.draw(canvas);
			gameStatus.draw(canvas, screenHeight, screenWidth);

			break;
		}
		case GAMEOVER: {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					blackPaint);
			normalEnemyHandler.draw(canvas);
			gameStatus.draw(canvas, screenHeight, screenWidth);
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					blackPaint);
			gameStatus.drawGameOverScreen(canvas, screenHeight, screenWidth);
			break;
		}
		}
	}
}