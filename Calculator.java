package pow.jie.calculater;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    // 用于判断优先级
    private static HashMap<Character, Integer> priority = new HashMap<>();

    static {
        priority.put('+', 1);
        priority.put('-', 1);
        priority.put('*', 2);
        priority.put('/', 2);
    }

    static String calculate(String str) {
        int len = str.length();
        char c, tempChar;
        ArrayDeque<Character> Operator = new ArrayDeque<>();
        ArrayDeque<Double> Value = new ArrayDeque<>();
        Stack<Object> tempStack = new Stack<>();
        double number;
        int lastIndex;
        String operator = "+-*/";            // 用于判断是否是操作符
        for (int i = 0; i < len; ++i) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                lastIndex = readDouble(str, i);
                number = Double.parseDouble(str.substring(i, lastIndex));
                Value.push(number);
                i = lastIndex - 1;
                if ((int) number == number)
                    tempStack.push((int) number);
                else
                    tempStack.push(number);
            } else if (c == '-' && i == 0) {
                lastIndex = readDouble(str, i + 1);
                number = Double.parseDouble(str.substring(i, lastIndex));
                Value.push(number);
                i = lastIndex - 1;
                if ((int) number == number)
                    tempStack.push((int) number);
                else
                    tempStack.push(number);
            } else if (i > 0 && c == '-' &&
                    (str.charAt(i - 1) == '('
                            || operator.indexOf(str.charAt(i - 1)) != -1)) {
                lastIndex = readDouble(str, i + 1);
                number = Double.parseDouble(str.substring(i, lastIndex));
                Value.push(number);
                i = lastIndex - 1;
                if ((int) number == number)
                    tempStack.push((int) number);
                else
                    tempStack.push(number);
            } else if (operator.indexOf(c) != -1) {
                while (!Operator.isEmpty() && Operator.peek() != '('
                        && priority.get(c) < priority.get(Operator.peek())) {
                    System.out.print(Operator.peek() + " ");
                    double num1 = Value.pop();
                    double num2 = Value.pop();
                    tempStack.push(Operator.peek());
                    Value.push(calc(num2, num1, Operator.pop()));
                }
                Operator.push(c);
            } else if (c == '(') {
                Operator.push(c);
            } else if (c == ')') {
                while ((tempChar = Operator.pop()) != '(') {
                    double num1 = Value.pop();
                    double num2 = Value.pop();
                    tempStack.push(Operator.peek());
                    Value.push(calc(num2, num1, tempChar));
                    if (Operator.isEmpty()) {
                        return "出错";
                    }
                }
            } else if (c == ' ') {
            } else {
                return "出错";
            }
        }
        while (!Operator.isEmpty()) {
            tempChar = Operator.pop();
            tempStack.push(tempChar);
            double num1 = Value.pop();
            double num2 = Value.pop();
            Value.push(calc(num2, num1, tempChar));
        }
        double result = Value.pop();
        if (!Value.isEmpty())
            return "出错";
        if ((int) result == result)
            return String.valueOf(((int) result));
        else
            return String.valueOf(result);
    }

    /**
     * 获取double值的最后一位索引
     */
    private static int readDouble(String str, int start) {
        int len = str.length();
        char ch;
        for (int i = start; i < len; i++) {
            ch = str.charAt(i);
            if (ch == '.') {
                if (i == len - 1)
                    return -1;
            } else if (!Character.isDigit(ch)) {
                return i;
            } else if (i == len - 1) {
                return len;
            }
        }
        return -1;
    }

    /**
     * 计算两个数的结果并返回
     */
    private static double calc(double num1, double num2, char op) {
        switch (op) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0)
                    throw new ArithmeticException("除数不能为0");
                return num1 / num2;
        }
        return 0;
    }
}
