import sun.jvm.hotspot.types.JIntField;
import sun.jvm.hotspot.types.basic.BasicJIntField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description:
 *      启动的类
 * @author maserhe
 * @date 2021/10/14 3:35 下午
 **/
public class StartUi extends JFrame {

    public static void main(String[] args) {
        StartUi startUi = new StartUi();
    }

    /**
     * 实验 名
     */
    public static String USER_NAME = "Maserhe";

    /**
     * 实验可点击点 出现 个数  图标数量
     */
    public static String OCCURRENCE_NUMBER = "10";

    /**
     *  放大倍速
     */
    public static String MAGNIFICATION = "0.1";

    /**
     * 实验 次数
     */
    public static String BLOCK_NUMBER = "1";

    /**
     * 图标的 宽度
     */
    public static String WIDTH = "50";

    /**
     * 图标的 高度
     */
    public static String HEIGHT = "50";

    public static Double MAX_HEIGHT;

    public static Double MAX_WIDTH;

    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();

    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();

    private JButton startButton = new JButton("开始");

    private JLabel jlabel1 = new JLabel();
    private JLabel jlabel2 = new JLabel();
    private JLabel jlabel3 = new JLabel();
    private JLabel jlabel4 = new JLabel();

    private JTextField jtext1 = new JTextField(20);
    private JTextField jtext2 = new JTextField(20);
    private JTextField jtext3 = new JTextField(20);
    private JTextField jtext4 = new JTextField(20);


    StartUi(){
        super();
        MAX_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        Container container = this.getContentPane();
        container.setLayout(null);
        setBounds(480, 260, 390, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel0.setBounds(10,50,343,45);
        panel1.setBounds(10, 300, 343, 45);

        panel2.setBounds(10, 110, 343, 45);
        panel3.setBounds(10, 170, 343, 45);
        panel4.setBounds(10, 230, 343, 45);
        panel5.setBounds(10,290, 343,45);



        jlabel1.setText("实验名称:");
        jlabel1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jlabel1.setBounds(20, 60, 135, 25);
        jtext1.setBounds(20, 65, 108, 24);



        jlabel2.setText("图标数量:");
        jlabel2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jlabel2.setBounds(20, 60, 135, 25);

        jtext2.setBounds(20, 65, 108, 24);


        jlabel3.setText("放大倍速:");
        jlabel3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jlabel3.setBounds(20, 60, 135, 25);

        jtext3.setBounds(20, 65, 108, 24);


        jlabel4.setText("实验次数:");
        jlabel4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jlabel4.setBounds(20, 60, 135, 25);

        jtext4.setBounds(20, 65, 108, 24);

        startButton.setBounds(35, 350, 294, 27);

        // 添加控件。
        panel0.add(jlabel1);
        panel0.add(jtext1);
        panel1.add(startButton);

        panel2.add(jlabel2);
        panel2.add(jtext2);

        panel3.add(jlabel3);
        panel3.add(jtext3);

        panel4.add(jlabel4);
        panel4.add(jtext4);


        // 添加面板
        container.add(panel0);
        container.add(panel1);

        container.add(panel2);
        container.add(panel3);
        container.add(panel4);

        setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dialog().start();
            }
        });


        // 默认实验者名
        jtext1.setText(USER_NAME);
        jtext2.setText(String.valueOf(OCCURRENCE_NUMBER));
        jtext3.setText(String.valueOf(MAGNIFICATION));
        jtext4.setText(String.valueOf(BLOCK_NUMBER));

        // 实验者姓名的监听。
        jtext1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                USER_NAME = jtext1.getText().trim();
                System.out.println("实验名称" + USER_NAME);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                USER_NAME = jtext1.getText().trim();
                System.out.println("实验名称：" + USER_NAME);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        jtext2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                OCCURRENCE_NUMBER = jtext2.getText().trim();
                System.out.println("图标出现的个数：" + OCCURRENCE_NUMBER);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                OCCURRENCE_NUMBER = jtext2.getText().trim();
                System.out.println("图标出现的个数：" + OCCURRENCE_NUMBER);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        jtext3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                MAGNIFICATION = jtext3.getText().trim();
                System.out.println("放大的倍数:" + MAGNIFICATION);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                MAGNIFICATION = jtext3.getText().trim();
                System.out.println("放大的倍数:" + MAGNIFICATION);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        jtext4.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                BLOCK_NUMBER = jtext4.getText().trim();
                System.out.println("第" + BLOCK_NUMBER + "次重复实验");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                BLOCK_NUMBER = jtext4.getText().trim();
                System.out.println("第" + BLOCK_NUMBER + "次重复实验");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }


}
