package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
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
