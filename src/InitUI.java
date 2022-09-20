/**
 * @ClassName : Register  //类名
 * @Description : This is used to register users,users are stored in the datebase  //描述
 * @Author : Chang,Liu //作者
 * @Date: 2022/9/17  23:22
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitUI {
     public static JFrame jFrame = new JFrame("欢迎使用小初高数学学习软件！");
     
    public static void initUI() {
        jFrame.setSize(647, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel initPanel = new JPanel(null);

        //账号文本框
        JTextField userField = new JTextField(20);
        userField.setBounds(200, 100, 200, 35);
        //密码文本框
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(200, 150, 200, 35);
        //用户名标签
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(100, 100, 90, 50);
        userLabel.setFont(new Font("宋体", Font.ITALIC, 20));
        //密码标签
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(100, 150, 70, 50);
        passwordLabel.setFont(new Font("宋体", Font.ITALIC, 20));

        //注册账号按钮
        JButton btnReg = new JButton("注册账号");
        btnReg.setBounds(150, 250, 100, 50);
        //登录账号按钮
        JButton btnLog = new JButton("登录账号");
        btnLog.setBounds(250, 250, 100, 50);
        //修改密码按钮
        JButton btnChange = new JButton("修改密码");
        btnChange.setBounds(350, 250, 100, 50);
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

        //点击注册按钮时的相应事件
        class btnRegHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                RegisterUI.registerUI();
            }
        }
        //点击登陆按钮时的相应事件
        class btnLogHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ifLogInFlag;
                ifLogInFlag = DatabaseOperation.checkUserExisted(userField.getText(),passwordField.getPassword());
                if(ifLogInFlag) {
                    jFrame.setVisible(false);
                    SelectUI.selectUI(userField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "用户不存在!", "WRONG", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        //点击找回密码按钮时的相应事件
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
    }
}
