package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
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
