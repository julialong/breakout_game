package game_jbl34;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
	public static final Paint BACKGROUND_COLOR = Color.PINK;
	public static final int GAME_HEIGHT = 400;
	public static final int GAME_WIDTH = 600;
	public static final int FRAMES_PER_SECOND = 40;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double PADDLE_SPEED = 10;
    
    // variables from input file determine number and structure of blocks
    public String blockFile;
    public int numBlocks;
	
	// important components of the game
	private Scene myScene;
	private ImageView myPaddle;
	private ImageView[] myBlocks[];
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage beginGame) {
		
		myScene = setupGame(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
		beginGame.setScene(myScene);
		beginGame.setTitle("Breakout");
		beginGame.show();
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public Scene setupGame(int width, int height, Paint background) {
		// create the top level for the other objects in the game
		Group root = new Group();
		// create level to see other objects
		Scene scene = new Scene(root, width, height, background);
		Paddle bounce = new Paddle(0);
		myPaddle = bounce.paddleObject;
		myPaddle.setX(GAME_WIDTH / 2);
		myPaddle.setY(GAME_HEIGHT -10);
		
		
		// add shapes to root to display
		root.getChildren().add(myPaddle);
		
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		
		return scene;
	}
	
	public void readInput(String input) {
		
	}
	
	private void step(double elaspedTime) {
		
	}
	
	private void handleKeyInput (KeyCode code) {
		if (code == KeyCode.RIGHT) {
			myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
		}
		if (code == KeyCode.LEFT) {
			myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
		}
	}
}
