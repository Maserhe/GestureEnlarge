import java.awt.*;

/**
 * Description:
 * 设计模式：状态模式
 *
 * @author maserhe
 * @date 2021/10/15 1:44 下午
 **/
public interface State {

    /**
     * 当前状态 执行的动作
     * @param context  when value == 1: 单击 , value == 2 双击
     */
    void action(Context context);

}

class Context {

    State state;

    /**
     *  绘图 使用的
     */
    Graphics og;

    public Context() {
    }

    public Context(Graphics og) {
        this.og = og;
    }

    public void action() {
        state.action(this);
    }

    public State setState(State state) {
        this.state = state;
        return state;
    }

    public State getState() {
        return state;
    }
}


class StartState implements State {

    @Override
    public void action(Context context) {
        System.out.println("双击进入开始状态");
        // 绘制 缩放的 图形。 以及图标
        PaintUtils.paintNoFillRect(PaintUtils.getX(), PaintUtils.getY(), (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og);
        // 绘制图标
        PaintUtils.paintIconsForSmall((Graphics2D) context.og);

    }

}

class ClickState implements State {

    @Override
    public void action(Context context) {
        System.out.println("由开始状态进入选择状态");
        // 选择小型图标


    }

}

class EnlargeState implements State {

    @Override
    public void action(Context context) {
        System.out.println("由选择状态进入 放大状态");
        // 进入放大状态 进行选择
        final Double magnification = Double.valueOf(StartUi.MAGNIFICATION);




    }

}

class EnlargeClickState implements State {

    @Override
    public void action(Context context) {
        System.out.println("进入当大 点击状态");


    }
}