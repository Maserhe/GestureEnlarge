import java.awt.*;

/**
 * Description:
 *     图标的位置信息
 *
 * @author maserhe
 * @date 2021/10/14 8:27 下午
 **/
public class Icon {

    int x;
    int y;
    Color color;

    int width;
    int height;

    public Icon() {
        x = 0;
        y = 0;
        width = 50;
        height = 50;
        color = Color.cyan;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public Icon(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
