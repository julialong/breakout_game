package game_jbl34;

public class TimeBlock extends Block{

    private double time;

    private static final String TIME_BLOCK = "block4.gif";

    TimeBlock() {
        super();
        super.setBlockObject(TIME_BLOCK);
    }

    @Override
    public void updateTime(double timePassed) {
        this.time += timePassed;
    }

    @Override
    public Boolean isTimed(){
        return true;
    }
}
