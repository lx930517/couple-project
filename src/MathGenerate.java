import java.util.HashSet;
import java.util.Random;

/**
 * @ClassName : MathGenerate  //类名
 * @Description : 生成数学题目  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/21  8:16
 */


public class MathGenerate {
    // 标志是否出现除以0，根号下负数，tan90的情况，如果有，则重新生成，true表示无，false表示有
    static boolean isIllegal = false;
    // 下面数学符号对应的等级
    static int[] opPriority = {
            1, 1, 2, 2, 3, 3, 4, 4, 4, -1
    };
    //不同字符数学符号表达
    static String[] operation = {
            "+", "-", "*", "/", "√", "^2", "sin", "cos", "tan", "none"
    };

    public static String getOperation() {
        Random random = new Random();
        return operation[random.nextInt(9)];
    }

    /**
     * @author :Chang
     * @date :2022/9/10 20:54
     * @description :得到数学符号在operation数组中的下标
     */
    public static int getOpIndex(String strOp) {
        int opIndex = 0;
        for (int index = 0; index < operation.length; index++) {
            if (operation[index].equals(strOp)) {
                opIndex = index;
                break;
            }
        }
        return opIndex;
    }

    /**
     * @author :Chang
     * @date :2022/9/10 20:55
     * @description :生成单独一个数学题目，本质上是构造这个树
     */
    static Problem generateMath(int operationNum) {
        isIllegal = false;
        Problem problem = new Problem();
        Random random = new Random();
        int leftOpNum;
        /**
         * 如果被分配的数学操作符为0个，说明该节点是实数节点，取值范围1-100
         * 则该节点为根节点，左子树和右子树都为空
         * 将等级的值设置为ACCOUNT_LEVEL_PRIMARY,用于后续判断
         */
        if (operationNum == 0) {
            problem.left = null;
            problem.right = null;
            problem.operator = "none";
            problem.level = accountLevel.ACCOUNT_LEVEL_PRIMARY;
            problem.randomValue = random.nextInt(100) + 1;
            return problem;
        }
        problem.operator = getOperation();
        /**
         * 根据当前节点的数学符号进行后续操作
         * 1、如果当前符号是加减乘除二元符号，则给左节点和右节点将剩余数学符号数随机分配
         * 2、如果是根号、平方一元符号，由于后续是进行中序遍历，则将左子树设置为空，右子树进行剩余数学符号的分配，并将等级设置为ACCOUNT_LEVEL_JUNIOR
         * 3、如果是三角符号，由于后续是进行中序遍历，则将左子树设置为空，右子树进行剩余数学符号的分配，并将等级设置为ACCOUNT_LEVEL_HIGH
         */
        switch (problem.operator) {
            case "+", "-", "*", "/":
                leftOpNum = random.nextInt(operationNum);
                problem.left = generateMath(leftOpNum);
                problem.right = generateMath(operationNum - leftOpNum - 1);
                break;
            case "√", "^2":
                problem.left = null;
                problem.right = generateMath(operationNum - 1);
                if (problem.level < accountLevel.ACCOUNT_LEVEL_JUNIOR) {
                    problem.level = accountLevel.ACCOUNT_LEVEL_JUNIOR;
                }
                break;
            case "sin", "cos", "tan":
                problem.left = null;
                problem.right = generateMath(operationNum - 1);
                if (problem.level < accountLevel.ACCOUNT_LEVEL_HIGH) {
                    problem.level = accountLevel.ACCOUNT_LEVEL_HIGH;
                }
                break;
            default:
                break;
        }
        // 用于遍历，找出所有数学符号中最高的符号等级，和用户等级在后续进行匹配
        if (problem.left != null) {
            if (problem.level < problem.left.level) {
                problem.level = problem.left.level;
            }
        }
        if (problem.right != null) {
            if (problem.level < problem.right.level) {
                problem.level = problem.right.level;
            }
        }


        switch (problem.operator) {
            case "+":
                assert problem.left != null;
                problem.randomValue = problem.left.randomValue + problem.right.randomValue;
                break;
            case "-":
                assert problem.left != null;
                problem.randomValue = problem.left.randomValue - problem.right.randomValue;
                break;
            case "*":
                assert problem.left != null;
                problem.randomValue = problem.left.randomValue * problem.right.randomValue;
                break;
            case "/":
                assert problem.left != null;
                // 如果出现除以0的情况，则将标志设置为true，用于下面方法中进行判断，以下同理
                if (problem.right.randomValue == 0.0) {
                    isIllegal = true;
                }
                problem.randomValue = problem.left.randomValue / problem.right.randomValue;
                break;
            case "√":
                if (problem.right.randomValue < 0) {
                    isIllegal = true;
                }
                problem.randomValue = Math.sqrt(problem.right.randomValue);
                break;
            case "^2":
                problem.randomValue = Math.pow(problem.right.randomValue, 2);
                break;
            case "sin":
                problem.randomValue = Math.sin(Math.toRadians(problem.right.randomValue));
                break;
            case "cos":
                problem.randomValue = Math.cos(Math.toRadians(problem.right.randomValue));
                break;
            case "tan":
                if (Math.toRadians(problem.right.randomValue - 90) % 180 == 0) {
                    isIllegal = true;
                }
                problem.randomValue = Math.tan(Math.toRadians(problem.right.randomValue));
                break;
            default:
                break;
        }
        return problem;
    }

    /**
     * @author :Chang
     * @date :2022/9/10 21：03
     * @description :生成表达式字符串
     */
    public static String generateExpression(Problem problem) {
        String leftExp = "";
        String rightExp = "";
        String finalExp = "";

        /**
         * 左子树和右子树不为空时的操作，中序遍历
         * 1、如果左子树不为空，则进行递归，如果左子树的运算符存在并且左子树的运算符等级小于当前节点运算符的等级，说明左子树需要加括号
         * 2、如果右子树不为空，则进行递归，如果右子树的运算符存在并且左子树的运算符等级小于当前节点运算符的等级，说明右子树需要加括号
         */
        if (problem.left != null) {
            leftExp = generateExpression(problem.left);
            if (!problem.left.operator.equals("none") && (opPriority[getOpIndex(problem.left.operator)] < opPriority[getOpIndex(problem.operator)])) {
                leftExp = "(" + leftExp + ")";
            }
        }
        if (problem.right != null) {
            rightExp = generateExpression(problem.right);
            if (!problem.right.operator.equals("none") && (opPriority[getOpIndex(problem.right.operator)] <= 2)) {
                rightExp = "(" + rightExp + ")";
            }
        }
        // 根据当前运算符，将左边和右边连接起来
        switch (problem.operator) {
            case "+":
                finalExp = leftExp + "+" + rightExp;
                break;
            case "-":
                finalExp = leftExp + "-" + rightExp;
                break;
            case "*":
                finalExp = leftExp + "*" + rightExp;
                break;
            case "/":
                finalExp = leftExp + "/" + rightExp;
                break;
            case "√":
                finalExp = "√" + "(" + rightExp + ")";
                break;
            case "^2":
                finalExp = "(" + rightExp + ")" + "^2";
                break;
            case "sin":
                finalExp = "sin" + "(" + rightExp + ")";
                break;
            case "cos":
                finalExp = "cos" + "(" + rightExp + ")";
                break;
            case "tan":
                finalExp = "tan" + "(" + rightExp + ")";
                break;
            case "none":
                finalExp = String.valueOf(problem.randomValue);
                break;
            default:
                break;
        }
        return finalExp;
    }

    /**
     * @author :Chang
     * @date :2022/9/10 21：08
     * @description :根据用户的等级选择相应的数学公式
     */
    public static ProblemInformation getFinalExpression(String type) {
        Random random = new Random();
        ProblemInformation problemInformation = new ProblemInformation();
        String mathStr;
        while (true) {
            Problem problemTemp = generateMath(random.nextInt(3) + 3);
            if (!isIllegal) {
                mathStr = generateExpression(problemTemp);
                if (type.equals("小学") && problemTemp.level == 1) {
                    problemInformation.expression = mathStr;
                    problemInformation.answer = problemTemp.randomValue;
                    break;
                } else if (type.equals("初中") && problemTemp.level == 2) {
                    problemInformation.expression = mathStr;
                    problemInformation.answer = problemTemp.randomValue;
                    break;
                } else if (type.equals("高中") && problemTemp.level == 3) {
                    problemInformation.expression = mathStr;
                    problemInformation.answer = problemTemp.randomValue;
                    break;
                }
            }
        }
        return problemInformation;
    }

    public interface accountLevel {
        /**
         * 设置小学等级的值为1，一下同理
         */
        int ACCOUNT_LEVEL_PRIMARY = 1;
        int ACCOUNT_LEVEL_JUNIOR = 2;
        int ACCOUNT_LEVEL_HIGH = 3;
    }

    public static class ProblemInformation {
        String expression;
        double answer;
    }

    public static class Problem {
        /**
         * 树的节点的符号，用于后续相应生成数学表达式
         * left、right分别是左子树、右子树
         * operator是该节点的数学符号
         * randomValue是该节点随机生成的double类型的随机值
         * level是该节点的等级，取值为ACCOUNT_LEVEL_PRIMARY、ACCOUNT_LEVEL_JUNIOR、ACCOUNT_LEVEL_HIGH
         */
        Problem left;
        Problem right;
        String operator;
        double randomValue;
        int level;
    }

    public static HashSet<ProblemInformation> generatePaper(String type, int num) {
        HashSet<ProblemInformation> paper = new HashSet<>();
        for (int i = 0; i < num; i++) {
            while (true) {
                ProblemInformation problemInformation = getFinalExpression(type);
                if (!paper.contains(problemInformation)) {
                    paper.add(problemInformation);
                    break;
                }
            }
        }
        return paper;
    }
}
