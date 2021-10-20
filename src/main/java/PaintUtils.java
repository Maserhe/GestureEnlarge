import com.csvreader.CsvWriter;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ans.x = icon.x / 2 + Context.originX;
        ans.y = icon.y / 2 + Context.originY;

        ans.width = icon.width / 2;
        ans.height = icon.height / 2;
        ans.color = icon.color;

        return ans;
    }

    /**
     * 获取所有的相对图标
     * @return
     */
    public static List<Icon> getRelativeIcons() {
        final List<Icon> icons = PaintUtils.icons.stream().map(t -> getRelativeIcon(t)).collect(Collectors.toList());
        return icons;
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

    /**
     * 判断 坐标是否在 图标内部
     * @param x
     * @param y
     * @return
     */
    public static boolean isInIcon(int x, int y, Icon icon) {
        final boolean flag1 = x <= icon.x + width && x >= icon.x;
        final boolean flag2 = y <= icon.y + height && y >= icon.y;
        return flag1 && flag2;
    }

    /**
     *  平移后 是否在 放大的 范围内
     * @return
     */
    public static boolean isInEnlarge(int pinX, int pinY, Icon icon, int startX, int startY) {
        boolean flag1 = icon.x + pinX >= startX || icon.x + pinX + icon.width <= startX + (int) (StartUi.MAX_WIDTH / 2);
        boolean flag2 = icon.y + pinY >= startY || icon.y + pinY + icon.height <= startY + (int) (StartUi.MAX_HEIGHT/ 2);
        return flag1 && flag2;
    }


    /**
     *
     * @param icons
     * @param pinX
     * @param pinY
     * @param magnification
     * @return
     */
    public static List<Icon> getEnlargeIcons(List<Icon> icons, int pinX, int pinY, double magnification) {

        List<Icon> ans = icons.stream().map(t -> {
            Icon icon = new Icon();
            // 1,  先平移
            icon.x = t.x + pinX;
            icon.y = t.y + pinY;
            icon.width = t.width;
            icon.height = t.height;
            icon.color = t.color;
            // 2， 放大
            return icon;
        }).collect(Collectors.toList());
        return ans;
    }

    /**
     * 将图标进行 放大
     * @param magnification
     * @return
     */
    public static List<Icon> enlargeIcons(double magnification) {
        List<Icon> ans = icons.stream().map(t -> {
            Icon icon = new Icon();
            icon.x = (int) (t.x * magnification);
            icon.y = (int)(t.y * magnification);
            icon.width = (int) (t.width * magnification);
            icon.height = (int) (t.height * magnification);
            icon.color = t.color;
            // 2， 放大
            return icon;
        }).collect(Collectors.toList());
        return ans;
    }

    /**
     * 平移后还需要绘制的 图形
     * @param icons
     * @param pinX
     * @param pinY
     * @return
     */
    public static List<Icon> getInEnlarge(List<Icon> icons, int pinX, int pinY) {

        return icons.stream().map(t -> {
            Icon icon = new Icon();
            icon.width = t.width;
            icon.height = t.height;
            icon.x = t.x + pinX - t.width / 2;
            icon.y = t.y + pinY - t.height / 2;
            icon.color = t.color;
            return icon;

        }).collect(Collectors.toList()).stream().filter(icon -> {

            boolean flag1 = icon.x >= Context.originX && icon.y >= Context.originY;
            boolean flag2 = icon.x + icon.width <= Context.originX + (int) (StartUi.MAX_WIDTH / 2) && icon.y + icon.height <= Context.originY + (int) (StartUi.MAX_HEIGHT / 2);
            return flag1 && flag2;

        }).collect(Collectors.toList());
    }


    /****
     * @comments 写CSV文件
     * @param str
     * @param file
     */
    public static void writeFileToCsv(String[] str, String file) {
        File f = new File(file);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
            CsvWriter cwriter = new CsvWriter(writer,',');
            cwriter.writeRecord(str,false);
            cwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入信息获取 文件的路径
     */
    public static String getFilePath() {

        System.out.println(System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "/data/" + StartUi.USER_NAME;

        File f = new File(path);

        if (!f.exists()) {
            f.mkdirs();
        }

        File csvFile = new File(path + "/实验" + StartUi.BLOCK_NUMBER +".csv");
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                // 写表头
                String[] headers = {
                        "small_x", "small_y",
                        "small_center_x", "small_center_y",
                        "small_distance", "small_start_time",
                        "small_stop_time", "small_time_interval",
                        "big_x", "big_y",
                        "big_center_x", "big_center_y",
                        "big_distance", "big_start_time",
                        "big_stop_time", "big_time_interval"
                };

                writeFileToCsv(headers, csvFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return csvFile.getPath();
    }

    /**
     * 绘制放大图形
     * @param icon
     * @param context
     */
    public static void paintEnlargeIcon(Icon icon, Context context) {
        // 1， 获取原点 可能为负数
        PaintUtils.paintNoFillRect(Context.originX, Context.originY, (int) (StartUi.MAX_WIDTH / 2 ), (int) (StartUi.MAX_HEIGHT/ 2 ), (Graphics2D) context.og);
        // 2， 先放大， 在平移
        final Double magnification = Double.valueOf(StartUi.MAGNIFICATION);
        List<Icon> enlargeIcons = PaintUtils.enlargeIcons(magnification);

        // 3, 平移 到原点
        int pinX = Context.originX + (int) ( StartUi.MAX_WIDTH / 4 )- (int) ((icon.x - Context.originX) * 2 * magnification);
        int pinY = Context.originY + (int) (StartUi.MAX_HEIGHT / 4)- (int) ((icon.y - Context.originY)  * 2 * magnification);
        PaintUtils.getInEnlarge(enlargeIcons, pinX, pinY).stream().forEach(t->PaintUtils.paintIcon(t, (Graphics2D) context.og));
    }


    public static Icon getMagnificationIcon(Icon icon, double magnification, int pinX, int pinY) {
        Icon ans  = new Icon();
        ans.x = (int) ((icon.x - Context.originX) * magnification * 2);
        ans.y = (int) ((icon.y - Context.originY) * magnification * 2 );
        ans.color = icon.color;
        ans.width = (int) (icon.width * magnification * 2);
        ans.height = (int) (icon.height * magnification * 2);

        ans.x = ans.x + pinX - ans.width / 2;
        ans.y = ans.y + pinY - ans.height / 2;
        return ans;
    }
}
