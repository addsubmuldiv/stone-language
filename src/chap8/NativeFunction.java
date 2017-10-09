package chap8;

import stone.StoneException;
import stone.ast.ASTree;

import java.lang.reflect.Method;

/**
 * @Author: Lighters_c
 * @Discrpition: 为stone添加原生方法，即可以调用java中的已有的函数
 * @Date: Created in 10:51 2017/10/9
 * @Modified_by:
 */
public class NativeFunction {
    protected Method method;
    protected String name;
    protected int numParams;
    public NativeFunction(String n, Method m) {
        name=n;
        method=m;
        numParams=m.getParameterCount();
    }
    @Override
    public String toString() {
        return "<native:"+hashCode()+">";
    }
    public int numOfParameters() { return numParams; }
    public Object invoke(Object[] args, ASTree tree) {
        try {
            return method.invoke(null,args);
        } catch (Exception e) {
            throw new StoneException("bad native function call: "+name, tree);
        }
    }
}
