package game_jbl34;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	public static final String WELCOME_SCREEN = "splash.gif";
	public static final String LOST_SCREEN = "lost.gif";
	public static final String WON_SCREEN = "won.gif";
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
	private Paddle myPaddle;
	private Block[] myBlocks;
	private Ball myBall;
	private Ball[] lifeBalls;
	private int[] Powerups;
	private ArrayList<Powerup> PowerupList = new ArrayList<Powerup>();
	public static int myPoints = 0;
	public int myLives = 3;
	private Text pointDisplay;
	private Text doneText;
	private Stage currentStage;
	private Timeline animation;
	private double pTime;
	private double stickyTime;
	
	public double ballXSpeed = 65;
	public double ballYSpeed = 65;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage beginGame) {	
		myLevel = 0;
		myScene = setLevel(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
		currentStage = beginGame;
		beginGame.setScene(myScene);
		beginGame.setTitle("Breakout");
		beginGame.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.setDelay(Duration.millis(1000));
		animation.play();
	}

 	public Scene startGame(int width, int height, Paint background) {
		root = new Group();
		Scene scene = new Scene(root, width, height, background);
		Text welcome = new Text(GAME_WIDTH/2, GAME_HEIGHT/2, "Hello, world!");
		root.getChildren().add(welcome);
		return scene;
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
			Powerups = new int[numBlocks];
			int i = 0;
			while (scan.hasNextInt()) {
				blockType[i] = scan.nextInt();
				blockPos[i][0] = scan.nextDouble();
				blockPos[i][1] = scan.nextDouble();
				Powerups[i] = scan.nextInt();
				i++;
			}
			scan.close();
		} catch (Exception E) {
			System.out.println(E);
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

		if (myLevel == 0) {
			return;
		}

		// move the ball
		
		myBall.speedBall(myPaddle, elapsedTime);
		myBall.ballObject.setX(myBall.ballObject.getX() + myBall.xSpeed * elapsedTime);
		myBall.ballObject.setY(myBall.ballObject.getY() + myBall.ySpeed * elapsedTime);

		int count = numBlocks;
		for (Block block : myBlocks) {
			if (myBall.ballObject.getBoundsInParent().intersects(block.blockObject.getBoundsInParent())
					&& block.blockOn) {
				myBall.streak++;
				if (myBall.streak > 6) multiplyOn();
				if (myBall.bounce) myBall.ySpeed = -1 * myBall.ySpeed;
				checkPowerups(block);
				block.destroy();
			}
			
			if (!block.blockOn)
				count--;
		}
		
		pTime += elapsedTime;
		if (pTime > 10) myBall.bounce = true;
		
		stickyTime += elapsedTime;
		if (pTime > 30) myPaddle.notSticky();
		
		for (Powerup power : PowerupList) {
			power.powerObject.setY(power.powerObject.getY() + power.ySpeed * elapsedTime);
			if (power.powerObject.getBoundsInParent().intersects(myPaddle.paddleObject.getBoundsInParent())) {
				activatePowerup(power);
				root.getChildren().remove(power.powerObject);
			}
		}
		
		pointDisplay.setText("Points: " + myPoints);

		if (count == 0) {
			myLevel++;
			if (myLevel == 5) {
				displayWon();
			}
			chooseLevel();
			changeScene();
		}
		if (myBall.ballObject.getY() > GAME_HEIGHT - 10) {
			myLives--;
			checkLives();
			myBall.turnOff();
			myBall.ballObject.setX(GAME_WIDTH / 2);
			myBall.ballObject.setY(GAME_HEIGHT - 150);
		}
		
	}
	
	private Scene setLevel(int width, int height, Paint background) {
		/**
		 * refresh root
		 */
		root = new Group();
		// create level to see other objects
		Scene scene = new Scene(root, width, height, background);

		if (myLevel == 0) {
			ImageView welcomeScreen = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(WELCOME_SCREEN)));
			root.getChildren().add(welcomeScreen);
		} 
		else {
			chooseLevel();
			
			// create the paddle
			myPaddle = new Paddle(0);
			myPaddle.paddleObject.setX(GAME_WIDTH / 2);
			myPaddle.paddleObject.setY(GAME_HEIGHT - 10);
			
			
			readInput(levelDoc);
			
			// create blocks based on current level
			for (int i = 0; i < numBlocks; i++) {
				myBlocks[i] = new Block(blockType[i]);
				myBlocks[i].blockObject.setX(blockPos[i][0]);
				myBlocks[i].blockObject.setY(blockPos[i][1]);
				myBlocks[i].powerup = Powerups[i];
			}
			
			lifeBalls = new Ball[myLives];
			for (int i=0; i< myLives; i++) {
				lifeBalls[i] = new Ball();
				lifeBalls[i].ballObject.setX(GAME_WIDTH - 20 * i - 20);
				lifeBalls[i].ballObject.setY(10);
				root.getChildren().add(lifeBalls[i].ballObject);
			}

			// create first ball
			generateBall();

			// create text
			pointDisplay = new Text("Points: " + myPoints);
			pointDisplay.setFont(new Font(12));
			pointDisplay.setX(5);
			pointDisplay.setY(15);

			// add shapes to root to display
			root.getChildren().add(myPaddle.paddleObject);
			for (int i = 0; i < numBlocks; i++) {
				root.getChildren().add(myBlocks[i].blockObject);
			}
			root.getChildren().add(myBall.ballObject);
			root.getChildren().add(pointDisplay);

		}

		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	
	private void changeScene() {
		currentStage.close();
		Stage newGame = new Stage();
		currentStage = newGame;
		Scene newScene = setLevel(GAME_WIDTH,GAME_HEIGHT,BACKGROUND_COLOR);
		newGame.setScene(newScene);
		newGame.setTitle("Breakout");
		newGame.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.setDelay(Duration.seconds(2));
		animation.play();
	}
	
	private void handleKeyInput(KeyCode code) {
		if (myLevel == 0 && code == KeyCode.SPACE) {
			myLevel++;
			chooseLevel();
			changeScene();
		}
		if (myLevel > 0 && code == KeyCode.SPACE && !myBall.move) myBall.turnOn();
		if (myLevel > 0 && code == KeyCode.SPACE && myBall.move && myPaddle.isSticky()) {
			myBall.xSpeed = 65;
			myBall.ySpeed = -65;
		}
		if (code == KeyCode.RIGHT) {
			if (myPaddle.paddleObject.getX() <= GAME_WIDTH - 40) {
				myPaddle.paddleObject.setX(myPaddle.paddleObject.getX() + PADDLE_SPEED);
			}
		}
		if (code == KeyCode.LEFT) {
			if (myPaddle.paddleObject.getX() >= 0) {
				myPaddle.paddleObject.setX(myPaddle.paddleObject.getX() - PADDLE_SPEED);
			}
		}
		if (code == KeyCode.L) {
			if (myLevel <= 4) {
				myLevel++;
				chooseLevel();
				changeScene();
			}
		}
		if (code == KeyCode.S) {
			myBall.xSpeed = myBall.xSpeed * .75;
			myBall.ySpeed = myBall.ySpeed * .75;
		}
		if (code == KeyCode.F) {
			myBall.xSpeed = myBall.xSpeed * 1.5;
			myBall.ySpeed = myBall.ySpeed * 1.5;
		}
		if (code == KeyCode.O && !myPaddle.wasSticky()) {
			double saveX = myPaddle.paddleObject.getX();
			double saveY = myPaddle.paddleObject.getY();
			root.getChildren().remove(myPaddle.paddleObject);
			myPaddle.makeSticky();
			stickyTime = 0;
			myPaddle.paddleObject.setX(saveX);
			myPaddle.paddleObject.setY(saveY);
			root.getChildren().add(myPaddle.paddleObject);
		}
		if (code == KeyCode.R) {
			restoreLives();
		}
	}

	private void generateBall() {
		myBall = new Ball();
		myBall.ballObject.setX(GAME_WIDTH / 2);
		myBall.ballObject.setY(GAME_HEIGHT - 150);
		myBall.streak = 0;
	}
	
	private void checkLives() {
		if (myLives < 0) {
			root.getChildren().remove(myBall.ballObject);
			displayLost();
		}
		if (myLives >= 0 && myLives < 3) root.getChildren().remove(lifeBalls[myLives].ballObject);
	}
	
	private void displayLost() {
		ImageView lostDisplay = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(LOST_SCREEN)));
		root.getChildren().remove(pointDisplay);
		endText();
		root.getChildren().add(lostDisplay);
		root.getChildren().add(doneText);
	}
	
	private void displayWon() {
		ImageView wonDisplay = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(WON_SCREEN)));
		root.getChildren().remove(pointDisplay);
		endText();
		root.getChildren().add(wonDisplay);
		root.getChildren().add(doneText);
	}
	
	private void endText() {
		doneText = new Text("" + myPoints);
		doneText.setFont(new Font(40));
		doneText.setX(GAME_WIDTH /2 + 30);
		doneText.setY(GAME_HEIGHT/2 + 70);
	}
	
	private void multiplyOn() {
		for (Block block : myBlocks) {
			block.multiply = true;
		}
	}
	
	private void restoreLives() {
		for (int i = 0; i < myLives; i++ ) {
			root.getChildren().remove(lifeBalls[i].ballObject);
		}
		myLives = 3;
		for (int i = 0; i < myLives; i++ ) {
			root.getChildren().add(lifeBalls[i].ballObject);
		}
	}
		
	private void checkPowerups(Block block) {
		if (block.hasPowerupBlock()) {
			Powerup newPowerup = new Powerup(block.powerup);
			PowerupList.add(newPowerup);
			newPowerup.powerObject.setX(block.getX());
			newPowerup.powerObject.setY(block.getY());
			root.getChildren().add(newPowerup.powerObject);
			newPowerup.startMoving();
		}
		if (block.releaseBalls()) {
			
		}
	}
	
	private void activatePowerup(Powerup powerUp) {
		if (powerUp.pType == 1) {
			double saveX = myPaddle.paddleObject.getX();
			double saveY = myPaddle.paddleObject.getY();
			root.getChildren().remove(myPaddle.paddleObject);
			myPaddle.makeLong();
			myPaddle.paddleObject.setX(saveX);
			myPaddle.paddleObject.setY(saveY);
			root.getChildren().add(myPaddle.paddleObject);
		}
		if (powerUp.pType == 2) {
			pTime = 0;
			myBall.bounce = false;
		}
	}
}

