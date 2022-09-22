import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Random;

/**
 * @ClassName : PaperUI         // 类名
 * @Description : 用户的做题界面  // 描述
 * @Author : 15154              // 作者
 * @Date: 2022/9/20  22:46
 */


public class PaperUI {
    // 记录答卷人的得分，随每一道题对错而变化
    static double score;
    // 记录用户做题的个数
    static int index;
    // 标志用户是否答到最后一题
    static boolean ifFinish;

    /***
     * @description:
     * @param: name 用户的姓名
    selectLevel 用户选择的出题等级
    questionNum 用户选择的出题数
     * @return: void
     * @author Chang
     * @date: 2022/9/22 12:17
     */
    public static void paperUI(String name,String selectLevel, int questionNum) {
        // 初始化，便于后续的操作
        score = 0;
        index = 0;
        ifFinish = false;
        // 每个小题的分值，随题目个数而变化（总分为100分）
        double singleScore = 100.0 / questionNum;
        // 通过建立哈希表，保证出的题目都不重复，并且将问题和答案传到Paper中保存
        HashSet<MathGenerate.ProblemInformation> paperHash = MathGenerate.generatePaper(selectLevel, questionNum);
        MathGenerate.ProblemInformation[] paper = new MathGenerate.ProblemInformation[questionNum];
        paperHash.toArray(paper);
        // 四个选项的保存数组
        String[] answers = assignAnswer(paper);
        DatabaseOperation.storeMathInDatabase(paper,selectLevel);
        JFrame jFrame = new JFrame("您正在答题!");
        JPanel jPanel = new JPanel(null);
        jFrame.setSize(800, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 获取窗口的长度和宽度，为组件自适应大小做准备
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();
        // 显示在做第几道题
        JLabel jLabelIndex = new JLabel("您正在作答第"+index+1+"题");
        jLabelIndex.setFont(new Font("楷体", Font.ITALIC,26));
        jLabelIndex.setBounds(width/3, height/8, width/3, height/10);
        // 可以拖动的文本框
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setText(paper[index].expression);
        jTextArea.setFont(new Font("楷体",Font.BOLD,22));
        jTextArea.setBounds(width/4, height/4, width/2, height/12);
        // 选择答案按钮
        JRadioButton radioButtonA = new JRadioButton();
        radioButtonA.setText("A:"+answers[0]);
        radioButtonA.setBounds(width/6, height/3+height/12, width/3, height/8);
        radioButtonA.setFont(new Font("楷体",Font.BOLD,20));
        JRadioButton radioButtonB = new JRadioButton();
        radioButtonB.setText("B:"+answers[1]);
        radioButtonB.setBounds(width/6+width/3, height/3+height/12, width/3, height/8);
        radioButtonB.setFont(new Font("楷体",Font.BOLD,20));
        JRadioButton radioButtonC = new JRadioButton();
        radioButtonC.setText("C:"+answers[2]);
        radioButtonC.setBounds(width/6, height/3+height/5, width/3, height/8);
        radioButtonC.setFont(new Font("楷体",Font.BOLD,20));
        JRadioButton radioButtonD = new JRadioButton();
        radioButtonD.setText("D:"+answers[3]);
        radioButtonD.setBounds(width/6+width/3, height/3+height/5, width/3, height/8);
        radioButtonD.setFont(new Font("楷体",Font.BOLD,20));
        // 将四个按钮放到一个组中，便于选择
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonA);
        buttonGroup.add(radioButtonB);
        buttonGroup.add(radioButtonC);
        buttonGroup.add(radioButtonD);
        // 默认情况下认为A答案被选中
        radioButtonA.setSelected(true);
        // 选择下一道题的按钮
        JButton btnNext = new JButton("下一题");
        btnNext.setBounds(width-width/4, height/2+height/4, width/6, height/15);
        JButton btnCommit = new JButton("提交");
        btnCommit.setBounds(width-width/4, height/2+height/4, width/6, height/15);
        // 将组件加入到面板中
        jPanel.add(jLabelIndex);
        jPanel.add(jTextArea);
        jPanel.add(radioButtonA);
        jPanel.add(radioButtonB);
        jPanel.add(radioButtonC);
        jPanel.add(radioButtonD);
        jPanel.add(btnNext);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        /***
         * @description: 按下"下一题"按钮时候的相应事件
         * @param: name 用户的名字
        selectLevel 用户选择的等级
        questionNum 用户选择的出题数
         * @return: void
         * @author Chang
         * @date: 2022/9/22 12:17
         */
        class btnNextHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 首先对是否到最后一道题进行判断，如果没有到最后一道题的界面，则首先计算上一道题的得分，并将界面更换成新题
                if(!ifFinish){
                    String answerSelected = "";
                    for (Component component : jPanel.getComponents()) {
                        if (component instanceof JRadioButton) {
                            if (((JRadioButton) component).isSelected()) {
                                answerSelected = ((JRadioButton) component).getText();
                            }
                        }
                    }
                    // 记录每一道题的得分，并将总分累加
                    if (answerSelected.equals(String.valueOf(paper[index].answer))) {
                        score += singleScore;
                    }
                    // 下标自加1，跳转到下一道题
                    index++;
                    // 如果已经到最后一道题，则将按钮更换成”提交“的字样
                    if (index == questionNum - 1) {
                        String[] answers = assignAnswer(paper);
                        System.out.println(index);
                        jTextArea.setText(paper[index].expression);
                        radioButtonA.setText(answers[0]);
                        radioButtonB.setText(answers[1]);
                        radioButtonC.setText(answers[2]);
                        radioButtonD.setText(answers[3]);
                        btnNext.setText("提交");
                        ifFinish = true;
                    } else {
                        String[] answers = assignAnswer(paper);
                        System.out.println(index);
                        jTextArea.setText(paper[index].expression);
                        radioButtonA.setText(answers[0]);
                        radioButtonB.setText(answers[1]);
                        radioButtonC.setText(answers[2]);
                        radioButtonD.setText(answers[3]);
                    }
                }
                // 已经到最后一道题，并且此时按钮名字为"提交"，那么记录最后一道题的得分情况，累加到总分中，然后跳转到finishUI
                else {
                    String answerSelected = "";
                    for (Component component : jPanel.getComponents()) {
                        if (component instanceof JRadioButton) {
                            if (((JRadioButton) component).isSelected()) {
                                answerSelected = ((JRadioButton) component).getText();
                            }
                        }
                    }
                    if (answerSelected.equals(String.valueOf(paper[index].answer))) {
                        score += singleScore;
                    }
                    jFrame.setVisible(false);
                    finishUI(name);
                }
            }
        }
        btnNext.addActionListener(new btnNextHandler());

        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange = jFrame.getWidth();
                int heightChange = jFrame.getHeight();
                jTextArea.setBounds(widthChange/4, heightChange/4, widthChange/2, heightChange/12);
                radioButtonA.setBounds(widthChange/6, heightChange/3+heightChange/12, widthChange/3, heightChange/8);
                radioButtonB.setBounds(widthChange/6+widthChange/3, heightChange/3+heightChange/12, widthChange/3, heightChange/8);
                radioButtonC.setBounds(widthChange/6, heightChange/3+heightChange/5, widthChange/3, heightChange/8);
                radioButtonD.setBounds(widthChange/6+widthChange/3, heightChange/3+heightChange/5, widthChange/3, heightChange/8);
                btnNext.setBounds(width-widthChange/4, heightChange/2+heightChange/4, widthChange/6, heightChange/15);
                btnCommit.setBounds(widthChange-widthChange/4, heightChange/2+heightChange/4, widthChange/6, heightChange/15);
            }

        });
    }

    /***
     * @description: 生成四个选线，其中只有一个选项是标准答案，其它答案为在原选项上加随机数
     * @param: problemInformations 题目信息
     * @return: java.lang.String[] 返回答案数组
     * @author Chang
     * @date: 2022/9/22 12:22
     */
    private static String[] assignAnswer(MathGenerate.ProblemInformation[] problemInformations) {
        String[] answers = new String[4];
        Random random = new Random();
        int rightIndex = random.nextInt(4);
        answers[rightIndex] = String.valueOf(problemInformations[index].answer);
        for (int i = 0; i < 4; i++) {
            if (i != rightIndex) {
                answers[i] = String.valueOf(problemInformations[index].answer + random.nextDouble(2.0) + 5.0);
            }
        }
        return answers;
    }

    /***
     * @description:  完成界面的UI，此时可以选择继续出题或者退出系统
     * @param: name 用户的名字
     * @return: void
     * @author Chang
     * @date: 2022/9/22 12:23
     */
    public static void finishUI(String name){
        JFrame jFrame = new JFrame("您已经完成作答");
        JPanel jPanel = new JPanel(null);
        jFrame.setSize(800, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 获取窗口的长度和宽度
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();

        JLabel jLabel = new JLabel();
        jLabel.setText("您最终的成绩是:"+String.valueOf(score));
        jLabel.setFont(new Font("楷体",Font.ITALIC,35));
        jLabel.setBounds(width/4, height/3, width/2, height/10);

        //继续做题按钮和退出系统按钮
        JButton btnContinue = new JButton("继续做题");
        JButton btnExit = new JButton("退出系统");
        btnContinue.setBounds(width/8, height/2+height/6, width/8, height/12);
        btnExit.setBounds(width/2+width/6, height/2+height/6, width/8, height/12);
        jPanel.add(btnContinue);
        jPanel.add(btnExit);
        jPanel.add(jLabel);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        class btnContinueHandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                jFrame.setVisible(false);
                SelectUI.selectUI(name);
            }
        }
        class btnExitHandler implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                jFrame.setVisible(false);
                InitUI.initUI();
            }
        }
        btnContinue.addActionListener(new btnContinueHandler());
        btnExit.addActionListener(new btnExitHandler());

        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int widthChange = jFrame.getWidth();
                int heightChange = jFrame.getHeight();
                jLabel.setBounds(widthChange/4, heightChange/3, widthChange/2, heightChange/10);
                btnContinue.setBounds(widthChange/8, heightChange/2+heightChange/6, widthChange/8, heightChange/12);
                btnExit.setBounds(widthChange/2+widthChange/6, heightChange/2+heightChange/6, widthChange/8, heightChange/12);
            }

        });
    }
}
