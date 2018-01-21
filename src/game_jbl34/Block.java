package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {

	public ImageView blockObject;
	public Boolean blockOn;
	public int blockType;
	public int lives;
	public int points;
	public Boolean multiply;
	public int powerup;
	
	private static final String BASIC_BLOCK = "basic_brick.gif";
	private static final String TWO_BLOCK = "block2.gif";
	private static final String POINT_BLOCK = "block3.gif";
	private static final String TIME_BLOCK = "block4.gif";
	private static final String PERMANENT_BLOCK = "block5.gif";
	
	/**
	 * Given the type of block, create a new block with the appropriate features
	 * @param type
	 */
	public Block(int type) {
		blockOn = true;
		multiply = false;
		blockType = type;
		Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(chooseImage(type)));
		blockObject = new ImageView(blockImage);
		lives = setLives(type);
		points = setPoints(type);
	}
	
	private String chooseImage(int type) {
		if (type == 2) return TWO_BLOCK;
		if (type == 3) return POINT_BLOCK;
		if (type == 4) return TIME_BLOCK;
		if (type == 5) return PERMANENT_BLOCK;
		return BASIC_BLOCK;
	}
	
	private int setLives(int type) {
		if (type == 2) return 2;
		if (type == 5) return 100000000;
		return 1;
	}
	
	private int setPoints(int type) {
		if (type == 1 | type == 2) return 1;
		if (type == 3 | type == 4) return 2;
		return 0;
	}
	
	/**
	 * Remove the block and turn it off
	 */
	public void destroy() {
		this.lives--;
		if (this.blockType == 2 && this.lives == 1) {
			double saveX = this.blockObject.getX();
			double saveY = this.blockObject.getY();
			Start.root.getChildren().remove(this.blockObject);
			Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(BASIC_BLOCK));
			this.blockObject = new ImageView(blockImage);
			this.blockObject.setX(saveX);
			this.blockObject.setY(saveY);
			Start.root.getChildren().add(this.blockObject);
		}
		if (this.lives == 0) {
			Start.myPoints += this.points;
			if (multiply) Start.myPoints += this.points;
			Start.root.getChildren().remove(this.blockObject);
			this.blockOn = false;
		}
	}
	
	public Boolean hasPowerupBlock() {
		return (this.powerup == 1 || this.powerup == 2);
	}
	
	public Boolean releaseBalls() {
		return (this.powerup == 3);
	}
	
	public double getX() {
		return this.blockObject.getX();
	}
	
	public double getY() {
		return this.blockObject.getY();
	}
}
