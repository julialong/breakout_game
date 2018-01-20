package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {

	public ImageView ballObject;
	public double xSpeed;
	public double ySpeed;
	public Boolean move;
	public int streak;
	
	private static final String BALL_IMAGE = "ball.gif";
	
	public Ball() {
		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		ballObject = new ImageView(ballImage);
		move = false;
		xSpeed = 0;
		ySpeed = 0;
		streak = 0;
	}
	
	public void turnOn() {
		this.move = true;
		this.xSpeed = 65;
		this.ySpeed = 65;
	}
	
	public void turnOff() {
		this.move = false;
		this.xSpeed = 0;
		this.ySpeed = 0;
	}
	
	public void speedBall(Paddle myPaddle, double elapsedTime) {
		if(this.ballObject.getX() <= 0 | this.ballObject.getX() >= Start.GAME_WIDTH-10) {
			this.xSpeed = -1 * this.xSpeed;
			this.ySpeed = 1 * this.ySpeed;
			this.streak = 0;
		}
		
		if(this.ballObject.getY() <= 0) {
			this.ySpeed = -1 * this.ySpeed;
			this.xSpeed = 1 * this.xSpeed;
			this.streak = 0;
		}

		// check if the ball hits the paddle
		if (this.ballObject.getBoundsInParent().intersects(myPaddle.paddleObject.getBoundsInParent()) && !myPaddle.isSticky()) {
			this.ballObject.setX(this.ballObject.getX() - this.xSpeed * elapsedTime);
			this.ballObject.setY(this.ballObject.getY() - this.ySpeed * elapsedTime);
			this.ySpeed = -1 * this.ySpeed;
		}
		if (this.ballObject.getBoundsInParent().intersects(myPaddle.paddleObject.getBoundsInParent()) && !myPaddle.isSticky()) {
			this.ballObject.setX(myPaddle.paddleObject.getX());
			this.ballObject.setY(myPaddle.paddleObject.getY());
			this.xSpeed = 0;
			this.ySpeed = 0;
		}
	}
	
	public void delete() {
		this.xSpeed = 0;
		this.ySpeed = 0;
	}
}
