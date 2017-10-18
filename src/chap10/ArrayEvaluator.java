package chap10;

import chap6.BasicEvaluator;
import chap6.Environment;
import chap7.FuncEvaluator;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.ArrayParser;
import stone.StoneException;
import stone.ast.ASTree;
import stone.ast.ArrayLiteral;
import chap6.BasicEvaluator.ASTreeEx;
import stone.ast.ArrayRef;
import stone.ast.PrimaryExpr;
import chap7.FuncEvaluator.PrimaryEx;
import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:35 2017/10/16
 * @Modified_by:
 */
@Require({FuncEvaluator.class, ArrayParser.class})
@Reviser public class ArrayEvaluator {


    /**
     * 数组字面量,Stone中的数组使用java中的Object类型的数组表示，对每一个数组元素调用eval，把它放到环境
     * 中，然后让Object数组元素去指向它，然后返回Object数组的引用
     */
    @Reviser public static class ArrayLitEx extends ArrayLiteral {
        public ArrayLitEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            int s=numChildren();
            Object[] res=new Object[s];
            int i=0;
            for(ASTree t:this) {
                res[i++]=((ASTreeEx)t).eval(env);
            }
            return res;
        }
    }


    /**
     * 数组的引用,其eval首先对下标表达式调用eval方法，计算下标的值，之后从参数value指向的Object类型数组中
     * 获取与该下标对应的元素的值并返回。
     */
    @Reviser public static class ArrayRefEx extends ArrayRef {
        public ArrayRefEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env, Object value) {
            if(value instanceof Object[]) {
                Object index=((ASTreeEx)index()).eval(env);
                if(index instanceof Integer)
                    return ((Object[])value)[(Integer)index];
            }
            throw new StoneException("bad array access:", this);
        }
    }



    @Reviser public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<ASTree> c) {
            super(c);
        }

        /**
         * 和面向对象中对字段进行赋值的算法差不多，先把左操作数进行eval，然后如果
         * 做操作数的postfix是数组引用的话，将其从环境中取出,然后看这个数组引用的下标是多少
         * 将其计算出来，然后对其进行赋值
         * @param env
         * @param rvalue
         * @return
         */
        @Override
        protected Object computeAssign(Environment env, Object rvalue) {
            ASTree le = left();
            if(le instanceof PrimaryExpr) {
                PrimaryEx p=(PrimaryEx)le;
                if(p.hasPostfix(0)&&p.postfix(0) instanceof ArrayRef) {
                    //a是Object数组引用
                    Object a = ((PrimaryEx)le).evalSubExpr(env,1);
                    if(a instanceof Object[]) {
                        ArrayRef aref = (ArrayRef)p.postfix(0);
                        Object index = ((ASTreeEx)aref.index()).eval(env);
                        if(index instanceof Integer) {
                            ((Object[])a)[(Integer)index]=rvalue;
                            return rvalue;
                        }
                    }
                    throw new StoneException("bad array access",this);
                }
            }
            return super.computeAssign(env, rvalue);
        }
    }
}
