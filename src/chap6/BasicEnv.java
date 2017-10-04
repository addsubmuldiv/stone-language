package chap6;

import java.util.HashMap;

/**
 * 环境接口的实现，使用HashMap来作为容器
 */
public class BasicEnv implements Environment {
    protected HashMap<String,Object> values;

    public BasicEnv() {
        values = new HashMap<>();
    }

    @Override
    public void put(String name, Object value) {
        values.put(name,value);
    }

    @Override
    public Object get(String name) {
        return values.get(name);
    }
}
