package simplemathexpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SimpleMathExpression {

    public static ArrayList<Character> symbols = new ArrayList<>(Arrays.asList('+', '-', '*', '/'));
    public static ArrayList<Character> signs = new ArrayList<>(Arrays.asList('+', '-'));
    
    public static void main(String[] args) {
        
        String expression = "";

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("calc> ");    // -2+24*3-23+10*2-24/8*2-2
                expression = sc.nextLine();
                expression.replace(" ", "");
                expression.replace("\t", "");
                char sign = expression.charAt(0);
                
                if (expression.equals("exit"))
                    break; 
                if (!signs.contains(sign))                    
                    expression = "+" + expression;
                ArrayList<String> explist = new ArrayList<>();
                explist = mathToList(expression);
                System.out.println(evaluate(explist));
            } catch (ArithmeticException e) {
                System.out.println("Illegal division by zero...");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }

    public static ArrayList<String> mathToList(String expression) {
        ArrayList<String> explist = new ArrayList<>();
        String num = "";
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                num += c;
            } else if (symbols.contains(c)) {
                explist.add(num);
                explist.add(c + "");
                num = "";
            }
        }
        explist.add(num);
        explist.remove(0);
        Collections.reverse(explist);
        return explist;
    }

    public static double evaluate(ArrayList<String> explist) {
        ArrayList<Double> sumlist = new ArrayList<>();
        ArrayList<Double> prodlist = new ArrayList<>();
        sumlist.add(0.0);
        prodlist.add(1.0);
        
        for (String el : explist) {
            if (!symbols.contains(el.charAt(0))) {
                prodlist.add(Double.parseDouble(el));
            } else if (el.equals("*")) {
                continue;
            } else if (el.equals("/")) {
                double temp = prodlist.get(prodlist.size() - 1);
                prodlist.set(prodlist.size() - 1, 1 /((double)temp));
            } else if (el.equals("+")) {
                sumlist.add(product(prodlist));
                prodlist.clear();
                prodlist.add(1.0);
            } else if (el.equals("-")) {
                sumlist.add(product(prodlist) * (-1.0));
                prodlist.clear();
                prodlist.add(1.0);
            }
        }
        return dsum(sumlist);
    }

    public static double product(ArrayList<Double> prodlist) {
        double result = 1;
        for (Double d : prodlist)
            result *= d;
        return result;
    }

    public static double dsum(ArrayList<Double> sumlist) {
        double result = 0;
        for (Double d : sumlist)
            result += d;
        return result;
    }

}
