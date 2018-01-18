package game_jbl34;

import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
	public static final Paint START_COLOR = Color.ANTIQUEWHITE;
	public static final Paint BACKGROUND_COLOR = Color.PINK;
	public static final int GAME_HEIGHT = 400;
	public static final int GAME_WIDTH = 600;
	public static final int FRAMES_PER_SECOND = 40;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final double PADDLE_SPEED = 40;
	public static final String LEVEL_1 = "Level_One.txt";
	public static final String LEVEL_2 = "Level_Two.txt";
	public static final String LEVEL_3 = "Level_Three.txt";
	public static final String LEVEL_4 = "Level_Four.txt";

	// variables from input file determine number and structure of blocks
	public String blockFile;
	public int numBlocks;
	public ImageView myBlock;
	public int[] blockType;
	public double[][] blockPos;
	public static Group root;

	// important components of the game
	private Scene myScene;
	private int myLevel;
	private String levelDoc;
	private ImageView myPaddle;
	private Block[] myBlocks;
	private Ball myBall;
	public static int myPoints = 0;
	private Text pointDisplay;
	
	public double ballXSpeed = 80;
	public double ballYSpeed = 80;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage beginGame) {	
		myLevel = 1;
		chooseLevel();
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

	public Scene startGame(int width, int height, Paint background) {
		root = new Group();
		Scene scene = new Scene(root, width, height, background);
		Text welcome = new Text(GAME_WIDTH/2, GAME_HEIGHT/2, "Hello, world!");
		root.getChildren().add(welcome);
		return scene;
	}
	
	public Scene setupGame(int width, int height, Paint background) {// create the top level for the other objects in the game
		return setLevel(width, height, background);
	}

	private void readInput(String input) {

		try {
			Scanner scan = new Scanner(getClass().getClassLoader().getResourceAsStream(input));

			if (scan.hasNextInt()) {
				numBlocks = scan.nextInt();
			} else {
				System.out.print("Wrong file type.");
			}
			blockType = new int[numBlocks];
			blockPos = new double[numBlocks][2];
			myBlocks = new Block[numBlocks];
			int i = 0;
			while (scan.hasNextInt()) {
				blockType[i] = scan.nextInt();
				blockPos[i][0] = scan.nextDouble();
				blockPos[i][1] = scan.nextDouble();
				i++;
			}
			scan.close();
		} catch (Exception E) {
			System.out.println("File not found.");
		}
	}

	private void chooseLevel() {
		if (myLevel == 1) {
		levelDoc = LEVEL_1;
		}
		if (myLevel == 2) {
			levelDoc = LEVEL_2;
		}
		if (myLevel == 3) {
			levelDoc = LEVEL_3;
		}
		if (myLevel == 4) {
			levelDoc = LEVEL_4;
		}
	}
	
	private void step(double elapsedTime) {

		// move the ball
		myBall.speedBall(myPaddle, elapsedTime);
		myBall.ballObject.setX(myBall.ballObject.getX() + .05 + myBall.xSpeed * elapsedTime);
		myBall.ballObject.setY(myBall.ballObject.getY() + .05 + myBall.ySpeed * elapsedTime);
		
		int count = numBlocks;
		for (Block block : myBlocks) {
			if (myBall.ballObject.getBoundsInParent().intersects(block.blockObject.getBoundsInParent()) && block.blockOn) {
				myBall.xSpeed = -1*myBall.xSpeed;
				myBall.ySpeed = -1*myBall.ySpeed;
				System.out.println(block.points);
				block.destroy();
				if (!block.blockOn) count--;
			} 
		}
		pointDisplay.setText("Points: " + myPoints);
		//System.out.println(myPoints);
		if (count == 0) {
			myLevel++;
			setLevel(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
		}
	}

	private Scene setLevel(int width, int height, Paint background) {
		/**
		 * refresh root
		 */
		root = new Group();
		// create level to see other objects
		Scene scene = new Scene(root, width, height, background);
		
		chooseLevel();
		
		// create the paddle
		Paddle bounce = new Paddle(0);
		myPaddle = bounce.paddleObject;
		myPaddle.setX(GAME_WIDTH / 2);
		myPaddle.setY(GAME_HEIGHT - 10);

		// create blocks based on current level
		
		readInput(levelDoc);
		for (int i = 0; i < numBlocks; i++) {
			myBlocks[i] = new Block(blockType[i]);
			myBlocks[i].blockObject.setX(blockPos[i][0]);
			myBlocks[i].blockObject.setY(blockPos[i][1]);
		}
		
		// create first ball 
		myBall = new Ball();
		myBall.xSpeed = ballXSpeed;
		myBall.ySpeed = ballYSpeed;
		myBall.ballObject.setX(GAME_WIDTH / 2);
		myBall.ballObject.setY(GAME_HEIGHT - 100);
		
		//create text
		pointDisplay = new Text("Points: " + myPoints);
		pointDisplay.setFont(new Font(12));
		pointDisplay.setX(5);
		pointDisplay.setY(10);

		// add shapes to root to display
		root.getChildren().add(myPaddle);
		for (int i = 0; i < numBlocks; i++) {
			root.getChildren().add(myBlocks[i].blockObject);
		}
		root.getChildren().add(myBall.ballObject);
		root.getChildren().add(pointDisplay);

		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	
	private void handleKeyInput(KeyCode code) {
		if (code == KeyCode.RIGHT) {
			if (myPaddle.getX() <= GAME_WIDTH - 40) {
				myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
			}
		}
		if (code == KeyCode.LEFT) {
			if (myPaddle.getX() >= 0) {
				myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
			}
		}
		if (code == KeyCode.L) {
			myLevel++;
			setLevel(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
		}
		if (code == KeyCode.S) {
			myBall.xSpeed = myBall.xSpeed/2;
			myBall.ySpeed = myBall.ySpeed/2;
		}
		if (code == KeyCode.F) {
			myBall.xSpeed = myBall.xSpeed*2;
			myBall.ySpeed = myBall.ySpeed*2;
		}
	}
}
