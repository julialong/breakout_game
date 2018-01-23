package game_jbl34;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
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
	public static final double MAX_BLOCK_TIME = 60;
	public static final String LEVEL_1 = "Level_One.txt";
	public static final String LEVEL_2 = "Level_Two.txt";
	public static final String LEVEL_3 = "Level_Three.txt";
	public static final String LEVEL_4 = "Level_Four.txt";

	// variables from input file determine number and structure of blocks
	private String blockFile;
	private int numBlocks;
	private int numLeft;
	private int[] blockType;
	private double[][] blockPos;
	private Group root;
	private Boolean hardMode = false;
	private double hardTime;

	// important components of the game
	private Scene myScene;
	private int myLevel;
	private Paddle myPaddle;
	private Block[] myBlocks;
	private Ball myBall;
	private Ball[] lifeBalls;
	private ArrayList<Ball> extraBalls;
	private int[] Powerups;
	private ArrayList<Powerup> PowerupList = new ArrayList<Powerup>();
	private int myPoints = 0;
	private int myLives = 3;
	private int maxLives = 3;
	private Display pointDisplay;
	private Display timeDisplay;
	private Display levelDisplay;
	private Text doneText;
	private Stage currentStage;
	private Timeline animation;
	private KeyFrame frame;
	private double pTime;
	private double stickyTime;

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

		startAnimation();
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
			System.out.println("File not found");
		}
	}

	private void chooseLevel() {
		if (myLevel == 1) {
			blockFile = LEVEL_1;
		}
		if (myLevel == 2) {
			blockFile = LEVEL_2;
		}
		if (myLevel == 3) {
			blockFile = LEVEL_3;
		}
		if (myLevel == 4) {
			blockFile = LEVEL_4;
		}
	}

	private void step(double elapsedTime) {

		if (myLevel == 0 || myLevel == 5) {
			return;
		}
		moveBalls(elapsedTime);
	
		checkTimedObjects(elapsedTime);

		checkLevel();

		pointDisplay.changeDisplay("Points: " + myPoints);
	}

	private Scene setLevel(int width, int height, Paint background) {
		root = new Group();
		
		Scene scene = new Scene(root, width, height, background);

		if (myLevel == 0) {
			ImageView welcomeScreen = new ImageView(
					new Image(getClass().getClassLoader().getResourceAsStream(WELCOME_SCREEN)));
			root.getChildren().add(welcomeScreen);
		} else {
			chooseLevel();
			
			createPaddle();
			
			readInput(blockFile);
			
			createBlocks();
			
			extraBalls = new ArrayList<Ball>();
			
			createLifeIndicators();
			
			generateBall();
			
			checkHardMode();
			
			createStatusDisplay();
			
			initializeScreen();
		}

		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}

	private void changeScene() {
		currentStage.close();
		Stage newGame = new Stage();
		currentStage = newGame;
		Scene newScene = setLevel(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
		newGame.setScene(newScene);
		newGame.setTitle("Breakout");
		newGame.show();

		startAnimation();
	}

	private void handleKeyInput(KeyCode code) {
		if (myLevel == 0 && code == KeyCode.SPACE) {
			myLevel++;
			chooseLevel();
			changeScene();
		}
		if (myLevel > 0 && code == KeyCode.SPACE && !myBall.isStuck() && !myBall.getMove()) {
			myBall.turnOn();
		}
		if (myLevel > 0 && code == KeyCode.SPACE && !myBall.getMove() && myBall.isStuck()) {
			myBall.unStuck();
			myBall.setY(myBall.getY() - 5);

		}
		if (code == KeyCode.RIGHT) {
			if (myPaddle.getX() <= GAME_WIDTH - 40) {
				myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
				if (myPaddle.isSticky() && paddleBall() != null)
					changeBallPos(paddleBall(), myPaddle.getX(), paddleBall().getY());
			}
		}
		if (code == KeyCode.LEFT) {
			if (myPaddle.getX() >= 0) {
				myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
				if (myPaddle.isSticky() && paddleBall() != null)
					changeBallPos(paddleBall(), myPaddle.getX(), paddleBall().getY());
			}
		}
		if (myLevel == 0 && code == KeyCode.H) {
			hardMode = true;
		}
		if (code == KeyCode.L) {
			if (myLevel < 4) {
				myLevel++;
				chooseLevel();
				changeScene();
			}
		}
		if (code == KeyCode.S) {
			myBall.setXSpeed(myBall.getXSpeed() * .75);
			myBall.setYSpeed(myBall.getYSpeed() * .75);
		}
		if (code == KeyCode.F) {
			myBall.setXSpeed(myBall.getXSpeed() * 1.5);
			myBall.setYSpeed(myBall.getYSpeed() * 1.5);
		}
		if (code == KeyCode.O && !myPaddle.wasSticky()) {
			double saveX = myPaddle.getX();
			double saveY = myPaddle.getY();
			root.getChildren().remove(myPaddle.getPaddleObject());
			myPaddle.makeSticky();
			stickyTime = 0;
			myPaddle.setX(saveX);
			myPaddle.setY(saveY);
			root.getChildren().add(myPaddle.getPaddleObject());
		}
		if (code == KeyCode.R) {
			restoreLives();
		}
	}

	private void startAnimation() {
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.setDelay(Duration.seconds(2));
		animation.play();
	}
	
	private void createPaddle() {
		myPaddle = new Paddle();
		myPaddle.setX(GAME_WIDTH / 2);
		myPaddle.setY(GAME_HEIGHT - 10);
	}

	private void checkHardMode() {
		if (hardMode) {
			hardTime = 160;
		}
	}

	private void createStatusDisplay() {
		pointDisplay = new Display("Points: " + myPoints, 5, 15);
		levelDisplay = new Display("Level: " + myLevel, 5, 30);
		if (hardMode) {
			timeDisplay = new Display("Time remaining: " + hardTime, GAME_WIDTH / 2 - 40, 15);
			root.getChildren().add(timeDisplay.getDisplayText());
		}
		root.getChildren().add(levelDisplay.getDisplayText());
		root.getChildren().add(pointDisplay.getDisplayText());
	}
	
	private void initializeScreen() {
		root.getChildren().add(myPaddle.getPaddleObject());
		for (int i = 0; i < numBlocks; i++) {
			root.getChildren().add(myBlocks[i].getBlockObject());
		}
		root.getChildren().add(myBall.getBallObject());
	}
	
	private void createLifeIndicators() {
		lifeBalls = new Ball[myLives];
		for (int i = 0; i < myLives; i++) {
			lifeBalls[i] = new Ball();
			lifeBalls[i].setX(GAME_WIDTH - 20 * i - 20);
			lifeBalls[i].setY(10);
			root.getChildren().add(lifeBalls[i].getBallObject());
		}
	}

	private void generateBall() {
		myBall = new Ball();
		myBall.setX(GAME_WIDTH / 2);
		myBall.setY(GAME_HEIGHT - 150);
	}
	
	private void moveBalls(double elapsedTime) {
		myBall.speedBall(myPaddle, elapsedTime);
		changeBallPos(myBall, myBall.getBallObject().getX() + myBall.getXSpeed() * elapsedTime,
				myBall.getBallObject().getY() + myBall.getYSpeed() * elapsedTime);

		checkCollisions(myBall);
		checkPowerupBalls(elapsedTime);
		checkIfDied();
	}
	
	private void checkPowerupBalls(double elapsedTime) {
		if (extraBalls != null) {
			for (Iterator<Ball> itBall = extraBalls.iterator(); itBall.hasNext();) {
				Ball ball = itBall.next();
				ball.speedBall(myPaddle, elapsedTime);
				changeBallPos(ball, ball.getBallObject().getX() + ball.getXSpeed() * elapsedTime,
						ball.getBallObject().getY() + ball.getYSpeed() * elapsedTime);
				checkCollisions(ball);
			}
		}
	}
	
	private void checkIfDied() {
		if (myBall.getY() > GAME_HEIGHT - 10) {
			myLives--;
			checkLives();
			myBall.turnOff();
			myBall.setX(GAME_WIDTH / 2);
			myBall.setY(GAME_HEIGHT - 150);
		}
	}
	
	private void checkTimedObjects(double elapsedTime) {
		checkTimedBlocks(elapsedTime);
		checkTimedBalls(elapsedTime);
		checkStickyTime(elapsedTime);
		updateHardMode(elapsedTime);
		checkPowerups(elapsedTime);
	}
	
	private void checkPowerups(double elapsedTime) {
		for (Powerup power : PowerupList) {
			power.setY(power.getY() + power.getYSpeed() * elapsedTime);
			if (power.getPowerObject().getBoundsInParent().intersects(myPaddle.getPaddleObject().getBoundsInParent())) {
				activatePowerup(power);
				root.getChildren().remove(power.getPowerObject());
			}
		}
	}
	
	private void checkTimedBlocks(double elapsedTime) {
		for (Block block : myBlocks) {
			block.updateTime(elapsedTime);
			if (block.getBlockType() == 4 && block.checkTime() > MAX_BLOCK_TIME) destroy(block);
		}
	}
	
	private void checkTimedBalls(double elapsedTime) {
		pTime += elapsedTime;
		if (pTime > 10) {
			myBall.setBounce(true);
			for (Ball ball : extraBalls) {
				ball.setBounce(true);
			}
		}
	}
	
	private void checkStickyTime(double elapsedTime) {
		if (myPaddle.isSticky()) {
			stickyTime += elapsedTime;
			if (stickyTime > 30) {
				normalPaddle();
			}
		}
	}
	
	private void checkHardTime() {
		if (hardMode) {
			if (hardTime <= 0) {
				hardMode = false;
				removeObject(myBall.getBallObject());
				cleanGame();
				displayLost();
			}
		}
	}
	
	private void updateHardMode(double elapsedTime) {
		if (hardMode) {
			hardTime = hardTime - elapsedTime;
			timeDisplay.changeDisplay("Time remaining: " + (int) hardTime);
		}
		checkHardTime();
	}

	private void checkLives() {
		if (myLives < 0) {
			removeObject(myBall.getBallObject());
			displayLost();
		}
		if (myLives >= 0 && myLives < 3)
			removeObject(lifeBalls[myLives].getBallObject());
	}

	private void displayLost() {
		ImageView lostDisplay = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(LOST_SCREEN)));
		endText();
		root.getChildren().add(lostDisplay);
		root.getChildren().add(doneText);
	}

	private void displayWon() {
		ImageView wonDisplay = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(WON_SCREEN)));
		endText();
		root.getChildren().add(wonDisplay);
		root.getChildren().add(doneText);
	}

	private void endText() {
		doneText = new Text("" + myPoints);
		doneText.setFont(new Font(40));
		doneText.setX(GAME_WIDTH / 2 + 30);
		doneText.setY(GAME_HEIGHT / 2 + 70);
	}

	private void createBlocks() {
		numLeft = numBlocks;
		for (int i = 0; i < numBlocks; i++) {
			myBlocks[i] = new Block(blockType[i]);
			if (blockType[i] == 5)
				numLeft--;
			myBlocks[i].setX(blockPos[i][0]);
			myBlocks[i].setY(blockPos[i][1]);
			myBlocks[i].setPowerup(Powerups[i]);
		}
	}

	private void multiplyOn() {
		for (Block block : myBlocks) {
			block.multiplyOn();
		}
	}

	private void restoreLives() {
		for (int i = 0; i < myLives; i++) {
			root.getChildren().remove(lifeBalls[i].getBallObject());
		}
		myLives = maxLives;
		for (int i = 0; i < myLives; i++) {
			root.getChildren().add(lifeBalls[i].getBallObject());
		}

	}

	private void releasePowerups(Block block) {
		if (block.hasPowerupBlock()) {
			Powerup newPowerup = new Powerup(block.getPowerup());
			PowerupList.add(newPowerup);
			newPowerup.setX(block.getX() + 20);
			newPowerup.setY(block.getY() + 10);
			root.getChildren().add(newPowerup.getPowerObject());
			newPowerup.startMoving();
		}
		if (block.hasBalls()) {
			block.deletePowerup();
			releaseTheBalls(block);
		}
	}

	private void activatePowerup(Powerup powerUp) {
		if (powerUp.getType() == 1) {
			double saveX = myPaddle.getX();
			double saveY = myPaddle.getY();
			root.getChildren().remove(myPaddle.getPaddleObject());
			myPaddle.makeLong();
			myPaddle.setX(saveX);
			myPaddle.setY(saveY);
			root.getChildren().add(myPaddle.getPaddleObject());
		}
		if (powerUp.getType() == 2) {
			pTime = 0;
			myBall.setBounce(false);
		}
	}

	private void normalPaddle() {
		double saveX = myPaddle.getX();
		double saveY = myPaddle.getY();
		removeObject(myPaddle.getPaddleObject());
		myPaddle.notSticky();
		myPaddle.setX(saveX);
		myPaddle.setY(saveY);
		addObject(myPaddle.getPaddleObject());
	}

	private void releaseTheBalls(Block block) {
		for (int i = 0; i < 2; i++) {
			Ball newBall = new Ball();
			newBall.makePowerup();
			newBall.setX(block.getX());
			newBall.setY(block.getY());
			extraBalls.add(newBall);
			root.getChildren().add(newBall.getBallObject());
		}
	}

	private void checkCollisions(Ball ball) {
		for (Block block : myBlocks) {
			if (ball.getBallObject().getBoundsInParent().intersects(block.getBlockObject().getBoundsInParent())
					&& block.isOn()) {
				ball.increaseStreak();
				if (ball.getStreak() > 6)
					multiplyOn();
				if (ball.getBounce())
					ball.setYSpeed(-1 * ball.getYSpeed());
				releasePowerups(block);
				destroy(block);
				if (!block.isOn())
					numLeft--;
			}
		}
	}

	private void checkLevel() {
		if (numLeft == 0)
			noBlocks();
	}

	private void noBlocks() {
		if (myLevel == 4) {
			myLevel++;
			cleanGame();
			displayWon();
		}
		if (myLevel < 4) {
			myLevel++;
			chooseLevel();
			changeScene();
		}
	}

	private Ball paddleBall() {
		if (myBall.getBallObject().getBoundsInParent().intersects(myPaddle.getPaddleObject().getBoundsInParent()))
			return myBall;
		for (Ball ball : extraBalls) {
			if (ball.getBallObject().getBoundsInParent().intersects(myPaddle.getPaddleObject().getBoundsInParent()))
				return ball;
		}
		return null;
	}

	private void changeBallPos(Ball ball, double x, double y) {
		ball.setX(x);
		ball.setY(y);
	}

	private void cleanGame() {
		for (Ball ball : extraBalls) {
			root.getChildren().remove(ball.getBallObject());
		}
		root.getChildren().remove(myBall.getBallObject());
		root.getChildren().remove(myPaddle.getPaddleObject());
		root.getChildren().remove(pointDisplay.getDisplayText());
	}

	public void removeObject(ImageView object) {
		root.getChildren().remove(object);
	}

	public void addObject(ImageView object) {
		root.getChildren().add(object);
	}

	public void destroy(Block block) {
		block.loseLife();
		if (block.getBlockType() == 2 && block.getLives() == 1) {
			double saveX = block.getX();
			double saveY = block.getY();
			removeObject(block.getBlockObject());
			block.downgradeBlock(saveX, saveY);
			addObject(block.getBlockObject());
		}
		if (block.getLives() == 0) {
			myPoints += block.getPoints();
			if (block.getMultiply())
				myPoints += block.getPoints();
			removeObject(block.getBlockObject());
			block.turnOff();
		}
	}
}