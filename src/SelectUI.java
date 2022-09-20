import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName : SelectUI  //类名
 * @Description : 选择出题界面  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/19  22:25
 */


public class SelectUI {
    public static void selectUI(String name) {
        JFrame jFrame = new JFrame("选择出卷难度");
        jFrame.setSize(647, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(null);

        // 创建小学、初中、高中三个单选按钮
        JRadioButton radioButtonPrimary = new JRadioButton("小学");
        JRadioButton radioButtonJunior = new JRadioButton("初中");
        JRadioButton radioButtonHigh = new JRadioButton("高中");
        radioButtonPrimary.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonPrimary.setBounds(150,50,100,100);
        radioButtonJunior.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonJunior.setBounds(250,50,100,100);
        radioButtonHigh.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonHigh.setBounds(350,50,100,100);

        //生成题目数量标签
        JLabel jLabelQuestionNum = new JLabel("请选择成的题目数量(10-30)");
        jLabelQuestionNum.setFont(new Font("楷体",Font.PLAIN,15));
        jLabelQuestionNum.setBounds(220, 150, 200, 50);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonPrimary);
        buttonGroup.add(radioButtonJunior);
        buttonGroup.add(radioButtonHigh);

        //设置文本框，选择出题数目
        JTextField jTextFieldNum = new JTextField(2);
        jTextFieldNum.setBounds(250, 200, 100, 30);
        // 设置重置密码的按钮
        JButton btnReset = new JButton("重置密码");
        btnReset.setBounds(10, 300, 100, 40);
        JButton btnConfirmNum = new JButton("确认生成题目");
        btnConfirmNum.setBounds(200,300,200,30);
        // 添加按钮和面板
        jPanel.add(radioButtonPrimary);
        jPanel.add(radioButtonJunior);
        jPanel.add(radioButtonHigh);
        jPanel.add(btnReset);
        jPanel.add(jLabelQuestionNum);
        jPanel.add(jTextFieldNum);
        jPanel.add(btnConfirmNum);
        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);


        // 将点击注册按钮加入相应的事件监听功能
        class btnResetHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                resetPasswordUI(name);
            }
        }
        btnReset.addActionListener(new btnResetHandler());

        // 将确认生成蒂姆按钮加入相应的事件监听
        class btnConfirmNumHandler implements ActionListener {
            String levelSelected = "";
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jTextFieldNum.getText().matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "请输入正确的数据格式！！", "WARN!", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    int questionNum = Integer.parseInt(jTextFieldNum.getText());
                    if((10<=questionNum)&&(questionNum<=30)){
                        for(Component component : jPanel.getComponents()){
                            if(component instanceof JRadioButton){
                                if(((JRadioButton)component).isSelected()){
                                    levelSelected += ((JRadioButton) component).getText();
                                }
                            }
                        }
                        jFrame.setVisible(false);
                        PaperUI.paperUI(levelSelected);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "请输入正确的题目数量！", "WARN!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        }
        btnConfirmNum.addActionListener(new btnConfirmNumHandler());
    }

    public static void resetPasswordUI(String name) {
        JFrame jFrameReset = new JFrame("重新设置密码");
        jFrameReset.setSize(647, 400);
        jFrameReset.setLocationRelativeTo(null);
        jFrameReset.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(null);

        // 原始密码、新密码、确认新密码提示标签
        JLabel jLabelInitPwd = new JLabel("原密码:");
        JLabel jLabelNewPwd = new JLabel("新密码:");
        JLabel jLabelCheckPwd = new JLabel("确认新密码:");
        jLabelInitPwd.setBounds(150, 50, 70, 30);
        jLabelNewPwd.setBounds(150, 150, 70, 30);
        jLabelCheckPwd.setBounds(150, 250, 70, 30);

        // 原始密码、新密码、确认新密码的密码框
        JPasswordField jPasswordFieldInit = new JPasswordField(10);
        JPasswordField jPasswordFieldNew = new JPasswordField(10);
        JPasswordField jPasswordFieldCheck = new JPasswordField(10);
        jPasswordFieldInit.setBounds(300, 50, 100, 30);
        jPasswordFieldNew.setBounds(300, 150, 100, 30);
        jPasswordFieldCheck.setBounds(300, 250, 100, 30);

        // 确认修改按钮
        JButton btnConfirm = new JButton("确认修改");
        btnConfirm.setBounds(250, 300, 100, 30);

        jPanel.add(jLabelInitPwd);
        jPanel.add(jLabelNewPwd);
        jPanel.add(jLabelCheckPwd);
        jPanel.add(jPasswordFieldInit);
        jPanel.add(jPasswordFieldNew);
        jPanel.add(jPasswordFieldCheck);
        jPanel.add(btnConfirm);
        jFrameReset.add(jPanel);
        jFrameReset.setVisible(true);


        // 点击修改密码按钮后的相应事件
        class btnConfirmHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getPasswordInit = String.valueOf(jPasswordFieldInit.getPassword());
                if(!getPasswordInit.equals(DatabaseOperation.findUserPassword(name))) {
                    JOptionPane.showMessageDialog(null, "原密码输入不正确!", "WRONG", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    if(!checkPasswordFormat(jPasswordFieldNew.getPassword(), jPasswordFieldCheck.getPassword())) {
                        JOptionPane.showMessageDialog(null, "新密码格式错误!", "WRONG", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "修改密码成功!", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                        DatabaseOperation.modifyPassword(name, jPasswordFieldNew.getPassword());
                        jFrameReset.setVisible(false);
                        InitUI.initUI();
                    }
                }
            }
        }

        btnConfirm.addActionListener(new btnConfirmHandler());
    }

    private static boolean checkPasswordFormat(char[] passwordCh, char[] checkPasswordCh) {
        String password = String.valueOf(passwordCh);
        String checkPassword = String.valueOf(checkPasswordCh);
        if (password.equals(checkPassword)) {
            if (password.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9#?!@$%^&*-.]+$)" +
                    "(?![A-Za-z#?!@$%^&*-.]+$)(?![A-Z0-9#?!@$%^&*-.]+$)[a-zA-Z0-9#?!@$%^&*-.]{6,10}$")) {
                return true;
            }
        }
        return false;
    }

}