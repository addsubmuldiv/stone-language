package chap7;

import javassist.gluonj.util.Loader;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 17:32 2017/10/5
 * @Modified by:
 */
public class FuncRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(FuncInterpreter.class, args, FuncEvaluator.class);
    }
}
