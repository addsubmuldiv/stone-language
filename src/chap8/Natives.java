package chap8;

import chap6.Environment;
import stone.StoneException;

import javax.swing.*;
import java.lang.reflect.Method;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 12:31 2017/10/9
 * @Modified_by:
 */
public class Natives {
    public Environment environment(Environment env) {
        appendNatives(env);
        return env;
    }
    protected void appendNatives(Environment env) {
        append(env, "print", Natives.class, "print", Object.class);
        append(env, "read", Natives.class, "read");
        append(env, "length", Natives.class, "length", String.class);
        append(env, "toInt", Natives.class, "toInt", Object.class);
        append(env, "currentTime", Natives.class, "currentTime");
    }

    /**
     * 把原生方法添加到环境中,name是新名字，methodName是本来名字
     * @param env
     * @param name
     * @param clazz
     * @param methodName
     * @param params
     */
    protected void append(Environment env, String name, Class<?> clazz,String methodName, Class<?> ... params) {
        Method m;
        try {
            m=clazz.getMethod(methodName, params);
        } catch (Exception e) {
            throw new StoneException("cannot find a native function: "+ methodName);
        }
        env.put(name, new NativeFunction(methodName,m));
    }

    /**
     * 添加输出字符串到控制台的函数
     * @param obj
     * @return
     */
    public static int print(Object obj) {
        System.out.println(obj.toString());
        return 0;
    }

    public static String read() {
        return JOptionPane.showInputDialog(null);
    }

    /**
     * 添加返回字符串的长度的函数
     * @param s
     * @return
     */
    public static int length(String s) {
        return s.length();
    }

    /**
     * 返回把其他类型转换为整数的函数
     * @param value
     * @return
     */
    public static int toInt(Object value) {
        if(value instanceof String)
            return Integer.parseInt((String)value);
        else if(value instanceof Integer)
            return ((Integer)value).intValue();
        else
            throw new NumberFormatException(value.toString());
    }

    private static long startTime=System.currentTimeMillis();

    /**
     * 返回当前当前时间的函数
     * @return
     */
    public static int currentTime() {
        return (int)(System.currentTimeMillis()-startTime);
    }
}
