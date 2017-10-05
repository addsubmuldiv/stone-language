package chap7;

import chap6.Environment;
import chap7.FuncEvaluator.EnvEx;
import java.util.HashMap;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 14:30 2017/10/5
 * @Modified by:
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
