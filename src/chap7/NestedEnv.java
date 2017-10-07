package chap7;

import chap6.Environment;
import chap7.FuncEvaluator.EnvEx;
import java.util.HashMap;

/**
 * @Author: Lighters_c
 * @Discrpition: NestedEnv，对Environment接口的另一个实现，为了支持了作用域和生命周期，添加了一个字段outer用来保存外部环境
 * 可以通过setOuter来设置外部环境，也可以在构造时传入外部环境的引用，putNew方法不管外部环境，添加一个新的变量，或者函数
 * where返回包含指定变量名的对象的环境，get先在当前环境里面找，找不到就去outer找
 * @Date: Created in 14:30 2017/10/5
 * @Modified_by:
 */
public class NestedEnv implements Environment {
    protected HashMap<String,Object> values;
    protected Environment outer;
    public NestedEnv() {this(null);}
    public NestedEnv(Environment env) {
        values=new HashMap<>();
        outer=env;
    }
    public void setOuter(Environment e) {
        outer=e;
    }
    public void putNew(String name, Object value) { values.put(name, value); }
    @Override
    public void put(String name, Object value) {
        Environment e =where(name);
    }

    @Override
    public Object get(String name) {
        Object v = values.get(name);
        if(v==null&&outer!=null)
            return outer.get(name);
        else
            return v;
    }

    public Environment where(String name) {
        if(values.get(name)!=null)
            return this;
        else if(outer==null)
            return null;
        else
            return ((EnvEx)outer).where(name);
    }
}
