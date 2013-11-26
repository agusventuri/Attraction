package com.midas.attraction;

import java.util.List;

public class BulletHandler {

	public void update(float screenWidth, float screenHeight, int pendingEvent,
			Crosshair crosshair, List<NormalEnemy> normalEnemies,
			GameStatus gameStatus) {
		Bullet newBullet = null;

		if (pendingEvent == GameEngine.EVENT_FIRE) {
			newBullet = new Bullet(crosshair.x, crosshair.y);
			gameStatus.increaseFiredBullets();

			for (NormalEnemy normalEnemy : normalEnemies) {
				double bulletX = newBullet.getX();
				double bulletY = newBullet.getY();
				double xPlus = normalEnemy.x + normalEnemy.bitmap.getWidth() / 2;
				double xMinus = normalEnemy.x - normalEnemy.bitmap.getWidth() / 2;
				double yPlus = normalEnemy.y + normalEnemy.bitmap.getHeight() / 2;
				double yMinus = normalEnemy.y - normalEnemy.bitmap.getHeight() / 2;
				if ((bulletX <= xPlus && bulletX >= xMinus)
						&& (bulletY <= yPlus && bulletY >= yMinus)) {
					normalEnemy.collided = true;
				}
			}
		}

	}

}