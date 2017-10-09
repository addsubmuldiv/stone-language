package chap8;

import chap7.ClosureEvaluator;
import javassist.gluonj.util.Loader;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 12:57 2017/10/9
 * @Modified_by:
 */
public class NativeRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(NativeInterpreter.class,args,NativeEvaluator.class, ClosureEvaluator.class);
    }
}
