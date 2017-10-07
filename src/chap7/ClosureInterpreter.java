package chap7;

import chap6.BasicInterpreter;
import stone.ClosureParser;
import stone.ParseException;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 0:16 2017/10/8
 * @Modified_by:
 */
public class ClosureInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new NestedEnv());
    }
}
