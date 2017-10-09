package chap8;

import chap6.BasicEvaluator;
import chap6.Environment;
import chap7.ClosureEvaluator;
import chap7.FuncEvaluator;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.ASTree;
import chap6.BasicEvaluator.ASTreeEx;
import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:10 2017/10/9
 * @Modified_by:
 */
@Require(FuncEvaluator.class)
@Reviser public class NativeEvaluator {
    @Reviser public static class NativeArgEx extends FuncEvaluator.ArgumentsEx {
        public NativeArgEx(List<ASTree> list) {
            super(list);
        }

        /**
         * 先判断是不是NativeFunction对象，不是则调用普通的eval，否则，就使用NativeFunction的invoke方法。
         * @param callerEnv
         * @param value
         * @return
         */
        @Override
        public Object eval(Environment callerEnv, Object value) {
            if(!(value instanceof NativeFunction))
                return super.eval(callerEnv, value);

            NativeFunction func = (NativeFunction)value;
            int nparams=func.numOfParameters();
            if(size()!=nparams)
                throw new StoneException("bad number of arguments", this);
            Object[] args=new Object[nparams];
            int num=0;
            for(ASTree a: this) {
                args[num++]=((ASTreeEx)a).eval(callerEnv);
            }
            return func.invoke(args,this);
        }
    }
}
