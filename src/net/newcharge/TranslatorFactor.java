package net.newcharge;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TranslatorFactor {
    //语义动作表
    private Map<Integer, Consumer<Character>> table;

    //对预测结构 const = f * ( n * 10 ^ ( e * p - m ) )
    private int n = 0;  //尾数值
    private int p = 0;  //指数值
    private int m = 0;  //小数位数
    private int e = 1;  //指数符号，只能为1或-1
    private int f = 1;  //尾数符号，只能为1或-1
    private int t = 0;  //类型变量，整形为0，实型为1

    private static final TranslatorFactor factor = new TranslatorFactor();

    private TranslatorFactor() {
        init();
    }

    private void init() {
        table = new HashMap<>();

        table.put(1, (ch) -> {
            if(ch == '-') setF(-1);
        });

        table.put(2, (ch) -> n = 10 * n + (int) ch - '0');

        table.put(3, (ch) -> t = 1);
        table.put(4, (ch) -> {
            n = 10 * n + ch - '0'; m++;
        });
        table.put(5, (ch) -> t = 1);
        table.put(6, (ch) -> {
            if(ch == '-') setE(-1);
        });
        table.put(7, (ch) -> p = p * 10 + ch - '0');
    }

    public Object calculate() {
        Object obj;

        if(t == 0)  obj = f * n;
        else obj = f * n * Math.pow(10, e * p - m);

        return obj;
    }

    public void execute(Integer i, Character ch) {
        table.get(i).accept(ch);
    }

    public static TranslatorFactor get() {
        factor.setN(0);
        factor.setP(0);
        factor.setM(0);
        factor.setE(1);
        factor.setF(1);
        factor.setT(0);

        return factor;
    }

    //getters and setters

    public void setN(int n) {
        this.n = n;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setE(int e) {
        this.e = e;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setT(int t) {
        this.t = t;
    }
}
