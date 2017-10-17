package chap9;


import chap6.Environment;
import chap7.FuncEvaluator.EnvEx;
/**
 * @Author: Lighters_c
 * @Discrpition: 使用闭包来实现对象，就是说把对象本身当做是一种环境，字段什么的就是自由变量，
 * 然后参数传进来的是约束变量，
 * @Date: Created in 14:08 2017/10/13
 * @Modified_by:
 */
public class StoneObject {
    public static class AccessException extends Exception {}
    protected Environment env;
    public StoneObject(Environment e) { env=e; }
    @Override
    public String toString() {
        return "<object:"+hashCode()+">";
    }

    /**
     * 从环境中取出指定的那个字段或者方法
     * @param member
     * @return
     * @throws AccessException
     */
    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);
    }

    /**
     * 把字段重新赋值
     * @param member
     * @param value
     * @throws AccessException
     */
    public void write(String member, Object value) throws AccessException {
        ((EnvEx)getEnv(member)).putNew(member,value);
    }

    /**
     * 获取member这个字段所处的环境
     * @param member
     * @return
     * @throws AccessException
     */
    protected Environment getEnv(String member) throws AccessException {
        Environment e=((EnvEx)env).where(member);
        if(e!=null&&e==env)
            return e;
        else
            throw new AccessException();
    }
}
