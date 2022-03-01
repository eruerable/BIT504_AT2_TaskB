package pong;

import java.awt.Color;

public class Paddle extends Sprite {
	static final int PADDLE_WIDTH = 10;
	static final int PADDLE_HEIGHT = 100;
	static final Color PADDLE_COLOUR = Color.decode("#55FFFF");
	static final int DISTANCE_FROM_EDGE = 40;
	
	public Paddle (Player player, int panelWidth, int panelHeight) {
		this.setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColour(PADDLE_COLOUR);
		
		if (player == Player.One) {
			setInitialPosition(DISTANCE_FROM_EDGE, panelHeight / 2 - getHeight() / 2);
		} else {
			setInitialPosition(panelWidth - DISTANCE_FROM_EDGE - getWidth(), panelHeight / 2 - getHeight() / 2);
		}
		resetToInitialPosition();
	}
}
