package chap7;

import chap6.BasicEvaluator;
import chap6.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.*;
import chap6.BasicEvaluator.ASTreeEx;
import chap6.BasicEvaluator.BlockEx;
import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: 函数的eval方法定义，依旧是使用了GluonJ，将需要修改的部分集中在一起，实现AOP
 * @Date: Created in 15:41 2017/10/5
 * @Modified by:
 */
@Require(BasicEvaluator.class)  //指定修改器需要用到的其他修改器，在调用FuncEvaluator前先调用BasicEvaluator
@Reviser public class FuncEvaluator {
    /**
     * 这里是环境接口，扩展了putNew：无视外层环境，给当前环境添加变量，和where:查找包含指定变量名的环境并返回。
     */
    @Reviser public static interface EnvEx extends Environment {
        void putNew(String name, Object value);
        Environment where(String name);
        void setOuter(Environment e);
    }

    /**
     * 定义函数的eval，就是向环境中添加一个名字，和一个Function对象关联起来
     */
    @Reviser public static class DefStmntEx extends DefStmnt {
        public DefStmntEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            ((EnvEx)env).putNew(name(), new Function(parameters(), body(), env));
            return name();
        }
    }

    /**
     * 为基本构成元素添加函数调用表达式，即fact(9)这样的，operand返回函数名，postfix返回实参序列，
     * hasPostfix返回是否有实参序列，eval，如果有实参序列，就先对实参序列进行eval，并把他们作为参数
     * 继续调用postfix的eval方法
     */
    @Reviser public static class PrimaryEx extends PrimaryExpr {
        public PrimaryEx(List<ASTree> list) {
            super(list);
        }
        public ASTree operand() {
            return child(0);
        }
        public Postfix postfix(int nest) {
            return (Postfix)child(numChildren()-nest-1);
        }
        public boolean hasPostfix(int nest) {
            return numChildren()-nest >1;
        }
        public Object eval(Environment env) {
            return evalSubExpr(env,0);
        }
        public Object evalSubExpr(Environment env, int nest) {
            if(hasPostfix(nest)) {
                Object target = evalSubExpr(env,nest+1);
                return ((PostfixEx)postfix(nest)).eval(env,target);
            }
            else
                return ((ASTreeEx)operand()).eval(env);
        }
    }
    //给Postfix类添加了一个eval方法
    @Reviser public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<ASTree> list) {
            super(list);
        }
        public abstract Object eval(Environment env, Object value);
    }
    /**
     * Arguments类添加的eval方法是整个函数调用功能的核心，value是要调用的函数对应的Function对象，Function对象由
     * def语句创建。
     * 调用func.parameters()方法获取形参列表，如果实参数量和形参数量不一致会抛出异常，否则继续，将创建Function时
     * 保存的环境作为父环境，创建新环境newEnv,然后遍历实参，调用Params类的eval方法，把实参的值和形参的名字对应起来
     * 放到newEnv中，最后把newEnv传给函数体，调用函数体的eval,newEnv在Arguments类的eval中创建，所以在这个方法调用
     * 完就被垃圾回收了，与函数参数和局部变量的生命周期相符。
     */
    @Reviser public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment callerEnv, Object value) {
            if(!(value instanceof Function))
                throw new StoneException("bad function",this);
            Function func=(Function)value;
            ParameterList params = func.parameters();
            if(size()!=params.size())
                throw new StoneException("bad number of arguments",this);
            Environment newEnv=func.makeEnv();
            int num=0;
            for(ASTree a:this)
                ((ParamsEx)params).eval(newEnv,num++,((ASTreeEx)a).eval(callerEnv));
            return ((BlockEx)func.body()).eval(newEnv);
        }
    }
    @Reviser public static class ParamsEx extends ParameterList {
        public ParamsEx(List<ASTree> list) {
            super(list);
        }
        public void eval(Environment env,int index,Object value) {
            ((EnvEx)env).putNew(name(index),value);
        }
    }
}
