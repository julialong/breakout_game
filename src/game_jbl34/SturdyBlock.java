package game_jbl34;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * SturdyBlock is a subclass of the Block superclass. This type of block takes two hits to be completely destroyed, so
 * it overrides a couple of the methods from the superclass
 */

public class SturdyBlock extends Block{

    private ImageView blockObject;
    private static final String BASIC_BLOCK = "basic_brick.gif";
    private static final String TWO_BLOCK = "block2.gif";

    /**
     * Creates a block with two points that needs to be hit twice to be destroyed
     */
    SturdyBlock() {
        super();
        super.setLives(2);
        super.setPoints(2);
        super.setBlockObject(TWO_BLOCK);
    }

    /**
     * Overrides the superclass method because this kind of block will not be destroyed after one hit
     * @return true because the block starts with more than one life
     */
    @Override
    public Boolean isSturdy() {
        return true;
    }

    /**
     * This overrides the superclass because the block will need to change appearance after one hit
     * @param x x position
     * @param y y position
     */
    @Override
    public void downgradeBlock(double x, double y) {
        Image blockImage = new Image(getClass().getClassLoader().getResourceAsStream(BASIC_BLOCK));
        this.blockObject = new ImageView(blockImage);
        this.blockObject.setX(x);
        this.blockObject.setY(y);
    }
}