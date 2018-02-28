package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Block class is a superclass that defines the basic properties and methods available to the wide variety of blocks.
 * By changing this to a superclass, it became easier to access basic methods that all of the block types have in common
 * while allowing certain classes to override the relevant methods without if statements in each method to determine the
 * block type.
 * @author julia
 */
public abstract class Block {

    private ImageView blockObject;
	private Boolean blockOn;
	private int lives;
	private int points;
	private Boolean multiply;
	private int powerup;
	private double time;

	/**
	 * Creates a generic block without any specific special attributes or behaviors
	 */
	public Block() {
		blockOn = true;
		multiply = false;
		lives = 1;
		points = 1;
		time = 0.0;
	}

    /**
     * Sets the ImageView object for the given block
     * @param filename is the name of the image used for the given block
     */
    public void setBlockObject(String filename) {
        this.blockObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(filename)));
    }

    /**
     * Sets the number of lives for the given block
     */
    public void setLives(int lifeNum){
        this.lives = lifeNum;
    }

    /**
     * Sets the number of points for the given block
     */
    public void setPoints(int numPoints) {
        this.points = numPoints;
    }

    /**
     * This method is empty for all blocks that do not have a timed component
     * @param timePassed is the amount of time to add to the total time of the block
     */
	public void updateTime(double timePassed){
    }

    /**
     * @return whether or not the block is timed
     */
    public Boolean isTimed(){
	    return false;
    }

    /**
     * @return whether or not the block can be destroyed in one hit
     */
    public Boolean isSturdy() {
	    return false;
    }

    /**
     * @return the current total time of the block
     */
    public double checkTime(){
	    return this.time;
    }

	public ImageView getBlockObject() {
        return this.blockObject;
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

    /**
     * @return true if a powerup square should fall from the block when broken
     */
	public Boolean hasPowerupBlock() {
		return (this.powerup == 1 || this.powerup == 2);
	}

    /**
     * @return true if extra balls should fall from the block when broken
     */
	public Boolean hasBalls() {
		return (this.powerup == 3);
	}

    /**
     * Downgrades the block if the block is sturdy
     * @param x x position
     * @param y y position
     */
	public void downgradeBlock(double x, double y) {
    }

    /**
     * Removes the powerup from the block
     */
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

    /**
     * turns the multiplier on
     */
	public void multiplyOn() {
		this.multiply = true;
	}

	public Boolean getMultiply() {
		return this.multiply;
	}

    /**
     * Indicates that the block should not be visible to the player
     */
	public void turnOff() {
		this.blockOn = false;
	}

    /**
     * @return true if the block should be visible to the player
     */
	public Boolean isOn() {
		return this.blockOn;
	}

    /**
     * Updates the lives of the block
     */
	public void loseLife() {
		this.lives--;
	}

	public int getLives() {
		return this.lives;
	}
}