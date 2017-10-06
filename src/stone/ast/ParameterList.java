package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: 函数参数列表的类，用name()可以获取每个参数的名字，然后size()返回参数的个数.
 * @Date: Created in 13:38 2017/10/5
 * @Modified by:
 */
public class ParameterList extends ASTList {
    public ParameterList(List<ASTree> list) {
        super(list);
    }
    public String name(int i) { return ((ASTLeaf)child(i)).token().getText(); }
    public int size() {return numChildren();}
}
