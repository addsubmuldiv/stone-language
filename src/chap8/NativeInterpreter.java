package chap8;

import chap6.BasicInterpreter;
import chap7.NestedEnv;
import stone.ClosureParser;
import stone.ParseException;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 12:52 2017/10/9
 * @Modified_by:
 */
public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),new Natives().environment(new NestedEnv()));
    }
}
