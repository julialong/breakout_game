package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The paddle for a basic Breakout game for CS308, Spring 2018
 * @author julia
 *
 */

public class Paddle{
	
	public String paddleFile; // the paddle image may change with powerups 
	public Boolean sticky; // one powerup will allow the paddle to become sticky
	public Boolean longPaddle; // another powerup will make the paddle 150% longer
	public ImageView paddleObject; // ImageView of paddle
	
	// possible images
	private static final String paddle1 = "paddle.gif";
	private static final String paddle2 = "sticky_paddle.gif";
	private static final String paddle3 = "long_paddle.gif";
	
	public Paddle(int powerup){
		if(powerup == 0) {
			paddleFile = paddle1;
			sticky = false;
			longPaddle = false;
		}
		else if(powerup == 1){
			paddleFile = paddle2;
			sticky = true;
			longPaddle = false;
		}
		else {
			paddleFile = paddle3;
			sticky = false;
			longPaddle = true;
		}
		Image paddleImage = new Image(getClass().getClassLoader().getResourceAsStream(paddleFile));
		paddleObject = new ImageView(paddleImage);
	}
	
	public void makeSticky() {
		this.paddleObject = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(paddle2)));
		this.sticky = true;
	}
	
	public Boolean isSticky() {
		return this.sticky;
	}
}
