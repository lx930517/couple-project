import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @ClassName : Register  //类名
 * @Description : This is used to register users,users are stored in the datebase  //描述
 * @Author : Chang,Liu //作者
 * @Date: 2022/9/17  23:22
 */
public class InitUI {
     public static JFrame jFrame = new JFrame("欢迎使用小初高数学学习软件！");

     /***
      * @description:  起始界面的UI，用来初始化窗口
      * @param:
      * @return: void
      * @author Chang
      * @date: 2022/9/21 19:16
      */
    public static void initUI() {
        jFrame.setSize(800, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel initPanel = new JPanel(null);
        // 获取jFrame的宽度和高度，便于设置其他组件的尺寸
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();

        //账号文本框
        JTextField userField = new JTextField(20);
        userField.setBounds(width/3, height/4, width/3, height/10);
        //密码文本框
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(width/3, height/4+height/10, width/3, height/10);
        //用户名标签
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(width/5, height/4, width/6, height/8);
        userLabel.setFont(new Font("宋体", Font.ITALIC, 20));
        //密码标签
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(width/5, height/4+height/10, width/6, height/8);
        passwordLabel.setFont(new Font("宋体", Font.ITALIC, 20));

        //注册账号按钮
        JButton btnReg = new JButton("注册账号");
        btnReg.setBounds(width/15, height/2+height/4, width/8, height/10);
        //登录账号按钮
        JButton btnLog = new JButton("登录账号");
        btnLog.setBounds(width/3, height/2, width/3, height/10);
        //修改密码按钮
        JButton btnChange = new JButton("忘记密码");
        btnChange.setBounds(width-width/5, height/2+height/4, width/8, height/10);

        //将Components添加到面板中
        initPanel.add(btnReg);
        initPanel.add(btnLog);
        initPanel.add(btnChange);
        initPanel.add(userField);
        initPanel.add(passwordField);
        initPanel.add(userLabel);
        initPanel.add(passwordLabel);
        //将面板添加到窗口中
        jFrame.setContentPane(initPanel);
        //窗口设置为对用户可见
        jFrame.setVisible(true);

        /***
         * @description:  点击注册按钮时的相应事件，转到注册界面的UI
         * @param:
         * @return: void
         * @author 15154
         * @date: 2022/9/21 19:48
         */
        class btnRegHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                RegisterUI.registerUI();
            }
        }
        /***
         * @description:  点击登录按钮的相应事件：如果在数据库里面搜索用户，用户不存在，则会弹出警告；成功则转到出题界面的UI
         * @param:
         * @return: void
         * @author 15154
         * @date: 2022/9/21 19:49
         */
        class btnLogHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ifLogInFlag;
                ifLogInFlag = DatabaseOperation.checkUserExisted(userField.getText(),passwordField.getPassword());
                if(ifLogInFlag) {
                    jFrame.setVisible(false);
                    SelectUI.selectUI(userField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "用户不存在!", "", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }

        /***
         * @description:  点击找回密码的响应事件，转到找回密码的UI
         * @param:
         * @return: void
         * @author 15154
         * @date: 2022/9/21 19:34
         */
        class btnChangeHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                FindPasswordUI.findPasswordUI();
            }
        }
        //不同按钮添加不同的动作监听者
        btnReg.addActionListener(new btnRegHandler());
        btnLog.addActionListener(new btnLogHandler());
        btnChange.addActionListener(new btnChangeHandler());

        /***
         * @description: 窗口的大小改变后，各个组件的大小会随之改变
         * @param:
         * @return: void
         * @author 15154
         * @date: 2022/9/21 19:38
         */
        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange=jFrame.getWidth();
                int heightChange=jFrame.getHeight();
                userField.setBounds(widthChange/3, heightChange/4, widthChange/3, heightChange/10);
                passwordField.setBounds(widthChange/3, heightChange/4+heightChange/10, widthChange/3, heightChange/10);
                userLabel.setBounds(widthChange/5, heightChange/4, widthChange/6, heightChange/8);
                passwordLabel.setBounds(widthChange/5, heightChange/4+heightChange/10, widthChange/6, heightChange/8);
                btnReg.setBounds(widthChange/15, heightChange/2+heightChange/4, widthChange/8, heightChange/10);
                btnLog.setBounds(widthChange/3, heightChange/2, widthChange/3, heightChange/10);
                btnChange.setBounds(widthChange-widthChange/5, heightChange/2+heightChange/4, widthChange/8, heightChange/10);

            }

        });
    }
}
