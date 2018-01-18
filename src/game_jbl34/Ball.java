package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {

	public ImageView ballObject;
	public double xSpeed;
	public double ySpeed;
	
	private static final String BALL_IMAGE = "ball.gif";
	
	public Ball() {
		Image ballImage = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		ballObject = new ImageView(ballImage);
	}
	
	public void speedBall(ImageView myPaddle, double elapsedTime) {
		if(this.ballObject.getX() <= 0 | this.ballObject.getX() >= Start.GAME_WIDTH) {
			this.xSpeed = -1 * this.xSpeed;
		}
		
		if(this.ballObject.getY() <= 0) {
			this.ySpeed = -1 * this.ySpeed;
		}

		// check if the ball hits the paddle
		if (this.ballObject.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
			this.ySpeed = -1 * this.ySpeed;
		}
	}
}
