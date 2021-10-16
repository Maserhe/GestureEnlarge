import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * 绘图的工具类
 *
 * @author maserhe
 * @date 2021/10/14 8:12 下午
 **/
public class PaintUtils {

    public static int width = 60;

    public static int height = 60;

    public static List<Icon> icons;

    public static int x = 0;

    public static int y = 0;

    /**
     * 绘制 图形
     * @param x
     * @param y
     * @param graph2D
     */
    public static void printRect(int x, int y, int width, int height, Graphics2D graph2D, Color color) {

        Rectangle rectangle = new Rectangle(x ,y , width, height);
        graph2D.setColor(color);
        graph2D.fill(rectangle);
        graph2D.setColor(Color.black);

    }
    /**
     * 绘制 图形
     * @param x
     * @param y
     * @param graph2D
     */
    public static void printRect(int x, int y, Graphics2D graph2D, Color color) {
        printRect(x, y, width, height, graph2D, color);
    }

    /**
     *  随机颜色
     * @param x
     * @param y
     * @param graph2D
     */
    public static void printRect(int x, int y, Graphics2D graph2D) {
        // 获取随机的颜色
        printRect(x, y, graph2D, randomColor());
    }

    /**
     * 获取随机颜色
     * @return
     */
    public static Color randomColor() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        String haxString;
        for (int i = 0; i < 3; i ++ ) {
            haxString = Integer.toHexString(random.nextInt(0XFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }
            buffer.append(haxString);
        }
        return Color.decode("#" + buffer.toString());
    }

    /**
     * 绘制图标
     * @param icon
     * @param graph2D
     */
    public static void paintIcon(Icon icon, Graphics2D graph2D) {
        printRect(icon.x, icon.y, icon.width, icon.height, graph2D, icon.color);
    }


    /**
     * 判断 两个图标 是否冲突
     * @param ic1
     * @param ic2
     * @return
     */
    public static boolean isConflict(Icon ic1, Icon ic2) {
        boolean flag1 = Math.max(ic1.x, ic2.x) <= Math.min(ic1.x + ic1.width, ic2.x + ic2.width);
        boolean flag2 = Math.max(ic1.y, ic2.y) <= Math.min(ic1.y + ic1.height, ic2.y + ic2.height);
        return flag1 && flag2;
    }

    /**
     * 获取 随机的 图标
     * @return
     */
    public static Icon randomIcon() {
        Icon icon = new Icon();
        icon.width = Integer.valueOf(StartUi.WIDTH);
        icon.height = Integer.valueOf(StartUi.HEIGHT);

        // 产生随机 数字
        icon.x = RandomUtils.nextInt(0, StartUi.MAX_WIDTH.intValue() - icon.width);
        icon.y = RandomUtils.nextInt(0, StartUi.MAX_HEIGHT.intValue() - 2 * icon.height);

        icon.color = randomColor();
        return icon;
    }

    /**
     * 初始化 图标的 数据
     */
    public static void init() {
        icons = new ArrayList<>();
        final int num = Integer.valueOf(StartUi.OCCURRENCE_NUMBER);
        for (int i = 0; i < num; i ++ ) {
            boolean flag = true;
            Icon icon = null;

            while (flag) {
                final Icon finalIcon = randomIcon();
                icon = finalIcon;
                flag  = icons.stream().anyMatch(t->isConflict(t, finalIcon));
            }
            icons.add(icon);
        }

    }

    /**
     *  将列表中的 图标全部绘制
     * @param graph2D
     */
    public static void paintIcons(Graphics2D graph2D) {
        icons.stream().forEach(t->{
            paintIcon(t,graph2D);
        });
    }

    /**
     * 获取缩小版 的 图标绘制
     * @param graphics2D
     */
    public static void paintIconsForSmall(Graphics2D graphics2D) {
        icons.stream().forEach(t->{
            paintIcon(getRelativeIcon(t), graphics2D);
        });
    }


    /**
     * 获取相对图标
     * @return
     */
    public static Icon getRelativeIcon(Icon icon) {

        Icon ans = new Icon();
        ans.x = icon.x / 2 + getX();
        ans.y = icon.y / 2 + getY();

        ans.width = icon.width / 2;
        ans.height = icon.height / 2;
        ans.color = icon.color;

        return ans;
    }


    /**
     * 获取 x 的原点位置
     * @return
     */
    public static int getX() {
        int temp = x;
        temp -= StartUi.MAX_WIDTH / 4;
        int ans = temp > 0 ? temp: 0;
        temp += StartUi.MAX_WIDTH / 2;
        if (temp > StartUi.MAX_WIDTH) {
            ans = (int) (StartUi.MAX_WIDTH / 2);
        }
        return ans;
    }

    /**
     * 获取 y
     * @return
     */
    public static int getY() {
        int temp = y;
        temp -= (StartUi.MAX_HEIGHT / 2 + 30);
        int ans = temp > 0 ? temp:0;
        return ans;
    }

    /**
     * 绘制 长方形
     * @param x
     * @param y
     * @param width
     * @param height
     * @param graph2D
     */
    public static void paintNoFillRect(int x, int y, int width, int height, Graphics2D graph2D) {
        Rectangle rectangle = new Rectangle(x ,y , width, height);
        graph2D.setColor(Color.cyan);
        graph2D.fill(rectangle);
        graph2D.setStroke(new BasicStroke(1f));
        graph2D.setColor(Color.black);
        graph2D.drawRect(x, y, width, height);

    }
}
