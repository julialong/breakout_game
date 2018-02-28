package game_jbl34;

/**
 * SturdyBlock is a subclass of the Block superclass. It can never be destroyed, so it has to call the superclass method
 * setLives() to set its lives to the number 100000 instead of the default of 1
 */
public class PermanentBlock extends Block{

    private static final String PERMANENT_BLOCK = "block5.gif";

    /**
     * Creates a permanent block that cannot be destroyed and will not be counted when the player is trying to clear the screen
     */
    PermanentBlock() {
        super();
        super.setLives(100000);
        super.setBlockObject(PERMANENT_BLOCK);
    }

}