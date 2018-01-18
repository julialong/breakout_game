package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {

	public ImageView blockObject;
	public Boolean blockOn;
	
	private static final String BASIC_BLOCK = "basic_brick.gif";
	
	/**
	 * Given the type of block, create a new block with the appropriate features
	 * @param type
	 */
	public Block(int type) {
		blockOn = true;
		Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(BASIC_BLOCK));
		blockObject = new ImageView(blockImage);
	}
	
	/**
	 * Remove the block and turn it off
	 */
	public void destroy() {
		Start.root.getChildren().remove(this.blockObject);
		this.blockOn = false;
	}
}
