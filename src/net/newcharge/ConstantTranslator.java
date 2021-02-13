package net.newcharge;

import javafx.util.Pair;

import java.util.*;

public class ConstantTranslator {
    private static List<Map<Character, Integer>> table;
    private static final TranslatorFactor FACTOR = TranslatorFactor.get();

    private static List<Map<Character, Integer>> init() {
        table = new ArrayList<>();
        for(int i = 0; i < 8; i++) table.add(new HashMap<>());

        //添加识别器诸元，从状态2结束断定为整数，从状态4结束断定为小数，从状态7结束断定为e指数
        tablePutNum(0, 2);
        table.get(0).put('+', 1);
        table.get(0).put('-', 1);

        tablePutNum(1, 2);

        tablePutNum(2, 2);
        table.get(2).put('.', 3);
        table.get(2).put('e', 5);

        tablePutNum(3, 4);

        tablePutNum(4, 4);
        table.get(4).put('e', 5);

        tablePutNum(5, 7);
        table.get(5).put('+', 6);
        table.get(5).put('-', 6);

        tablePutNum(6, 7);

        tablePutNum(7, 7);

        return table;
    }

    private static void tablePutNum(int curr, int next) {
        //填充数字（0-9）
        char numStr = '0';
        for(int i = 0; i < 10; i++)
            table.get(curr).put((char) (numStr + i), next);
    }

    public static Pair<String, Object> translate(String str) {
        //懒加载
        Optional.ofNullable(table).orElseGet(ConstantTranslator::init);
        //标签
        String tag = "";
        //const
        Object constant;

        //正餐
        char[] chars = str.toCharArray();
        int curr = 0;   //当前状态值
        int loc = 0;    //当前扫描位置

        while(chars[loc] != '#') {
            curr = table.get(curr).get(chars[loc]);    //状态转换
            //执行语义动作
            FACTOR.execute(curr, chars[loc]);
            loc++;
        }

        //标记为整数
        if(curr == 2) tag = "int";
        //标记为小数
        if(curr == 4) tag = "real";
        //标记为e指数
        if(curr == 7) tag = "e_real";

        //计算常数值
        constant = FACTOR.calculate();

        return new Pair<>(tag, constant);
    }
}
