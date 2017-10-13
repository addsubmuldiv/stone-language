package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: 类定义的树结构，第一个子节点是类名，第二个是它的父类(如果有的话)最后一个是类的具体定义的树结构的引用,
 * which is 类的体
 * @Date: Created in 13:19 2017/10/13
 * @Modified_by:
 */
public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> list) {
        super(list);
    }

    /**
     * 返回类名
     * @return
     */
    public String name() {
        return ((ASTLeaf)child(0)).token().getText();
    }

    /**
     * 返回父类，如果存在的话，否则返回null
     * @return
     */
    public String superClass() {
        if(numChildren() < 3) {
            return null;
        } else {
            return ((ASTLeaf)child(1)).token().getText();
        }
    }

    /**
     * 返回类的具体定义
     * @return
     */
    public ClassBody body() {
        return (ClassBody)child(numChildren()-1);
    }

    /**
     * 返回类的描述
     * @return
     */
    @Override
    public String toString() {
        String parent=superClass();
        if(parent==null)
            parent="*";
        return "(class "+name()+" "+parent+" "+body()+")";
    }
}
