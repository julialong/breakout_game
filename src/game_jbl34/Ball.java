package game_jbl34;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {

	private ImageView ballObject;
	private double xSpeed;
	private double ySpeed;
	private Boolean move;
	private int streak;
	private Boolean bounce;
	private Boolean stuck;

	private static final String BALL_IMAGE = "ball.gif";
	private static final String EXTRA_IMAGE = "extra_ball.gif";

	public Ball() {
		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		ballObject = new ImageView(ballImage);
		move = false;
		xSpeed = 0;
		ySpeed = 0;
		streak = 0;
		stuck = false;
		bounce = true;
	}

	public void turnOn() {
		this.move = true;
		int speed = (new Random().nextInt(15) + 45);
		this.xSpeed = speed;
		this.ySpeed = speed;
	}

	public void turnOff() {
		this.move = false;
		this.xSpeed = 0;
		this.ySpeed = 0;
	}

	public void speedBall(Paddle myPaddle, double elapsedTime) {
		if (this.ballObject.getX() <= 0 | this.ballObject.getX() >= Start.GAME_WIDTH - 10 && !this.isStuck()) {
			this.xSpeed = -1 * this.xSpeed;
			this.ySpeed = 1 * this.ySpeed;
			this.streak = 0;
		}

		if (this.ballObject.getY() <= 0 && !this.isStuck()) {
			this.ySpeed = -1 * this.ySpeed;
			this.xSpeed = 1 * this.xSpeed;
			this.streak = 0;
		}

		// check if the ball hits the paddle
		if (this.ballObject.getBoundsInParent().intersects(myPaddle.getPaddleObject().getBoundsInParent())
				&& !myPaddle.isSticky()) {
			this.ballObject.setX(this.ballObject.getX() - this.xSpeed * elapsedTime);
			this.ballObject.setY(this.ballObject.getY() - this.ySpeed * elapsedTime);
			this.ySpeed = -1 * this.ySpeed;
		}
		if (this.ballObject.getBoundsInParent().intersects(myPaddle.getPaddleObject().getBoundsInParent())
				&& myPaddle.isSticky()) {
			this.ballObject.setX(myPaddle.getX() + 30);
			this.ballObject.setY(myPaddle.getY() - 10);
			this.makeStuck();
			this.turnOff();
			this.xSpeed = 0;
			this.ySpeed = 0;
		}
	}

	public void delete() {
		this.xSpeed = 0;
		this.ySpeed = 0;
	}

	public void makePowerup() {
		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(EXTRA_IMAGE));
		this.ballObject = new ImageView(ballImage);
		move = true;
		this.turnOn();
	}

	public ImageView getBallObject() {
		return this.ballObject;
	}

	public Boolean getBounce() {
		return this.bounce;
	}

	public void setBounce(Boolean value) {
		this.bounce = value;
	}

	public Boolean isStuck() {
		return this.stuck;
	}

	public void makeStuck() {
		this.stuck = true;
	}

	public void unStuck() {
		this.stuck = false;
		this.move = true;
		int speed = (new Random().nextInt(15) + 45);
		this.xSpeed = speed;
		this.ySpeed = -1 * speed;
	}

	public double getX() {
		return this.ballObject.getX();
	}

	public void setX(double position) {
		this.ballObject.setX(position);
	}

	public double getY() {
		return this.ballObject.getY();
	}

	public void setY(double position) {
		this.ballObject.setY(position);
	}

	public void setXSpeed(double speed) {
		this.xSpeed = speed;
	}

	public double getXSpeed() {
		return this.xSpeed;
	}

	public double getYSpeed() {
		return this.ySpeed;
	}

	public void setYSpeed(double speed) {
		this.ySpeed = speed;
	}

	public int getStreak() {
		return this.streak;
	}

	public void increaseStreak() {
		this.streak++;
	}

	public Boolean getMove() {
		return this.move;
	}
}
