package chap7;

import chap6.BasicInterpreter;
import stone.FuncParser;
import stone.ParseException;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 17:21 2017/10/5
 * @Modified by:
 */
public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(),new NestedEnv());
    }
}
