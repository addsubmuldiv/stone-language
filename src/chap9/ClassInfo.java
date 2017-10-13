package chap9;

import chap6.Environment;
import stone.StoneException;
import stone.ast.ClassBody;
import stone.ast.ClassStmnt;

/**
 * @Author: Lighters_c
 * @Discrpition: 类的信息，stone中定义的类的信息。总觉得，这个有点类似java中的反射,就是，所有的类都是类类型的对象
 * @Date: Created in 13:38 2017/10/13
 * @Modified_by:
 */
public class ClassInfo {
    protected ClassStmnt definition;
    protected Environment environment;
    protected ClassInfo superClass;

    /**
     * 初始化的时候，把定义的类本身和类在AST中的节点、以及环境还有这个类的父类关联起来
     * @param cs
     * @param env
     */
    public ClassInfo(ClassStmnt cs, Environment env) {
        definition = cs;
        environment = env;
        Object obj=env.get(cs.superClass());
        if(obj==null)
            superClass=null;
        else if(obj instanceof ClassInfo)
            superClass=(ClassInfo)obj;
        else
            throw new StoneException("unknown super class: "+cs.superClass(),cs);
    }

    /**
     * 返回这个类的名字
     * @return
     */
    public String name() {
        return definition.name();
    }

    /**
     * 返回这个类的父类
     * @return
     */
    public ClassInfo superClass() {
        return superClass;
    }

    /**
     * 返回这个类的定义的AST树节点
     * @return
     */
    public ClassBody body() {
        return definition.body();
    }

    /**
     * 返回这个类的环境
     * @return
     */
    public Environment environment() {
        return environment;
    }

    /**
     * 显示类的说明
     * @return
     */
    @Override
    public String toString() {
        return "<class "+name()+">";
    }
}
