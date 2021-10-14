import javax.swing.*;
import java.awt.*;

/**
 * Description:
 * 实验操作面板
 *
 * @author maserhe
 * @date 2021/10/14 3:59 下午
 **/
public class OperationPanel extends JPanel {

    Graphics og;

    /**
     * 图像缓冲
     */
    Image image;

    public OperationPanel() {
        // 添加监听



    }

    /**
     * 绘图 函数
     */
    public void paintComponent() {
        if (og == null) {
            // 创建一个和 JPanel 一样的 图形缓冲。
            image = this.createImage(this.getWidth(),this.getHeight());
            if (image != null) {
                og = image.getGraphics();
            }
        }

        if (og != null) {
            //调用父类的  paint 会刷新屏幕。
            super.paint(og);
            // 画笔加粗,向下强制转型。
            Graphics2D graph2D = (Graphics2D) og;
            // 开始画图。
            // 先把 当前画笔里面的 先画出来。

            PaintUtils.paintIcons(graph2D);

        }

        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 进行2D绘图
        g.drawImage(image, 0, 0, this);
    }




}
