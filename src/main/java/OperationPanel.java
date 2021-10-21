import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    /**
     *  点击状态之间的 切换
     */
    Context context;

    private boolean doubleClick = false;

    public OperationPanel() {


        // 添加监听
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int count = e.getClickCount();
                PaintUtils.x = e.getX();
                PaintUtils.y = e.getY();

                switch (count){
                    case 1:
                        doubleClick = false;
                        Timer t = new Timer(300, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                clickAction(doubleClick);
                            }
                        });
                        t.setRepeats(false);
                        t.start();
                        break;
                    case 2:
                        doubleClick = true;
                        break;
                    default:
                        clickAction(true);
                }

            }
        });


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
            PaintUtils.printWord(graph2D);
        }

        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 进行2D绘图
        g.drawImage(image, 0, 0, this);
    }


    private void clickAction(boolean dbClick) {
        if (context == null) {
            context = new Context();
            context.setState(new StartState());
        }

        if (!dbClick) {
            System.out.println("单击");
            if (context.state instanceof ClickState || context.state instanceof EnlargeState || context.state instanceof EnlargeClickState) {
                stateAction();
            } else {
                System.out.println("现在不应该点击");
            }

        } else {
            System.out.println("双击");
            if (context.state instanceof StartState) {
                stateAction();
            } else {
                System.out.println("此时不应该双击");
            }

        }
    }


    /**
     * 给一个状态 并进行执行
     * @param state
     */
    private void stateAction(State state) {
        og = null;
        paintComponent();
        context.setOg((Graphics2D) og);
        context.setState(state);
        context.action();
    }

    /**
     * 执行当前状态
     */
    private void stateAction() {
        og = null;
        paintComponent();
        context.setOg((Graphics2D) og);
        State newState = context.action();

        // 设置下一个状态
        context.setState(newState);
    }
}
