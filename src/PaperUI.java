import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

/**
 * @ClassName : PaperUI  //类名
 * @Description : 用户的做题界面  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/20  22:46
 */


public class PaperUI {
    static double score;
    static int index;
    static boolean ifFinish;

    public static void paperUI(String name,String selectLevel, int questionNum) {
        score = 0;
        index = 0;
        ifFinish = false;
        HashSet<MathGenerate.ProblemInformation> paperHash = MathGenerate.generatePaper(selectLevel, questionNum);
        MathGenerate.ProblemInformation[] paper = new MathGenerate.ProblemInformation[questionNum];
        paperHash.toArray(paper);
        double singleScore = 100.0 / questionNum;
        // DatabaseOperation.storeMathInDatabase(paper,selectLevel);
        JFrame jFrame = new JFrame("您正在答题!");
        JPanel jPanel = new JPanel(null);
        jFrame.setSize(647, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 可以拖动的文本框
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setText(paper[index].expression);
        jTextArea.setFont(new Font("楷体", Font.PLAIN, 15));
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(200, 50, 200, 40);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jPanel.add(jScrollPane);
        String[] answers = assignAnswer(paper);
        // 选择答案按钮
        JRadioButton radioButtonA = new JRadioButton();
        radioButtonA.setText(answers[0]);
        radioButtonA.setBounds(50, 50, 100, 30);
        JRadioButton radioButtonB = new JRadioButton();
        radioButtonB.setText(answers[1]);
        radioButtonB.setBounds(50, 100, 100, 30);
        JRadioButton radioButtonC = new JRadioButton();
        radioButtonC.setText(answers[2]);
        radioButtonC.setBounds(50, 200, 100, 30);
        JRadioButton radioButtonD = new JRadioButton();
        radioButtonD.setText(answers[3]);
        radioButtonD.setBounds(50, 250, 100, 30);
        // 将四个按钮放到一个组中，便于选择
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonA);
        buttonGroup.add(radioButtonB);
        buttonGroup.add(radioButtonC);
        buttonGroup.add(radioButtonD);
        jPanel.add(radioButtonA);
        jPanel.add(radioButtonB);
        jPanel.add(radioButtonC);
        jPanel.add(radioButtonD);

        // 默认情况下认为A答案被选中
        radioButtonA.setSelected(true);
        // 选择下一道题的按钮
        JButton btnNext = new JButton("下一题");
        btnNext.setBounds(350, 300, 100, 30);
        jPanel.add(btnNext);
        JButton btnCommit = new JButton("提交");
        btnCommit.setBounds(350, 300, 100, 30);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
        class btnNextHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!ifFinish){
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
                    System.out.println(score);
                    index++;
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

    }

    private static String[] assignAnswer(MathGenerate.ProblemInformation[] problemInformations) {
        String[] answers = new String[4];
        Random random = new Random();
        int rightIndex = random.nextInt(4);
        answers[rightIndex] = String.valueOf(problemInformations[index].answer);
        for (int i = 0; i < 4; i++) {
            if (i != rightIndex) {
                answers[i] = String.valueOf(problemInformations[index].answer + random.nextDouble(2) + 5);
            }
        }
        return answers;
    }

    public static void finishUI(String name){
        JFrame jFrame = new JFrame("HaveFinished");
        JPanel jPanel = new JPanel(null);
        jFrame.setSize(647, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel jLabel = new JLabel();
        jLabel.setText("您最终的成绩是:"+String.valueOf(score));
        jLabel.setFont(new Font("楷体",Font.ITALIC,15));
        jLabel.setBounds(200, 150, 300, 30);

        //继续做题按钮和退出系统按钮
        JButton btnContinue = new JButton("继续做题");
        JButton btnExit = new JButton("退出系统");
        btnContinue.setBounds(100,300, 100, 30);
        btnExit.setBounds(300, 300  ,100, 30);
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
    }
}
