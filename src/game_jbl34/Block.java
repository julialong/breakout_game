package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {

	private ImageView blockObject;
	private Boolean blockOn;
	private int blockType;
	private int lives;
	private int points;
	private Boolean multiply;
	private int powerup;
	private double time;

	private static final String BASIC_BLOCK = "basic_brick.gif";
	private static final String TWO_BLOCK = "block2.gif";
	private static final String POINT_BLOCK = "block3.gif";
	private static final String TIME_BLOCK = "block4.gif";
	private static final String PERMANENT_BLOCK = "block5.gif";

	/**
	 * Given the type of block, create a new block with the appropriate features
	 * 
	 * @param type
	 */
	public Block(int type) {
		setBlockObject(type);
		blockOn = true;
		multiply = false;
		blockType = type;
		lives = setLives(type);
		points = setPoints(type);
		time = 0.0;
	}

	private void setBlockObject(int type) {
		Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(chooseImage(type)));
		blockObject = new ImageView(blockImage);
	}

	public ImageView getBlockObject() {
		return this.blockObject;
	}

	public int getBlockType() {
		return this.blockType;
	}

	private String chooseImage(int type) {
		if (type == 2)
			return TWO_BLOCK;
		if (type == 3)
			return POINT_BLOCK;
		if (type == 4)
			return TIME_BLOCK;
		if (type == 5)
			return PERMANENT_BLOCK;
		return BASIC_BLOCK;
	}

	private int setLives(int type) {
		if (type == 2)
			return 2;
		if (type == 5)
			return 100000000;
		return 1;
	}

	private int setPoints(int type) {
		if (type == 1 | type == 2)
			return 1;
		if (type == 3 | type == 4)
			return 2;
		return 0;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPowerup(int power) {
		this.powerup = power;
	}

	public int getPowerup() {
		return this.powerup;
	}

	public Boolean hasPowerupBlock() {
		return (this.powerup == 1 || this.powerup == 2);
	}
	
	public void updateTime(double timePassed) {
		if (this.blockType == 4) time += timePassed; 
	}
	
	public double checkTime() {
		return this.time;
	}

	public Boolean hasBalls() {
		return (this.powerup == 3);
	}

	public void deletePowerup() {
		this.powerup = 0;
	}

	public void setX(double position) {
		this.blockObject.setX(position);
	}

	public double getX() {
		return this.blockObject.getX();
	}

	public void setY(double position) {
		this.blockObject.setY(position);
	}

	public double getY() {
		return this.blockObject.getY();
	}

	public void multiplyOn() {
		this.multiply = true;
	}

	public void multiplyOff() {
		this.multiply = false;
	}

	public Boolean getMultiply() {
		return this.multiply;
	}

	public void turnOff() {
		this.blockOn = false;
	}

	public Boolean isOn() {
		return this.blockOn;
	}

	public void loseLife() {
		this.lives--;
	}

	public int getLives() {
		return this.lives;
	}

	public void downgradeBlock(double x, double y) {
		Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(BASIC_BLOCK));
		this.blockObject = new ImageView(blockImage);
		this.blockObject.setX(x);
		this.blockObject.setY(y);
	}
}
