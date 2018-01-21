package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup {
	
	public static final String power1 = "powerup_one.gif";
	public static final String power2 = "powerup_two.gif";
	
	public ImageView powerObject;
	public String image;
	public double xSpeed;
	public double ySpeed;
	public int pType;
	
	public Powerup(int type) {
		choosePic(type);
		pType = type;
		xSpeed = 0;
		ySpeed = 0;
		powerObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(image)));
	}
	
	public void choosePic(int type) {
		if (type == 1) this.image = power1;
		if (type == 2) this.image = power2;
	}
	
	public void setPosition(double x, double y) {
		if (this.pType == 1 || this.pType == 2) {
			this.powerObject.setX(x);
			this.powerObject.setY(y);
		}
	}
	
	public void startMoving() {
		this.ySpeed = 30;
	}

}
