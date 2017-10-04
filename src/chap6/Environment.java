package chap6;

/**
 * 环境的接口，用来保存变量状态等，使用put方法添加，使用get获取
 */
public interface Environment {
    void put(String name, Object value);
    Object get(String name);
}
