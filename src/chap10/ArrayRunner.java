package chap10;

import chap7.ClosureEvaluator;
import chap8.NativeEvaluator;
import chap9.ClassEvaluator;
import chap9.ClassInterpreter;
import javassist.gluonj.util.Loader;


/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:14 2017/10/16
 * @Modified_by:
 */
public class ArrayRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class,args, ClassEvaluator.class,ArrayEvaluator.class,
                NativeEvaluator.class, ClosureEvaluator.class);
    }
}
