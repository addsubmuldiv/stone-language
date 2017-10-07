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
 * @Discrpition: 在这里定义闭包的eval方法，直接新建函数并返回，和def不一样，因为闭包是没有名字的，而直接返回一个Function
 * 对象，所以它可以被赋值给一个变量，而变量名在环境里实际上和def定义出的函数名是没有本质区别的
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
