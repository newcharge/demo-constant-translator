package net.newcharge;

import javafx.util.Pair;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String constant = "-282828#";
        Pair<String, Object> ans = ConstantTranslator.translate(constant);
        if(ans.getKey().equals("int")) {
            int integer = (Integer) ans.getValue() + 1;
            System.out.println("int " + integer);
        }
        if(ans.getKey().equals("real") || ans.getKey().equals("e_real")) {
            double real = (Double) ans.getValue() + 1;
            System.out.println("real " + real);
        }
    }
}
