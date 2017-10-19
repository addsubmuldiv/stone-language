package chap11;

import chap6.Environment;
import stone.StoneException;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 23:34 2017/10/19
 * @Modified_by:
 */
public class ArrayEnv implements Environment {
    protected Object[] values;
    protected Environment outer;

    public ArrayEnv(int size, Environment out) {
        values = new Object[size];
        outer = out;
    }

    public Symbols symbols() {
        throw new StoneException("no symbols");
    }

    @Override
    public void put(String name, Object value) {

    }

    @Override
    public Object get(String name) {
        return null;
    }
}
