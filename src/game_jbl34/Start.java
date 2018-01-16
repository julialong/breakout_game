package game_jbl34;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
	public static final Paint Background_Color = Color.GRAY;
	public static final int Game_Height = 400;
	public static final int Game_Width = 600;
	public static final int FRAMES_PER_SECOND = 40;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	// important components of the game
	private Scene myScene;
	private Paddle myPaddle;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage beginGame) {
		
		myScene = setupGame(Game_Width, Game_Height, Background_Color);
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
		myPaddle = new Paddle(0);
		ImageView bounce = myPaddle.paddleObject;
		bounce.setX(Game_Width / 2);
		bounce.setY(Game_Height -10);
		// add shapes to root to display
		root.getChildren().add(bounce);
		return scene;
	}
	
	private void step(double elaspedTime) {
		
	}
}
