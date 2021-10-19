import javax.print.attribute.standard.PrinterURI;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description:
 * 设计模式：状态模式
 *
 * @author maserhe
 * @date 2021/10/15 1:44 下午
 **/
public interface State {

    /**
     * 返回需要执行的 状态
     * @param context
     * @return
     */
    State action(Context context);

}

class Context {

    State state;

    /**
     *  绘图 使用的
     */
    Graphics og;

    /**
     *  原点位置x
     */
    static int originX;

    /**
     *  原点位置Y
     */
    static int originY;

    public Context() {
    }

    public Context(Graphics og) {
        this.og = og;
    }

    /**
     * 当前状态 执行，并返回下一个状态
     * @return
     */
    public State action() {
        return state.action(this);
    }

    public State setState(State state) {
        this.state = state;
        return state;
    }

    public void setOg(Graphics2D og) {
        this.og = og;
    }

    public State getState() {
        return state;
    }
}


class StartState implements State {

    @Override
    public State action(Context context) {
        System.out.println("双击进入开始状态");
        // 绘制 缩放的 图形。 以及图标
        Context.originX = PaintUtils.getX();
        Context.originY = PaintUtils.getY();
        PaintUtils.paintNoFillRect(Context.originX, Context.originY, (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og);

        // 绘制图标
        PaintUtils.paintIconsForSmall((Graphics2D) context.og);
        System.out.println("开始状态结束");
        return new ClickState();
    }

}

class ClickState implements State {

    @Override
    public State action(Context context) {

        System.out.println("由开始状态进入选择状态");
        // 选择小型图标
        // 1， 获取点击位置
        final int x = PaintUtils.x;
        final int y = PaintUtils.y;
        // 2， 判断点击位置是否在 小范围的框内
        final List<Icon> relativeIcons = PaintUtils.getRelativeIcons();
        final List<Icon> icons = relativeIcons.stream().filter(t -> PaintUtils.isInIcon(x, y, t)).collect(Collectors.toList());

        if (icons != null && icons.size() > 0) {
            System.out.println("点击了一个图标");
            // 进入放大状态, 找出需要
            Icon icon = icons.get(0);
            // 绘制图形
            // 1， 获取原点 可能为负数
            int t_x = icon.x + icon.width / 2 - (int)(StartUi.MAX_WIDTH / 4);
            int t_y = icon.y + icon.height / 2 - (int) (StartUi.MAX_HEIGHT/ 4);

            // 2，检测在 放大后的图标在 放大范围内的 图标
            PaintUtils.printRect(t_x, t_y, (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og, Color.CYAN);

            // 放大操作
            final Double magnification = Double.valueOf(StartUi.MAGNIFICATION);
            System.out.println("放大的倍数" + magnification);
            // 以当前目标为坐标计算需要平移的大小 ，然后放大。
            // 4, 平移的 大小
            int pin_x = (int)(t_x + StartUi.MAX_WIDTH / 4) - icon.x;
            int pin_y = (int)(t_y + StartUi.MAX_HEIGHT / 4) - icon.y;


            final int startX = t_x;
            final int startY = t_y;

            System.out.println("获取 放大图标");
            List<Icon> enlargeIcons = PaintUtils.getEnlargeIcons(relativeIcons, pin_x, pin_y, magnification);
            enlargeIcons.stream().filter(t-> {
                boolean flag1 = t.x >= t_x && t.x  + t.width <= t_x + (int) (StartUi.MAX_WIDTH / 2);
                boolean flag2 = t.y >= t_y && t.y  + t.height <= t_y + (int) (StartUi.MAX_HEIGHT/ 2);
                return flag1 && flag2;
            }).forEach(t->PaintUtils.paintIcon(t, (Graphics2D) context.og));

            // enlargeIcons.stream().forEach(t->PaintUtils.paintIcon(t, (Graphics2D) context.og));
            List<Icon> collect = relativeIcons.stream().filter(t -> PaintUtils.isInEnlarge(pin_x, pin_y, t, startX, startY)).collect(Collectors.toList());


            return new EnlargeState();
        } else {
            System.out.println("没有点中小图标");
            // 需要重新绘制
            // 绘制 缩放的 图形。 以及图标
            PaintUtils.paintNoFillRect(Context.originX, Context.originY, (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og);
            // 绘制图标
            PaintUtils.paintIconsForSmall((Graphics2D) context.og);
            // 再次进入当前状态
            return this;
        }
    }

}

class EnlargeState implements State {

    @Override
    public State action(Context context) {
        System.out.println("由选择状态进入 放大状态");
        // 进入放大状态 进行选择


        return this;
    }

}

class EnlargeClickState implements State {

    @Override
    public State action(Context context) {
        System.out.println("进入当大 点击状态");
        return this;
    }
}