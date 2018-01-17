package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class basicBlock {
	
	public ImageView blockObject;
	
	private static final String BLOCK_IMAGE = "basic_block.gif";
	
	/**
	 * Create a basic block that will disappear after one hit
	 */
	public basicBlock() {
		Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE));
		blockObject = new ImageView(blockImage);
	}
}
