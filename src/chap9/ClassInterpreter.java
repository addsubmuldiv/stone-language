package chap9;

import chap6.BasicInterpreter;
import chap7.NestedEnv;
import chap8.Natives;
import stone.ClassParser;
import stone.ParseException;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 16:06 2017/10/13
 * @Modified_by:
 */
public class ClassInterpreter extends BasicInterpreter{
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(),new Natives().environment(new NestedEnv()));
    }
}
