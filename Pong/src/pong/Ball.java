package pong;

import java.awt.Color;

public class Ball extends Sprite {
	static final Color BALL_COLOUR = Color.white;
	static final int BALL_WIDTH = 25;
	static final int BALL_HEIGHT = 25;
	
	public Ball (int panelWidth, int panelHeight) {
		setColour(BALL_COLOUR);
		setHeight(BALL_HEIGHT);
		setWidth(BALL_WIDTH);
		setInitialPosition(panelWidth / 2 - getWidth() / 2, panelHeight / 2 - getHeight() / 2);
		resetToInitialPosition();
	}
}

