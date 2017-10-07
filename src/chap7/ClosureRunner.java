package chap7;

import javassist.gluonj.util.Loader;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 0:32 2017/10/8
 * @Modified_by:
 */
public class ClosureRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClosureInterpreter.class, args, ClosureEvaluator.class);
    }
}
