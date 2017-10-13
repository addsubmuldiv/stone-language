package chap9;

import chap7.ClosureEvaluator;
import chap8.NativeEvaluator;
import javassist.gluonj.util.Loader;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 16:08 2017/10/13
 * @Modified_by:
 */
public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class,
                NativeEvaluator.class, ClosureEvaluator.class);
    }
}
