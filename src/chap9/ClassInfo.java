package chap9;

import chap6.Environment;
import stone.StoneException;
import stone.ast.ClassBody;
import stone.ast.ClassStmnt;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:38 2017/10/13
 * @Modified_by:
 */
public class ClassInfo {
    protected ClassStmnt definition;
    protected Environment environment;
    protected ClassInfo superClass;
    public ClassInfo(ClassStmnt cs, Environment env) {
        definition = cs;
        environment = env;
        Object obj=env.get(cs.superClass());
        if(obj==null)
            superClass=null;
        else if(obj instanceof ClassInfo)
            superClass=null;
        else
            throw new StoneException("unknown super class: "+cs.superClass(),cs);
    }
    public String name() {
        return definition.name();
    }
    public ClassInfo superClass() {
        return superClass;
    }
    public ClassBody body() {
        return definition.body();
    }
    public Environment environment() {
        return environment;
    }
    @Override
    public String toString() {
        return "<class "+name()+">";
    }
}
