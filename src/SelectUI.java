import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @ClassName : SelectUI  //类名
 * @Description : 选择出题界面  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/19  22:25
 */


public class SelectUI {
    public static void selectUI(String name) {
        JFrame jFrame = new JFrame("选择出卷难度");
        jFrame.setSize(800, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(null);

        int width = jFrame.getWidth();
        int height = jFrame.getHeight();
        // 创建小学、初中、高中三个单选按钮
        JRadioButton radioButtonPrimary = new JRadioButton("小学");
        JRadioButton radioButtonJunior = new JRadioButton("初中");
        JRadioButton radioButtonHigh = new JRadioButton("高中");
        radioButtonPrimary.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonPrimary.setBounds(width/4,height/10,width/6,height/4);
        radioButtonJunior.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonJunior.setBounds(width/4+width/6,height/10,width/6,height/4);
        radioButtonHigh.setFont(new Font("楷体",Font.PLAIN,30));
        radioButtonHigh.setBounds(width/4+width/3,height/10,width/6,height/4);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonPrimary);
        buttonGroup.add(radioButtonJunior);
        buttonGroup.add(radioButtonHigh);
        //生成题目数量标签
        JLabel jLabelQuestionNum = new JLabel("请选择成的题目数量(10-30)");
        jLabelQuestionNum.setFont(new Font("楷体",Font.PLAIN,20));
        jLabelQuestionNum.setBounds(width/3, height/4, width/3, height/4);
        //设置文本框，选择出题数目
        JTextField jTextFieldNum = new JTextField(2);
        jTextFieldNum.setBounds(width/2-width/9, height/2, width/6, height/15);
        // 设置重置密码的按钮
        JButton btnReset = new JButton("重置密码");
        btnReset.setBounds(width/30, height/2+height/3, width/8, height/15);
        // 设置确认生成题目的按钮
        JButton btnConfirmNum = new JButton("确认生成题目");
        btnConfirmNum.setBounds(width/3-width/22, height/2+height/5, width/3+width/20, height/15);
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

        // 将确认生成密码按钮加入相应的事件监听
        class btnConfirmNumHandler implements ActionListener {
            String levelSelected = "";
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jTextFieldNum.getText().matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "请输入正确的数据格式!", "", JOptionPane.PLAIN_MESSAGE);
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
                        PaperUI.paperUI(name,levelSelected,questionNum);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "请输入正确的题目数量!", "", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        }
        btnConfirmNum.addActionListener(new btnConfirmNumHandler());

        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange = jFrame.getWidth();
                int heightChange = jFrame.getHeight();
                radioButtonPrimary.setBounds(widthChange/4,heightChange/10,widthChange/6,heightChange/4);
                radioButtonJunior.setBounds(widthChange/4+widthChange/6,heightChange/10,widthChange/6,heightChange/4);
                radioButtonHigh.setBounds(widthChange/4+widthChange/3,heightChange/10,widthChange/6,heightChange/4);
                jLabelQuestionNum.setBounds(widthChange/3, heightChange/4, widthChange/3, heightChange/4);
                jTextFieldNum.setBounds(widthChange/2-widthChange/9, heightChange/2, widthChange/6, heightChange/15);
                btnReset.setBounds(widthChange/30, heightChange/2+heightChange/3, widthChange/8, heightChange/15);
                btnConfirmNum.setBounds(widthChange/3-widthChange/22, heightChange/2+heightChange/5, widthChange/3+widthChange/20, heightChange/15);
            }
        });
    }

    public static void resetPasswordUI(String name) {
        JFrame jFrameReset = new JFrame("重新设置密码");
        jFrameReset.setSize(800, 450);
        jFrameReset.setLocationRelativeTo(null);
        jFrameReset.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(null);

        // 获取窗口的长度和宽度，为组件自适应大小做准备
        int width = jFrameReset.getWidth();
        int height = jFrameReset.getHeight();
        // 原始密码、新密码、确认新密码提示标签
        JLabel jLabelInitPwd = new JLabel("原密码:");
        JLabel jLabelNewPwd = new JLabel("新密码:");
        JLabel jLabelCheckPwd = new JLabel("确认新密码:");
        jLabelInitPwd.setBounds(width/4, height/8, width/10, height/10);
        jLabelInitPwd.setFont(new Font("楷体",Font.ITALIC,20));
        jLabelNewPwd.setBounds(width/4, height/8+height/6, width/10, height/10);
        jLabelNewPwd.setFont(new Font("楷体",Font.ITALIC,20));
        jLabelCheckPwd.setBounds(width/4-width/20, height/8+height/3, width/4, height/10);
        jLabelCheckPwd.setFont(new Font("楷体",Font.ITALIC,20));

        // 原始密码、新密码、确认新密码的密码框
        JPasswordField jPasswordFieldInit = new JPasswordField(10);
        JPasswordField jPasswordFieldNew = new JPasswordField(10);
        JPasswordField jPasswordFieldCheck = new JPasswordField(10);
        jPasswordFieldInit.setBounds(width/4+width/8, height/7, width/4, height/15);
        jPasswordFieldNew.setBounds(width/4+width/8, height/7+height/6, width/4, height/15);
        jPasswordFieldCheck.setBounds(width/4+width/8, height/7+height/3, width/4, height/15);

        // 确认修改按钮
        JButton btnConfirm = new JButton("确认修改");
        btnConfirm.setBounds(width/4+width/15, height/3+height/3, width/3, height/15);

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
                    JOptionPane.showMessageDialog(null, "原密码输入不正确!", "W", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    if(!checkPasswordFormat(jPasswordFieldNew.getPassword(), jPasswordFieldCheck.getPassword())) {
                        JOptionPane.showMessageDialog(null, "新密码格式错误!", "", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "修改密码成功!", "", JOptionPane.PLAIN_MESSAGE);
                        DatabaseOperation.modifyPassword(name, jPasswordFieldNew.getPassword());
                        jFrameReset.setVisible(false);
                        InitUI.initUI();
                    }
                }
            }
        }

        btnConfirm.addActionListener(new btnConfirmHandler());

        jFrameReset.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange = jFrameReset.getWidth();
                int heightChange = jFrameReset.getHeight();
                jLabelInitPwd.setBounds(widthChange/4, heightChange/8, widthChange/10, heightChange/10);
                jLabelNewPwd.setBounds(widthChange/4, heightChange/8+heightChange/6, widthChange/10, heightChange/10);
                jLabelCheckPwd.setBounds(widthChange/4-widthChange/20, heightChange/8+heightChange/3, widthChange/4, heightChange/10);
                jPasswordFieldInit.setBounds(widthChange/4+widthChange/8, heightChange/7, widthChange/4, heightChange/15);
                jPasswordFieldNew.setBounds(widthChange/4+widthChange/8, heightChange/7+heightChange/6, widthChange/4, heightChange/15);
                jPasswordFieldCheck.setBounds(widthChange/4+widthChange/8, heightChange/7+heightChange/3, widthChange/4, heightChange/15);
                btnConfirm.setBounds(widthChange/4+widthChange/15, heightChange/3+heightChange/3, widthChange/3, heightChange/15);
            }

        });
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