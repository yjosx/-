package pow.jie.calculater;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入算式：");
        String strString = sc.nextLine();
        System.out.println("结果为"+Calculator.calculate(strString));
    }

}