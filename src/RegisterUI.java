import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Random;

/**
 * @ClassName : RegisterUI  //类名
 * @Description : usde to register users and users are stored in datebase  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/18  17:26
 */


public class RegisterUI {
    public static String saveRandomNum="";
    private static JFrame jFrame = new JFrame("注册界面");

    /***
     * @description: 注册界面的UI
     * @param:
     * @return: void
     * @author 15154
     * @date: 2022/9/18 19:18
     */
    public static void registerUI() {
        //注册界面窗口的参数
        jFrame.setSize(647, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //注册界面的面板
        JPanel jPanel = new JPanel(null);

        //注册邮箱标签
        JLabel emailLabel = new JLabel("注册邮箱:");
        emailLabel.setBounds(150, 50, 80, 50);
        //验证码标签
        JLabel vercodeLabel = new JLabel("验证码:");
        vercodeLabel.setBounds(150, 100, 50, 50);
        //昵称标签
        JLabel nameLabel = new JLabel("昵称:");
        nameLabel.setBounds(150, 150, 50, 50);
        //密码标签
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(150, 200, 50, 50);
        //校验密码标签
        JLabel checkLabel = new JLabel("请再次输入密码");
        checkLabel.setBounds(150, 250, 100, 50);

        //注册邮箱文本框
        JTextField emailField = new JTextField(20);
        emailField.setBounds(250, 50, 150, 30);
        //验证码文本框,6位数字的验证码
        JTextField vercodeField = new JTextField(6);
        vercodeField.setBounds(250, 100, 80, 30);
        //昵称文本框
        JTextField nameField = new JTextField(10);
        nameField.setBounds(250, 150, 80, 30);
        //密码文本框
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(250, 200, 80, 30);
        //校验密码文本框
        JPasswordField checkField = new JPasswordField(10);
        checkField.setBounds(250, 250, 80, 30);

        //发送验证码按钮
        JButton emailButton = new JButton("发送验证码");
        emailButton.setBounds(500, 50, 150, 30);
        //确认注册按钮
        JButton confirmButton = new JButton("点击注册");
        confirmButton.setBounds(250,300,150,30);

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
                    sendEmail(checkCode,emailField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "请输入正确的邮箱！", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        //将发送验证码按钮加入到事件监听
        emailButton.addActionListener(new emailButtonHandler());

        // 按下点击注册按钮，如果验证码不规范或者密码不规范，用户重新进行校验；如果都符合规范，则显示注册成功，并将用户添加到数据库中
        class confirmButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyRegister(vercodeField.getText(),nameField.getText(),passwordField.getPassword(),checkField.getPassword());
            }
        }
        confirmButton.addActionListener(new confirmButtonHandler());
    }

    /***
     * @description:  做发送邮件的一些系统配置工作
     * @param: sendEmail
    sendEmailPwd
    title
    content
    toEmailAddress
     * @return: void
     * @author 15154
     * @date: 2022/9/18 19:00
     */
    public static void deployEmail(String sendEmail,String sendEmailPwd,String title,String content,
                                   String toEmailAddress) throws Exception{
        Properties props = new Properties();
        // 开启debug调试，以便在控制台查看
        props.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        // 设置环境信息
        Session session = Session.getInstance(props);
        // 创建邮件对象
        Message msg = new MimeMessage(session);
        // 发送的邮箱地址
        msg.setFrom(new InternetAddress(sendEmail));
        // 设置标题
        msg.setSubject(title);
        // 设置内容
        msg.setContent("【原P科技】"+content+"(QQ邮箱注册验证码)，请尽快完成注册。如非本人操作，请忽略。", "text/html;charset=gbk;");
        Transport transport = session.getTransport();
        // 设置服务器以及账号和密码
        transport.connect("smtp.qq.com", sendEmail, sendEmailPwd);
        // 发送到的邮箱地址
        transport.sendMessage(msg,new Address[]{new InternetAddress(toEmailAddress)});

        if(transport!=null){
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * @description:  实现发送验证码的功能
     * @param: checkCode 6位的验证码
    toEmailAddress 接受验证码信息的邮箱
     * @return: void
     * @author 15154
     * @date: 2022/9/18 20:34
     */
    public static void sendEmail(String checkCode,String toEmailAddress) throws Exception{
        deployEmail("1515479725@qq.com","nhzbjjmnifuxhhfc","Email Message" ,
               checkCode,toEmailAddress);
    }

    /***
     * @description:  校验验证码和密码格式是否正确
     * @param: checkCode
    name
    password
    checkPassword
     * @return: void
     * @author 15154
     * @date: 2022/9/19 15:55
     */
    private static void verifyRegister(String checkCode,String name,char[] password,char[] checkPassword) {
        // 获取传进来的参数
        String getCheckCode = checkCode;
        String getName = name;
        char[] getPasswordCh = password;
        char[] getCheckPwdCh = checkPassword;
        String getPassword = String.valueOf(getPasswordCh);
        String getCheckPwd = String.valueOf(getCheckPwdCh);

        //下面进行逻辑判断
        if ((getCheckCode.length() == 0) || (getName.length() == 0) || (getPassword.length() == 0) ||
                (getCheckPwd.length() == 0)) {
            JOptionPane.showMessageDialog(null, "请填写内容！", "警告", JOptionPane.PLAIN_MESSAGE);
        } else {
            if (getCheckCode.equals(saveRandomNum) && getPassword.equals(getCheckPwd)) {
                if (getPassword.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9#?!@$%^&*-.]+$)" +
                        "(?![A-Za-z#?!@$%^&*-.]+$)(?![A-Z0-9#?!@$%^&*-.]+$)[a-zA-Z0-9#?!@$%^&*-.]{6,10}$")) {
                    JOptionPane.showMessageDialog(null, "注册成功！", "", JOptionPane.PLAIN_MESSAGE);
                    DatabaseOperation.InsertIntoUsers(getName, getPassword);
                } else {
                    JOptionPane.showMessageDialog(null, "密码格式不正确！", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "验证码或密码有误！", "警告", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

}
