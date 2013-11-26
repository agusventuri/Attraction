package com.midas.attraction;

public abstract class Abstract {
	private static GameEngine gameEngine;

	public static GameEngine getGameEngine() {
		return gameEngine;
	}

	public static void setGameEngine(GameEngine gameEngine) {
		Abstract.gameEngine = gameEngine;
	}
}
