import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

/**
 * @ClassName : Main  //类名
 * @Description : This is the main interface  //描述
 * @Author : Chang,Liu //作者
 * @Date: 2022/9/17  23:20
 */


public class Main {
    /***
     * @description: 生成6位随机数字验证码
     * @param:
     * @return: java.lang.String
     * @author 15154
     * @date: 2022/9/18 19:22
     */
    public static String makeRandomNum(){
        Random random = new Random();
        String randomNum = "";
        for(int i = 0;i < 6;i++){
            randomNum+=random.nextInt(10);
        }
        return randomNum;
    }
    public static void main(String[] args){
        InitUI.initUI();
    }
}
