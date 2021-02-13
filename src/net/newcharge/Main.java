package net.newcharge;

import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        String constant = "-282828.0e-7#";
        Object ans = ConstantTranslator.translate(constant);
        if(ans instanceof Integer) {
            int integer = (Integer) ans;
            System.out.println("int " + integer);
        }
        if(ans instanceof Double) {
            double real = (Double) ans;
            System.out.println("real " + new DecimalFormat("#0.000000").format(real));
        }
    }
}
