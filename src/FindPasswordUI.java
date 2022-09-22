import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        // 获取窗口的宽度和长度，为组件自适应窗口大小做准备
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();
        //注册邮箱标签
        JLabel emailLabel = new JLabel("注册邮箱:");
        emailLabel.setBounds(width / 4, height / 8, width / 10, height / 8);
        emailLabel.setFont(new Font("楷体", Font.ITALIC, 17));
        //验证码标签
        JLabel vercodeLabel = new JLabel("验证码:");
        vercodeLabel.setBounds(width / 4, height / 8 + height / 10, width / 10, height / 8);
        vercodeLabel.setFont(new Font("楷体", Font.ITALIC, 17));
        //昵称标签
        JLabel nameLabel = new JLabel("昵称:");
        nameLabel.setBounds(width / 4, height / 8 + (height / 10) * 2, width / 10, height / 8);
        nameLabel.setFont(new Font("楷体", Font.ITALIC, 17));
        //密码标签
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(width / 4, height / 8 + (height / 10) * 3, width / 10, height / 8);
        passwordLabel.setFont(new Font("楷体", Font.ITALIC, 17));
        //校验密码标签
        JLabel checkLabel = new JLabel("请再次输入密码:");
        checkLabel.setBounds(width / 6, height / 8 + (height / 10) * 4, width / 2, height / 8);
        checkLabel.setFont(new Font("楷体", Font.ITALIC, 17));

        //注册邮箱文本框
        JTextField emailField = new JTextField(20);
        emailField.setBounds(width / 2 - width / 8, height / 6 - height / 50, width / 5, height / 15);
        //验证码文本框,6位数字的验证码
        JTextField vercodeField = new JTextField(6);
        vercodeField.setBounds(width / 2 - width / 8, height / 6 - height / 50 + height / 10, width / 5, height / 15);
        //昵称文本框
        JTextField nameField = new JTextField(10);
        nameField.setBounds(width / 2 - width / 8, height / 6 - height / 50 + (height / 10) * 2, width / 5, height / 15);
        //密码文本框
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(width / 2 - width / 8, height / 6 - height / 50 + (height / 10) * 3, width / 5, height / 15);
        //校验密码文本框
        JPasswordField checkField = new JPasswordField(10);
        checkField.setBounds(width / 2 - width / 8, height / 6 - height / 50 + (height / 10) * 4, width / 5, height / 15);

        //发送验证码按钮
        JButton emailButton = new JButton("发送验证码");
        emailButton.setBounds(width / 2 + width / 10, height / 6 - height / 50, width / 8, height / 15);
        //确认注册按钮
        JButton confirmButton = new JButton("点击注册");
        confirmButton.setBounds(width / 3 - width / 15, height / 2 + height / 6, width / 4 + width / 6, height / 15);
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
                    JOptionPane.showMessageDialog(null, "请输入正确的邮箱！", "", JOptionPane.PLAIN_MESSAGE);
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

        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange = jFrame.getWidth();
                int heightChange = jFrame.getHeight();
                emailLabel.setBounds(widthChange / 4, heightChange / 8, widthChange / 10, heightChange / 8);
                vercodeLabel.setBounds(widthChange / 4, heightChange / 8 + heightChange / 10, widthChange / 10, heightChange / 8);
                nameLabel.setBounds(widthChange / 4, heightChange / 8+(heightChange/10)*2, widthChange / 10, heightChange / 8);
                passwordLabel.setBounds(widthChange / 4, heightChange / 8 + (heightChange / 10)*3, widthChange / 10, heightChange / 8);
                checkLabel.setBounds(widthChange / 6, heightChange / 8+ (heightChange / 10)*4, widthChange / 2, heightChange / 8);

                emailField.setBounds(widthChange / 2 -widthChange/8, heightChange / 6-heightChange/50, widthChange / 5, heightChange / 15);
                vercodeField.setBounds(widthChange / 2 -widthChange/8, heightChange / 6-heightChange/50+heightChange/10, widthChange / 5, heightChange / 15);
                nameField.setBounds(widthChange / 2 -widthChange/8, heightChange / 6-heightChange/50+(heightChange/10)*2,widthChange / 5, heightChange / 15);
                passwordField.setBounds(widthChange / 2 -widthChange/8, heightChange / 6-heightChange/50+(heightChange/10)*3, widthChange / 5, heightChange / 15);
                checkField.setBounds(widthChange / 2 -widthChange/8, heightChange / 6-heightChange/50+(heightChange/10)*4, widthChange / 5, heightChange / 15);

                emailButton.setBounds(widthChange / 2 + widthChange / 10, heightChange / 6 - heightChange / 50, widthChange / 8, heightChange / 15);
                confirmButton.setBounds(widthChange / 3 - widthChange / 15, heightChange / 2 + heightChange / 6, widthChange / 4 + widthChange / 6, heightChange / 15);
            }

        });
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
            JOptionPane.showMessageDialog(null, "请填写内容！", "", JOptionPane.PLAIN_MESSAGE);
        } else {
            if (getCheckCode.equals(saveRandomNum) && getPassword.equals(getCheckPwd)) {
                if (getPassword.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9#?!@$%^&*-.]+$)" +
                        "(?![A-Za-z#?!@$%^&*-.]+$)(?![A-Z0-9#?!@$%^&*-.]+$)[a-zA-Z0-9#?!@$%^&*-.]{6,10}$")) {
                    JOptionPane.showMessageDialog(null, "", "", JOptionPane.PLAIN_MESSAGE);
                    jFrame.setVisible(false);
                    InitUI.initUI();
                } else {
                    JOptionPane.showMessageDialog(null, "密码格式不正确！", "", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "验证码或密码有误！", "", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
