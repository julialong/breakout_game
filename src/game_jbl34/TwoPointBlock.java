package game_jbl34;


public class TwoPointBlock extends Block{

    private static final String POINT_BLOCK = "block3.gif";

    TwoPointBlock() {
        super();
        super.setPoints(2);
        super.setBlockObject(POINT_BLOCK);
    }
}
