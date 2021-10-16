import javax.swing.*;
import java.awt.*;

/**
 * Description:
 * 实验的对话框
 *
 * @author maserhe
 * @date 2021/10/14 3:54 下午
 **/
public class Dialog extends JFrame {


    OperationPanel optPanel = new OperationPanel();

    public Dialog() throws HeadlessException {

        // 覆盖全屏幕。
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        // 添加画板。
        this.getContentPane().add(optPanel);
        optPanel.setBounds(0, 0, getWidth(), getHeight());
        optPanel.setLayout(null);
        optPanel.setOpaque(false);
        this.setResizable(false);
        setVisible(true);

    }


    public void start() {
        System.out.println("《《《 实验开始 》》》");
        // 准备 数据
        PaintUtils.init();
        // 绘图
        optPanel.paintComponent();
    }
}
