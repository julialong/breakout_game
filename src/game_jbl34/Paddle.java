package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The paddle for a basic Breakout game for CS308, Spring 2018
 * @author julia
 *
 */

public class Paddle{
	
	private int xPosition;
	public String paddleImage; // the paddle image may change with powerups 
	public Boolean sticky; // one powerup will allow the paddle to become sticky
	public Boolean longPaddle; // another powerup will make the paddle 150% longer
	public ImageView paddleObject; // ImageView of paddle
	
	// possible images
	private static final String paddle1 = "paddle.gif";
	//private static final String paddle2 = "stickyPaddle.png";
	//private static final String paddle3 = "longPaddle.png";
	
	public Paddle(int powerup){
		if(powerup == 0) {
			paddleImage = paddle1;
			sticky = false;
			longPaddle = false;
		}
		else if(powerup == 1){
			paddleImage = paddle1;
			sticky = true;
			longPaddle = false;
		}
		else {
			paddleImage = paddle1;
			sticky = false;
			longPaddle = true;
		}
		paddleImage = paddle1;
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(paddle1));
		paddleObject = new ImageView(paddleImage);
	}
}
