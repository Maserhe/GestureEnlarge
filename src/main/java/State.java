import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    /**
     *  缩小后的 icon
     */
    static Icon smallIcon;

    static int number;

    /**
     * 数据 记录
     */
    static int SMALL_X;

    static int SMALL_Y;

    static Date SMALL_START_TIME;

    static Date SMALL_STOP_TIME;

    static int SMALL_CENTER_X;

    static int SMALL_CENTER_Y;

    static int BIG_X;

    static int BIG_Y;

    static int BIG_CENTER_X;

    static int BIG_CENTER_Y;

    static Date BIG_START_TIME;

    static Date BIG_STOP_TIME;

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
        // 当前目录 System.out.println(System.getProperty("user.dir"));

        System.out.println("双击进入开始状态");
        // 绘制 缩放的 图形。 以及图标
        Context.originX = PaintUtils.getX();
        Context.originY = PaintUtils.getY();
        PaintUtils.paintNoFillRect(Context.originX, Context.originY, (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og);
        // 绘制图标
        PaintUtils.paintIconsForSmall((Graphics2D) context.og);
        Context.SMALL_START_TIME = new Date();
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
        // 记录的点 点击的点

        final int x = PaintUtils.x;
        final int y = PaintUtils.y;

        // 2， 判断点击位置是否在 小范围的框内
        final List<Icon> relativeIcons = PaintUtils.getRelativeIcons();
        final List<Icon> icons = relativeIcons.stream().filter(t -> PaintUtils.isInIcon(x, y, t)).collect(Collectors.toList());

        if (icons != null && icons.size() > 0) {
            System.out.println("点击了一个图标");
            // 进入放大状态, 找出需要
            Icon icon = icons.get(0);

            Context.SMALL_X = x;
            Context.SMALL_Y = y;
            Context.SMALL_STOP_TIME = new Date();
            Context.SMALL_CENTER_X = icon.x + icon.width / 2;
            Context.SMALL_CENTER_Y = icon.y + icon.height / 2;

            Context.smallIcon = icon;
            // 绘制图形
            PaintUtils.paintEnlargeIcon(icon, context);
            Context.BIG_START_TIME = new Date();

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
        final int x = PaintUtils.x;
        final int y = PaintUtils.y;

        // 1, 判断是否点中目标
        final Double magnification = Double.valueOf(StartUi.MAGNIFICATION);

        // 3, 平移 到原点
        int pinX = Context.originX + (int) ( StartUi.MAX_WIDTH / 4 )- (int) ((Context.smallIcon.x - Context.originX) * 2 * magnification);
        int pinY = Context.originY + (int) (StartUi.MAX_HEIGHT / 4)- (int) ((Context.smallIcon.y - Context.originY)  * 2 * magnification);

        Icon icon = PaintUtils.getMagnificationIcon(Context.smallIcon, magnification, pinX, pinY);


        boolean flag1 = x >= icon.x && y >= icon.y;
        boolean flag2 = x <= icon.x + icon.width && y <= icon.y + icon.height;

        // 2， 点中目标
        if (flag1 && flag2) {
            System.out.println("点中目标");
            Context.BIG_X = x;
            Context.BIG_Y = y;
            Context.BIG_STOP_TIME = new Date();
            Context.BIG_CENTER_X = icon.x + icon.width / 2;
            Context.BIG_CENTER_Y = icon.y + icon.height / 2;

            String[] headers = {"small_x", "small_y", "small_center_x", "small_center_y",
                    "small_distance", "small_start_time", "small_stop_time", "time_abs",
                    "big_x", "big_y", "big_center_x", "big_center_y", "big_start_time", "big_stop_time" };

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS");

            Double smallDistance = Math.sqrt( Math.pow(Context.SMALL_X - Context.SMALL_CENTER_X, 2) + Math.pow(Context.SMALL_Y - Context.SMALL_CENTER_Y, 2));
            Double bigDistance = Math.sqrt( Math.pow(Context.BIG_X - Context.BIG_CENTER_X, 2) + Math.pow(Context.BIG_Y - Context.BIG_CENTER_Y, 2));

            /**
                    "small_x", "small_y",
                    "small_center_x", "small_center_y",
                    "small_distance", "small_start_time",
                    "small_stop_time", "small_time_interval",
                    "big_x", "big_y",
                    "big_center_x", "big_center_y",
                    "big_distance", "big_start_time",
                    "big_stop_time", "big_time_interval"
            */

            PaintUtils.init();
            String[] data = {
                String.valueOf(Context.SMALL_X), String.valueOf(Context.SMALL_Y), String.valueOf(Context.SMALL_CENTER_X), String.valueOf(Context.SMALL_CENTER_Y),
                String.valueOf(smallDistance), simpleDateFormat.format(Context.SMALL_START_TIME), simpleDateFormat.format(Context.SMALL_STOP_TIME), String.valueOf(Context.SMALL_STOP_TIME.getTime() - Context.SMALL_START_TIME.getTime()),
                String.valueOf(Context.BIG_X), String.valueOf(Context.BIG_Y), String.valueOf(Context.BIG_CENTER_X), String.valueOf(Context.BIG_CENTER_Y),
                String.valueOf(bigDistance), simpleDateFormat.format(Context.BIG_START_TIME), simpleDateFormat.format(Context.BIG_STOP_TIME), String.valueOf(Context.BIG_STOP_TIME.getTime() - Context.BIG_START_TIME.getTime())
            };
            PaintUtils.writeFileToCsv(data, PaintUtils.getFilePath());
            Context.number ++ ;
            return new StartState();
        } else {
            PaintUtils.paintEnlargeIcon(Context.smallIcon, context);
            System.out.println("未点中目标");
            return new EnlargeState();
        }
    }
}

class EnlargeClickState implements State {

    @Override
    public State action(Context context) {
        // 进入刷新状态
        System.out.println("进入当大 点击状态");
        return this;
    }
}

