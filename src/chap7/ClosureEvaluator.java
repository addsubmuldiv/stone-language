package chap7;

import chap6.BasicEvaluator;
import chap6.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.ast.ASTree;
import stone.ast.Fun;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 0:04 2017/10/8
 * @Modified_by:
 */
@Require({BasicEvaluator.class,FuncEvaluator.class})
@Reviser public class ClosureEvaluator {
    @Reviser public static class FunEx extends Fun {
        public FunEx(List<ASTree> c) { super(c); }
        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}
