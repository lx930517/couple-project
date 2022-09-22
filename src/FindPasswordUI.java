import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName : ChangePasswordUI  //类名
 * @Description : 修改密码的界面  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/19  22:49
 */


public class FindPasswordUI {
    public static String saveRandomNum = "";
    private static JFrame jFrame = new JFrame("忘记密码");

    public static void findPasswordUI() {
        //修改密码窗口的参数
        jFrame.setSize(800, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //修改密码的面板
        JPanel jPanel = new JPanel(null);

        //注册邮箱标签
        JLabel emailLabel = new JLabel("修改邮箱:");
        emailLabel.setBounds(150, 50, 80, 50);
        //验证码标签
        JLabel vercodeLabel = new JLabel("验证码:");
        vercodeLabel.setBounds(150, 100, 50, 50);
        //昵称标签
        JLabel nameLabel = new JLabel("您的昵称:");
        nameLabel.setBounds(150, 250, 100, 30);
        //密码标签
        JLabel passwordLabel = new JLabel("新密码:");
        passwordLabel.setBounds(150, 150, 50, 50);
        //校验密码标签
        JLabel checkLabel = new JLabel("请确认新密码:");
        checkLabel.setBounds(150, 200, 100, 50);


        //注册邮箱文本框
        JTextField emailField = new JTextField(20);
        emailField.setBounds(250, 50, 150, 30);
        //验证码文本框,6位数字的验证码
        JTextField vercodeField = new JTextField(6);
        vercodeField.setBounds(250, 100, 80, 30);
        //昵称文本框
        JTextField nameField = new JTextField(10);
        nameField.setBounds(250, 250, 80, 30);
        //新密码文本框
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(250, 150, 80, 30);
        //校验密码文本框
        JPasswordField checkField = new JPasswordField(10);
        checkField.setBounds(250, 200, 80, 30);


        //发送验证码按钮
        JButton emailButton = new JButton("发送验证码");
        emailButton.setBounds(500, 50, 150, 30);
        //确认修改按钮
        JButton confirmButton = new JButton("确认修改");
        confirmButton.setBounds(250, 300, 150, 30);

        //将Components添加到panel中
        jPanel.add(emailLabel);
        jPanel.add(vercodeLabel);
        jPanel.add(nameLabel);
        jPanel.add(passwordLabel);
        jPanel.add(checkLabel);
        jPanel.add(emailField);
        jPanel.add(vercodeField);
        jPanel.add(nameField);
        jPanel.add(passwordField);
        jPanel.add(checkField);
        jPanel.add(emailButton);
        jPanel.add(confirmButton);

        jFrame.add(jPanel);
        jFrame.setVisible(true);

        // 按下发送验证码按钮的响应事件
        class emailButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkCode = Main.makeRandomNum();
                saveRandomNum = checkCode;
                try {
                    RegisterUI.sendEmail(checkCode, emailField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "请输入正确的邮箱！", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        // 按下点击确认修改按钮，如果验证码不规范或者密码不规范，用户重新进行校验；如果都符合规范，则表示修改成功，根据昵称找到对应的密码，然后将密码修改
        class confirmButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyChange(vercodeField.getText(), passwordField.getPassword(), checkField.getPassword());
                DatabaseOperation.modifyPassword(nameField.getText(),passwordField.getPassword());
                jFrame.setVisible(false);
                InitUI.initUI();
            }
        }
        //将确认修改按钮添加到事件监听
        confirmButton.addActionListener(new confirmButtonHandler());
        //将发送验证码按钮加入到事件监听
        emailButton.addActionListener(new emailButtonHandler());
    }

    public static void verifyChange(String verifyCode, char[] password, char[] checkPassword) {
        // 获取传进来的参数
        String getCheckCode = verifyCode;
        char[] getPasswordCh = password;
        char[] getCheckPwdCh = checkPassword;
        String getPassword = String.valueOf(getPasswordCh);
        String getCheckPwd = String.valueOf(getCheckPwdCh);

        //下面进行逻辑判断
        if ((getCheckCode.length() == 0) || (getPassword.length() == 0) || (getCheckPwd.length() == 0)) {
            JOptionPane.showMessageDialog(null, "请填写内容！", "警告", JOptionPane.PLAIN_MESSAGE);
        } else {
            if (getCheckCode.equals(saveRandomNum) && getPassword.equals(getCheckPwd)) {
                if (getPassword.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9#?!@$%^&*-.]+$)" +
                        "(?![A-Za-z#?!@$%^&*-.]+$)(?![A-Z0-9#?!@$%^&*-.]+$)[a-zA-Z0-9#?!@$%^&*-.]{6,10}$")) {
                    JOptionPane.showMessageDialog(null, "修改成功！", "", JOptionPane.PLAIN_MESSAGE);
                    jFrame.setVisible(false);
                    InitUI.initUI();
                } else {
                    JOptionPane.showMessageDialog(null, "密码格式不正确！", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "验证码或密码有误！", "警告", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
