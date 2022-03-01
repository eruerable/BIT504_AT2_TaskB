package pong;

import javax.swing.JFrame;

public class Pong extends JFrame {
	static final String WINDOW_TITLE = "Pong";
	static final int WINDOW_WIDTH = 800;
	static final int WINDOW_HEIGHT = 600;
	
	public Pong() {
		setTitle(WINDOW_TITLE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		add(new PongPanel());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Pong();
			}
		});
	}

}
