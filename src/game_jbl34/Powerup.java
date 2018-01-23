package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup {

	private static final String power1 = "powerup_one.gif";
	private static final String power2 = "powerup_two.gif";

	private ImageView powerObject;
	private String image;
	private double xSpeed;
	private double ySpeed;
	private int pType;

	public Powerup(int type) {
		choosePic(type);
		pType = type;
		xSpeed = 0;
		ySpeed = 0;
		powerObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(image)));
	}

	public ImageView getPowerObject() {
		return this.powerObject;
	}

	public int getType() {
		return this.pType;
	}

	public void setX(double position) {
		this.powerObject.setX(position);
	}

	public double getX() {
		return this.powerObject.getX();
	}

	public void setY(double position) {
		this.powerObject.setY(position);
	}

	public double getY() {
		return this.powerObject.getY();
	}

	public void choosePic(int type) {
		if (type == 1)
			this.image = power1;
		if (type == 2)
			this.image = power2;
	}

	public void setPosition(double x, double y) {
		if (this.pType == 1 || this.pType == 2) {
			this.powerObject.setX(x);
			this.powerObject.setY(y);
		}
	}

	public double getXSpeed() {
		return this.xSpeed;
	}

	public double getYSpeed() {
		return this.ySpeed;
	}

	public void startMoving() {
		this.ySpeed = 30;
	}

}
