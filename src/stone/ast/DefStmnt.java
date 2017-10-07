package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: def，即函数定义的结构，child(0)是函数名，child(1)是参数列表，child(2)则是函数体的节点
 * 有toString方法，把整个函数的定义写出来
 * @Date: Created in 13:48 2017/10/5
 * @Modified by:
 */
public class DefStmnt extends ASTList {
    public DefStmnt(List<ASTree> list) {
        super(list);
    }
    public String name() { return ((ASTLeaf)child(0)).token().getText(); }

    public ParameterList parameters() { return (ParameterList)child(1); }

    public BlockStmnt body() { return (BlockStmnt)child(2); }

    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }
}
