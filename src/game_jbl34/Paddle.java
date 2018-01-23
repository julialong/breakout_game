package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {

	private Boolean sticky;
	private Boolean wasSticky;
	private ImageView paddleObject;

	// possible images
	private static final String paddle1 = "paddle.gif";
	private static final String paddle2 = "sticky_paddle.gif";
	private static final String paddle3 = "long_paddle.gif";

	public Paddle() {
		wasSticky = false;
		sticky = false;
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(paddle1));
		paddleObject = new ImageView(paddleImage);
	}

	public ImageView getPaddleObject() {
		return this.paddleObject;
	}

	public double getX() {
		return this.paddleObject.getX();
	}

	public void setX(double position) {
		this.paddleObject.setX(position);
	}

	public double getY() {
		return this.paddleObject.getY();
	}

	public void setY(double position) {
		this.paddleObject.setY(position);
	}

	public void makeSticky() {
		this.paddleObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(paddle2)));
		this.sticky = true;
	}

	public void notSticky() {
		this.paddleObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(paddle1)));
		this.sticky = false;
		this.wasSticky = true;
	}

	public Boolean isSticky() {
		return this.sticky;
	}

	public Boolean wasSticky() {
		return this.wasSticky;
	}

	public void makeLong() {
		this.paddleObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(paddle3)));
	}
}
