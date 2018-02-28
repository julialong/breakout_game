package game_jbl34;

/**
 * Basic Block does not change much from the superclass; simply specifies its image file
 */
public class BasicBlock extends Block{


    private static final String BASIC_BLOCK = "basic_brick.gif";

    /**
     * Creates a basic block that has one point and will be destroyed in one hit
     */
    BasicBlock() {
        super();
        super.setBlockObject(BASIC_BLOCK);
    }
}