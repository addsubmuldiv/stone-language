package chap6;
import javassist.gluonj.*;
import stone.StoneException;
import stone.Token;
import stone.ast.*;

import java.util.List;
/**
 * @Author: Lighters_c
 * @Discrpition: 这里使用《自制脚本语言》作者实验室研制出的GluonJ框架，可以实现AOP(面向切面编程),即
 * 通过使用@Reviser注解和继承把每次要修改的代码在一处处理，而不是再到写好的类里面大改。尽管因为
 * 代码分离开，无法一次看到一个类的全貌，因而对读代码的人造成困扰，但对于开发还是很方便的。
 * 我自己对着写是没啥感觉的啦…反正估计也不会有人看……………………所以如果你看到了，你可以要求我向你道歉(逃
 * @Date: Created in 13:45 2017/10/4
 * @Modified by:
 */
@Reviser public class BasicEvaluator {
    public static final int TRUE=1;
    public static final int FALSE=0;

    //为ASTree类添加eval抽象方法
    @Reviser public static abstract class ASTreeEx extends ASTree {
        public abstract Object eval(Environment env);
    }
    //为上面ASTree类的eval抽象方法提供默认实现
    @Reviser public static class ASTListEx extends ASTList {
        public ASTListEx(List<ASTree> c) { super(c); }
        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser public static class ASTLeafEx extends ASTLeaf {
        public ASTLeafEx(Token t) { super(t); }
        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }
    //实现数字字面量的eval方法，就是直接返回数字的值
    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(Token t) {
            super(t);
        }
        public Object eval(Environment e) {
            return value();
        }
    }
    //实现字符串字面量的eval方法，也是直接返回字符串本身
    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            return value();
        }
    }
    //name是变量名,所以就要从环境env中去获取变量的值，如果环境没有这个变量名，就抛出异常
    @Reviser public static class NameEx extends Name {

        public NameEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            Object value=env.get(name());
            if(value==null) {
                throw new StoneException("undefined name: "+name(),this);
            }else
                return value;
        }
    }
    // 这是负数表达式，其eval实际上是包裹了一个普通的数字字面量(也就是说只考虑正数)，负号是在将数字
    // 从环境里取出来后再加上的，换句话说，环境里只保存正数
    @Reviser public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            Object v=((ASTreeEx)operand()).eval(env);
            if(v instanceof Integer)
                return new Integer(-((Integer)v).intValue());
            else
                throw new StoneException("bad type for -",this);
        }
    }

    /**
     * 这里是二元运算表达式，eval先判断操作符是否是赋值号，如果是赋值，那就先计算赋值号
     * 右边的值，然后取left()和变量名一起保存到环境里，否则计算左边和右边的值，然后和
     * 中间的操作符在一起进行计算
      */
    @Reviser public static class BinaryEx extends BinaryExpr {
        public BinaryEx(List<ASTree> list) {
            super(list);
        }

        public Object eval(Environment env) {
            String op=operator();
            if("=".equals(op)) {
                Object right = ((ASTreeEx)right()).eval(env);
                return computeAssign(env,right);
            }else {
                Object left = ((ASTreeEx)left()).eval(env);
                Object right = ((ASTreeEx)right()).eval(env);
                return computeOp(left,op,right);
            }
        }

        protected Object computeAssign(Environment env, Object right) {
            ASTree l=left();
            if(l instanceof Name) {
                env.put(((Name)l).name(),right);
                return right;
            }else {
                throw new StoneException("bad assignment", this);
            }
        }

        private Object computeOp(Object left, String op, Object right) {
            if(left instanceof Integer&&right instanceof Integer) {
                return computeNumber((Integer)left,op,(Integer)right);
            }
            else
            {
                if(op.equals("+"))
                    return String.valueOf(left)+String.valueOf(right);
                else if(op.equals("==")) {
                    if(left==null)
                        return right==null? TRUE:FALSE;
                    else
                        return left.equals(right)? TRUE:FALSE;
                }else {
                    throw new StoneException("bad type",this);
                }
            }
        }

        private Object computeNumber(Integer left, String op, Integer right) {
            int a=left.intValue();
            int b=right.intValue();
            if(op.equals("+"))
                return a+b;
            else if(op.equals("-"))
                return a-b;
            else if(op.equals("*"))
                return a*b;
            else if(op.equals("/"))
                return a/b;
            else if(op.equals("%"))
                return a%b;
            else if(op.equals("=="))
                return a==b? TRUE:FALSE;
            else if(op.equals(">"))
                return a>b? TRUE:FALSE;
            else if(op.equals("<"))
                return a<b? TRUE:FALSE;
            else
                throw new StoneException("bad operator",this);
        }
    }

    /**
     * 块的树结构，
     */
    @Reviser public static class BlockEx extends BlockStmnt {
        public BlockEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            Object result=0;
            for(ASTree t: this) {
                if(!(t instanceof NullStmnt))
                    result = ((ASTreeEx)t).eval(env);
            }
            return result;
        }
    }

    /**
     * If的树结构，先计算condition，如果是TRUE，那么久对thenBlock进行eval，
     * 否则进行else，如果else不存在，就返回0跳过
     */
    @Reviser public static class IfEx extends IfStmnt {

        public IfEx(List<ASTree> list) {
            super(list);
        }

        public Object eval(Environment env) {
            Object c = ((ASTreeEx)condition()).eval(env);
            if(c instanceof Integer&&((Integer)c).intValue()!=FALSE)
                return ((ASTreeEx)thenBlock()).eval(env);
            else {
                ASTree b =elseBlock();
                if(b==null)
                    return 0;
                else
                    return ((ASTreeEx)b).eval(env);
            }
        }
    }

    @Reviser public static class WhileEx extends WhileStmnt {
        public WhileEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            Object result=0;
            for(;;) {
                Object c = ((ASTreeEx)condition()).eval(env);
                if(c instanceof Integer&&((Integer)c).intValue()==FALSE)
                    return result;
                else
                    result=((ASTreeEx)body()).eval(env);
            }
        }
    }
}



